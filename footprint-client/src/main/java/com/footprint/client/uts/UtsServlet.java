package com.footprint.client.uts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.client.ClientConfig;
import com.footprint.common.WebResult;
import com.footprint.common.utils.ExceptionUtils;
import com.footprint.common.utils.JsonUtils;
import com.footprint.common.utils.NetUtils;
import com.footprint.common.utils.StringUtils;

public class UtsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ServletContext servletContext;
	private JobExecutePool jobExecutePool;

	public final void init() throws ServletException {
		servletContext = super.getServletConfig().getServletContext();
		try {
			String utsServerUrl = ClientConfig.getUtsServerUrl();
			int coreJobThread = ClientConfig.getUtsCoreJobThreads();
			int maxJobThread = ClientConfig.getUtsMaxJobThreads();
			int jobQueueSize = ClientConfig.getUtsJobQueueSize();

			jobExecutePool = new JobExecutePool(coreJobThread, maxJobThread, jobQueueSize, utsServerUrl);
		} catch (Exception ex) {
			logger.error("[uts]Exception occur when init.", ex);
			throw new ServletException(ex);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	public void destroy() {
		jobExecutePool.shutdown();
	}

	@SuppressWarnings("unchecked")
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String json = getReqBody(req);
		Map<String, Object> reqParams = JsonUtils.jsonToBean(json, Map.class);
		if (reqParams.containsKey(HttpFields.TEST)) {
			writeResp(resp, WebResult.newResult());
			return;
		}

		String fireInstanceId = (String) reqParams.get(HttpFields.FIRE_INSTANCE_ID);
		if (StringUtils.isEmpty(fireInstanceId)) {
			String errorMsg = "[uts]Parameter fireInstanceId is empty, access is denied.";
			logger.warn(errorMsg);
			writeResp(resp, WebResult.newErrorResult(errorMsg));
			return;
		}
		String executorClassName = (String) reqParams.get(HttpFields.EXECUTOR_CLASS_NAME);
		if (StringUtils.isEmpty(executorClassName)) {
			String errorMsg = "[uts]Parameter brokerClassName not found.";
			logger.warn(errorMsg);
			writeResp(resp, WebResult.newErrorResult(errorMsg));
			return;
		}
		String jobGroup = (String) reqParams.get(HttpFields.JOB_GROUP);
		String jobName = (String) reqParams.get(HttpFields.JOB_NAME);
		String triggerGroup = (String) reqParams.get(HttpFields.TRIGGER_GROUP);
		String triggerName = (String) reqParams.get(HttpFields.TRIGGER_NAME);
		Boolean concurrentDisallowed = (Boolean) reqParams.get(HttpFields.CONCURRENT_DISALLOWED);
		JobContext jobContext = new JobContext(servletContext, fireInstanceId, jobGroup, jobName, triggerGroup,
				triggerName, executorClassName, reqParams, concurrentDisallowed == null ? false : concurrentDisallowed);
		try {
			jobExecutePool.addJobToPool(jobContext);
		} catch (RejectedExecutionException ex) {
			String errorMsg = "[uts]All Job thread is busy, access denied, please try other node, fireInstanceId: " + fireInstanceId;
			logger.warn(errorMsg, ex);
			WebResult result = WebResult.newErrorResult(errorMsg);
			result.put(HttpFields.CLIENT_NODE_NAME, getClientNodeName());
			
			StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            Throwable rootException = ExceptionUtils.getRootCause(ex);
			result.put(HttpFields.EXCEPTION_STACK_TRACE, sw.toString());
			result.put(HttpFields.ROOT_EXCEPTION_CLASS_NAME, rootException.getClass().getName());
			String rootMsg = StringUtils.isEmpty(rootException.getMessage()) ? "No exception message." : rootException.getMessage();
			int msgLen = rootMsg.length()>1000 ? 1000 : rootMsg.length();
			result.put(HttpFields.ROOT_EXCEPTION_MSG, rootMsg.substring(0, msgLen));
			writeResp(resp, result);
			return;
		}
		WebResult result = WebResult.newResult();
		result.put(HttpFields.CLIENT_NODE_NAME, getClientNodeName());
		writeResp(resp, result);
	}

	private String getClientNodeName() {
		String nodeIp = NetUtils.getLocalIP();
		String processorId = ManagementFactory.getRuntimeMXBean().getName();
		if (processorId != null) {
			processorId = processorId.split("@")[0];
		}
		return nodeIp + "/" + processorId;
	}

	private void writeResp(HttpServletResponse response, Object obj) throws IOException {
		String json = JsonUtils.beanToJson(obj);
		response.setContentType("application/json");
		OutputStream os = response.getOutputStream();
		try {
			os.write(json.getBytes(ClientConfig.DEFAULT_CHARSET_STRING));
		} finally {
			os.close();
		}
	}

	private String getReqBody(HttpServletRequest req) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), ClientConfig.DEFAULT_CHARSET_STRING));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

}
