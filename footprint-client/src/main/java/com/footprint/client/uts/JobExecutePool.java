package com.footprint.client.uts;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.client.uts.executor.JobExecutor;
import com.footprint.common.WebResult;
import com.footprint.common.utils.ExceptionUtils;
import com.footprint.common.utils.HttpClient;
import com.footprint.common.utils.JsonUtils;
import com.footprint.common.utils.NetUtils;
import com.footprint.common.utils.StringUtils;

public class JobExecutePool {
	private static final long REPORT_PERIOD_TIME = 60000L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//field
	private ConcurrentHashMap<String, JobContext> fireInstances = new ConcurrentHashMap<String, JobContext>();
	private ThreadPoolExecutor taskExecutor;
    private CheckerThread checkerThread;
    
    private String reportUrl;
    private String validateUrl;
    
	public JobExecutePool(int coreJobThread, int maxJobThread, int jobQueueSize, String utsServerUrl) {
    	validateUrl = StringUtils.removeEnd(utsServerUrl, "/") + "/uts/validateJob.do";
    	reportUrl = StringUtils.removeEnd(utsServerUrl, "/") + "/uts/reportJob.do";
    	
		taskExecutor = new ThreadPoolExecutor(coreJobThread, maxJobThread, 60,
				TimeUnit.SECONDS, jobQueueSize == 0 ? new SynchronousQueue<Runnable>() :
					(jobQueueSize < 0 ? new LinkedBlockingQueue<Runnable>() :
						new LinkedBlockingQueue<Runnable>(jobQueueSize)));
        checkerThread = new CheckerThread(REPORT_PERIOD_TIME, "checkerThread");
        checkerThread.setDaemon(true);
        checkerThread.start();
	}
	
	public void addJobToPool(final JobContext jobContext) {
	        taskExecutor.execute(new Runnable() {
	            public void run() {
	                if (jobContext.acquireExecutingLock()) {
	                    try {
	                        if (jobContext.isExecuted())
	                            return;
	                        String fireInstanceId = jobContext.getFireInstanceId();
	                        logger.info("[uts]Job starting run in threadPool, fireInstanceId: " + fireInstanceId);
	                        boolean validateResult = validateJob(fireInstanceId);
	                        if (validateResult) {
	                        	jobContext.setValidated(true);
	                        	logger.info("[uts]validateJob is ok, fireInstanceId: " + fireInstanceId);
	                        } else {
	                        	fireInstances.remove(fireInstanceId);
	                        	logger.warn("[uts]validateJob is failure, fireInstanceId: "+ fireInstanceId);
	                        	return;
	                        }
	                        try {
	                            JobExecutor jobExecutor = jobContext.getJobExecutor();
	                            jobExecutor.execute(jobContext);
	                            jobContext.setExecuted(true);
	                        } catch (Throwable ex) {
	                            logger.error("[uts]Exception occur when Job executing.", ex);
	                            jobContext.setExecuted(true);
	                            StringWriter sw = new StringWriter();
	                            PrintWriter pw = new PrintWriter(sw);
	                            ex.printStackTrace(pw);
	                            Throwable rootException = ExceptionUtils.getRootCause(ex);
	                            jobContext.setRootExceptionClassName(rootException.getClass().getName());
	                            String rootMsg = StringUtils.isEmpty(rootException.getMessage()) ? "No exception message." : rootException.getMessage();
	                            int msgLen = rootMsg.length()>1000 ? 1000 : rootMsg.length();
	                            jobContext.setRootExceptionMsg(rootMsg.substring(0, msgLen));
	                            jobContext.setExceptionStackTrace(sw.toString());
	                        } finally {
	                            List<JobContext> contexts = new ArrayList<JobContext>();
	                            contexts.add(jobContext);
	                            reportJob(contexts);
	                        }
	                    } finally {
	                        jobContext.releaseExecutingLock();
	                    }
	                }
	            }
	        });
	    }
	   
	    public void shutdown() {
	        taskExecutor.shutdown();
	        checkerThread.shutdown();
	        try {
	            checkerThread.join(3000L);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }
	    
	    private void reportJob(Collection<JobContext> contexts) {
	        if (contexts.isEmpty())
	            return;
	        String nodeName = getClientNodeName();
	        List<Map<String, Object>> insList = new ArrayList<Map<String, Object>>();
	        List<String> executedFireInstanceIds = new ArrayList<String>();
	        for (JobContext context : contexts) {
	            Map<String, Object> fireInstance = new HashMap<String, Object>();
	            fireInstance.put(HttpFields.FIRE_INSTANCE_ID, context.getFireInstanceId());
	            fireInstance.put(HttpFields.CLIENT_NODE_NAME, nodeName);
	            fireInstance.put(HttpFields.JOB_GROUP, context.getJobGroup());
	            fireInstance.put(HttpFields.JOB_NAME, context.getJobName());
	            fireInstance.put(HttpFields.TRIGGER_GROUP, context.getTriggerGroup());
	            fireInstance.put(HttpFields.TRIGGER_NAME, context.getTriggerName());
	            fireInstance.put(HttpFields.EXECUTED, context.isExecuted());
	            if (context.isExecuted()) {
	                executedFireInstanceIds.add(context.getFireInstanceId());
	            }
	            if (!StringUtils.isEmpty(context.getExceptionStackTrace())) {
	                fireInstance.put(HttpFields.EXCEPTION_STACK_TRACE, context.getExceptionStackTrace());
	                fireInstance.put(HttpFields.ROOT_EXCEPTION_CLASS_NAME, context.getRootExceptionClassName());
	                fireInstance.put(HttpFields.ROOT_EXCEPTION_MSG, context.getRootExceptionMsg());
	            }
	            insList.add(fireInstance);
	        }
	        try {
	        	String json = JsonUtils.beanToJson(insList);
	            String respJson = HttpClient.sendByPost(reportUrl, json, 30000);
	            logger.info("[uts]Job report resp: {}", respJson);
	            for (String instanceId : executedFireInstanceIds) {
	                fireInstances.remove(instanceId);
	            }
	        } catch (Exception ex) {
	            logger.error("[uts]IOException occur when report job execution, reportUrl[{}]", reportUrl, ex);
	        }
	    }

	    private boolean validateJob(String fireInstanceId) {
	        Map<String, String> data = new HashMap<String, String>();
	        data.put(HttpFields.FIRE_INSTANCE_ID, fireInstanceId);
	        data.put(HttpFields.CLIENT_NODE_NAME, getClientNodeName());
	        String json = JsonUtils.beanToJson(data);
	        logger.info("[uts]validateJob req json: {}", json);
	        for (int i = 0; i < 10; i++) {
	            int interval = 500;
	            try {
	                String respJson = HttpClient.sendByPost(validateUrl, json, 30000);
	                logger.info("[uts]validateJob resp json: {}", respJson);
	                if (respJson != null) {
	                    WebResult result = JsonUtils.jsonToBean(respJson, WebResult.class);
	                    return result.isSuccess();
	                }
	            } catch (Exception ex) {
	                logger.warn("[uts]Exception occur when validate job, fireInstanceId[{}]", fireInstanceId, ex);
	            }
	            try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					//NOOP
				}
	        }
	        return false;
	    }
	    

	    private String getClientNodeName() {
	        String nodeIp = NetUtils.getLocalIP();
	        String processorId = ManagementFactory.getRuntimeMXBean().getName();
	        if (processorId != null) {
	            processorId = processorId.split("@")[0];
	        }
	        return nodeIp + "/" + processorId;
	    }
	    
	   private class CheckerThread extends Thread {
	        private final long sleepMillis;
	        private volatile boolean shutdown = false;

	        public CheckerThread(long sleepMillis, String name) {
	            super(name);
	            this.sleepMillis = sleepMillis;
	        }

	        public synchronized void shutdown() {
	            shutdown = true;
	            super.interrupt();
	        }

	        @Override
	        public void run() {
	            while (!shutdown) {
	                List<String> needRemoved = new ArrayList<String>();
	                for (JobContext context : fireInstances.values()) {
	                    if (!context.hasValidated()) {
	                        // Not pass validation in 30 min.
	                        if (System.currentTimeMillis() - context.getReceivedTime() > 1000 * 60 * 30) {
	                            needRemoved.add(context.getFireInstanceId());
	                            logger.warn("[uts]FireInstanceId[{}] not pass validation in 30 min, job is removed.", context.getFireInstanceId());
	                        }
	                    }
	                }
	                for (String fireInstanceId : needRemoved) {
	                    fireInstances.remove(fireInstanceId);
	                }
	                reportJob(fireInstances.values());
	                try {
	                	Thread.sleep(sleepMillis);
	                } catch (InterruptedException e) {
	                    return;
	                }
	            }
	        }
	    }
}
