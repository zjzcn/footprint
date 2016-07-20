package com.footprint.server.platform.uts.core.invocation;

import java.util.HashMap;
import java.util.Map;

import com.footprint.client.uts.executor.StaticMethodJobExecutor;

/**
 * @Description 静态方法方式的调用封装类，包含调用的类名、方法名、参数类型、参数等信息
 */

public class StaticMethodInvocation extends Invocation {
	
	private static final long serialVersionUID = 1L;

	private String className;

    private String staticMethodName;

    /**
     * 支持所有简单数据类型及其封装类，及简单数据类型和其封装类的数组；支持String类型及其数组
     */
    private Object[] arguments;

    /**
     * 支持所有简单数据类型及其封装类，及简单数据类型和其封装类的数组；支持String类型及其数组
     */
    private Class<?>[] argumentTypes;

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

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public Class<?>[] getArgumentTypes() {
		return argumentTypes;
	}

	public void setArgumentTypes(Class<?>[] argumentTypes) {
		this.argumentTypes = argumentTypes;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executorClassName", StaticMethodJobExecutor.class.getName());
        map.put("className", className);
        map.put("staticMethodName", staticMethodName);
        map.put("argumentTypes", argumentTypes);
        map.put("arguments", arguments);
        return map;
	}

}
