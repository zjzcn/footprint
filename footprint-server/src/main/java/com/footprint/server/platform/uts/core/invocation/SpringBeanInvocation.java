package com.footprint.server.platform.uts.core.invocation;

import java.util.HashMap;
import java.util.Map;

import com.footprint.client.uts.executor.SpringBeanJobExecutor;

/**
 * @author <a href="mailto:ljw79618@gmail.com">L.J.W</a>
 * @Description Spring bean 方式的调用封装类，包含调用的beanId，方法名，参数类型、参数等信息
 */

public class SpringBeanInvocation extends Invocation {

    private static final long serialVersionUID = 5337610994697199526L;

    private String beanId;

    private String methodName;

    /**
     * 支持所有简单数据类型及其封装类，及简单数据类型和其封装类的数组；支持String类型及其数组
     */
    private Object[] arguments;

    /**
     * 支持所有简单数据类型及其封装类，及简单数据类型和其封装类的数组；支持String类型及其数组
     */
    private Class<?>[] argumentTypes;

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

    public Class<?>[] getArgumentTypes() {
        return argumentTypes;
    }

    public void setArgumentTypes(Class<?>[] argumentTypes) {
        this.argumentTypes = argumentTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("executorClassName", SpringBeanJobExecutor.class.getName());
        map.put("beanId", beanId);
        map.put("methodName", methodName);
        map.put("argumentTypes", argumentTypes);
        map.put("arguments", arguments);
        return map;
	}
	
}
