package com.footprint.server.platform.uts.controller.vo;

import java.util.List;

public class JobInfo {
	public static final String INVOCATION_TYPE_SPRING_BEAN = "springBean";
	public static final String INVOCATION_TYPE_STATIC_METHOD = "staticMethod";
	
	private String jobName;
	private String jobGroup;
	private Boolean concurrentDisallowed;
	private String jobDesc;
	private String invocationUrl;
	private String invocationType;
	private String beanId;
	private String methodName;
	private String className;
	private String staticMethodName;
	private String[] argumentTypesString;
	private String[] argumentsString;
	private Boolean executing;
	private Boolean paused;
	
	private List<TriggerInfo> triggerList;
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public Boolean getConcurrentDisallowed() {
		return concurrentDisallowed;
	}

	public void setConcurrentDisallowed(Boolean concurrentDisallowed) {
		this.concurrentDisallowed = concurrentDisallowed;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getInvocationUrl() {
		return invocationUrl;
	}

	public void setInvocationUrl(String invocationUrl) {
		this.invocationUrl = invocationUrl;
	}

	public String getInvocationType() {
		return invocationType;
	}

	public void setInvocationType(String invocationType) {
		this.invocationType = invocationType;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStaticMethodName() {
		return staticMethodName;
	}

	public void setStaticMethodName(String staticMethodName) {
		this.staticMethodName = staticMethodName;
	}

	public String[] getArgumentTypesString() {
		return argumentTypesString;
	}

	public void setArgumentTypesString(String[] argumentTypesString) {
		this.argumentTypesString = argumentTypesString;
	}

	public String[] getArgumentsString() {
		return argumentsString;
	}

	public void setArgumentsString(String[] argumentsString) {
		this.argumentsString = argumentsString;
	}

	public Boolean getExecuting() {
		return executing;
	}

	public void setExecuting(Boolean executing) {
		this.executing = executing;
	}

	public List<TriggerInfo> getTriggerList() {
		return triggerList;
	}

	public void setTriggerList(List<TriggerInfo> triggerList) {
		this.triggerList = triggerList;
	}

	public Boolean getPaused() {
		return paused;
	}

	public void setPaused(Boolean paused) {
		this.paused = paused;
	}

}
