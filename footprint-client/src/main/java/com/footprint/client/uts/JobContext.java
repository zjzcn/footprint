package com.footprint.client.uts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletContext;

import com.footprint.client.uts.executor.JobExecutor;

public class JobContext {
	private ServletContext servletContext;
    private String fireInstanceId;
    private String jobGroup;
    private String jobName;
    private String triggerGroup;
    private String triggerName;
    private JobExecutor jobExecutor;
    private Map<String, ?> data = new HashMap<String, Object>();
    private boolean validated = false;
    private ReentrantLock executingLock = new ReentrantLock();
    private boolean executed = false;
    private String exceptionStackTrace = null;
    private String rootExceptionClassName = null;
    private String rootExceptionMsg = null;
    private Long receivedTime;
    private boolean concurrentDisallowed = false;

    public JobContext(ServletContext servletContext, String fireInstanceId, String jobGroup, String jobName,
                               String triggerGroup, String triggerName,
                               String executorClassName, Map<String, ?> data,
                               boolean concurrentDisallowed) {
    	this.servletContext = servletContext;
        this.fireInstanceId = fireInstanceId;
        this.jobGroup = jobGroup;
        this.jobName = jobName;
        this.triggerGroup = triggerGroup;
        this.triggerName = triggerName;
        this.data = data;
        this.receivedTime = System.currentTimeMillis();
        this.concurrentDisallowed = concurrentDisallowed;
        
        Object object;
    	try {
    		Class<?> clazz = Class.forName(executorClassName);
    		object = clazz.newInstance();
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	if (!(object instanceof JobExecutor)) {
    		throw new RuntimeException("Class named " + executorClassName + " is not a JobExecutor.");
    	}
    	jobExecutor = (JobExecutor) object;
    }

    public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getFireInstanceId() {
        return fireInstanceId;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public Set<String> getDataKeys() {
        return data.keySet();
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public void setExceptionStackTrace(String exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
    }

    public String getRootExceptionClassName() {
        return rootExceptionClassName;
    }

    public void setRootExceptionClassName(String rootExceptionClassName) {
        this.rootExceptionClassName = rootExceptionClassName;
    }

    public boolean hasValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public Long getReceivedTime() {
        return receivedTime;
    }

    public JobExecutor getJobExecutor() {
    	return jobExecutor;
    }

    public void setRootExceptionMsg(String rootExceptionMsg) {
        this.rootExceptionMsg = rootExceptionMsg;
    }

    public String getRootExceptionMsg() {
        return rootExceptionMsg;
    }

    public boolean acquireExecutingLock() {
        return executingLock.tryLock();
    }

    public void releaseExecutingLock() {
        executingLock.unlock();
    }

    public boolean isConcurrentDisallowed() {
        return concurrentDisallowed;
    }
}
