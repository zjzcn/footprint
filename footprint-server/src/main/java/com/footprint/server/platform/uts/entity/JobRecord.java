package com.footprint.server.platform.uts.entity;

import java.util.Date;

import com.footprint.server.common.BaseBean;

/**
 * @author 定时任务执行日志实体类
 */

public class JobRecord extends BaseBean {

	private static final long serialVersionUID = 1L;

	public static final String JOB_STATE_INIT = "init";
	public static final String JOB_STATE_CLIENT_RECEIVED = "client_received";
	public static final String JOB_STATE_EXECUTING = "executing";
	public static final String JOB_STATE_SUCCESS = "success";
	public static final String JOB_STATE_EXCEPTION = "exception";
	public static final String JOB_STATE_TIMEOUT = "timeout";

	private String fireInstanceId;
	private String jobGroup;
	private String jobName;
	private String triggerGroup;
	private String triggerName;
	private String clientNodeName;
	private Date createTime;
	private Date clientReceivedTime;
	private Date lastReportTime;
	private Date finishTime;
	private String rootExceptionMsg;
	private String rootExceptionClass;
	private String exceptionStackTrace;
	private String jobState;

	public String getFireInstanceId() {
		return fireInstanceId;
	}

	public void setFireInstanceId(String fireInstanceId) {
		this.fireInstanceId = fireInstanceId;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getClientNodeName() {
		return clientNodeName;
	}

	public void setClientNodeName(String clientNodeName) {
		this.clientNodeName = clientNodeName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getClientReceivedTime() {
		return clientReceivedTime;
	}

	public void setClientReceivedTime(Date clientReceivedTime) {
		this.clientReceivedTime = clientReceivedTime;
	}

	public Date getLastReportTime() {
		return lastReportTime;
	}

	public void setLastReportTime(Date lastReportTime) {
		this.lastReportTime = lastReportTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getRootExceptionMsg() {
		return rootExceptionMsg;
	}

	public void setRootExceptionMsg(String rootExceptionMsg) {
		this.rootExceptionMsg = rootExceptionMsg;
	}

	public String getRootExceptionClass() {
		return rootExceptionClass;
	}

	public void setRootExceptionClass(String rootExceptionClass) {
		this.rootExceptionClass = rootExceptionClass;
	}

	public String getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

	public String getJobState() {
		return jobState;
	}

	public void setJobState(String jobState) {
		this.jobState = jobState;
	}

}
