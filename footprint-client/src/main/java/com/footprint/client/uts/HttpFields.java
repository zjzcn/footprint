package com.footprint.client.uts;

public class HttpFields {
	//=====================job execute(server to client)=============================
	//-----------------------job execute request key-------------------------------
	public static final String FIRE_INSTANCE_ID = "fireInstanceId";
	public static final String JOB_GROUP = "jobGroup";
	public static final String JOB_NAME = "jobName";
	public static final String TRIGGER_GROUP = "triggerGroup";
	public static final String TRIGGER_NAME = "triggerName";
	public static final String CONCURRENT_DISALLOWED = "concurrentDisallowed";
	//invocation
	public static final String EXECUTOR_CLASS_NAME = "executorClassName";
	//spring invocation
	public static final String BEAN_ID = "beanId";
	public static final String METHOD_NAME = "methodName";
	//static method invocation
	public static final String CLASS_NAME = "className";
	public static final String STATIC_METHOD_NAME = "staticMethodName";
	
	//------------------------job execute response key---------------------------------
	public static final String CLIENT_NODE_NAME = "clientNodeName";
	
	//=====================test connect(server to client)===================================================
	public static final String TEST = "test";
	
	//=====================job report(client to server)=============================
	public static final String EXECUTED = "executed";
	public static final String EXCEPTION_STACK_TRACE = "exceptionStackTrace";
	public static final String ROOT_EXCEPTION_CLASS_NAME = "rootExceptionClassName";
	public static final String ROOT_EXCEPTION_MSG = "rootExceptionMsg";
	
}
