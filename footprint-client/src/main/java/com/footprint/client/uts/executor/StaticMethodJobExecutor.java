package com.footprint.client.uts.executor;

import java.lang.reflect.Method;

import com.footprint.client.uts.JobContext;
import com.footprint.client.uts.UtsException;
import com.footprint.common.utils.StringUtils;

public class StaticMethodJobExecutor implements JobExecutor {

	public void execute(JobContext jobContext) throws UtsException {
        String staticClassName = (String) jobContext.getData("staticClassName");
        if (StringUtils.isEmpty(staticClassName)) {
            throw new UtsException("Can not find staticClassName in jobExecutionContext data.");
        }
        String methodName = (String) jobContext.getData("methodName");
        if (StringUtils.isEmpty(methodName)) {
            throw new UtsException("Can not find methodName in jobExecutionContext data.");
        }
        try {
            Class<?> clazz = Class.forName(staticClassName);
            Class<?>[] argumentTypes = (Class[]) jobContext.getData("argumentTypes");
            if (argumentTypes == null || argumentTypes.length == 0) {
                Method method = clazz.getMethod(methodName);
                method.invoke(null);
            } else {
                Method method = clazz.getMethod(methodName, argumentTypes);
                Object[] arguments = (Object[]) jobContext.getData("arguments");
                method.invoke(null, arguments);
            }
        } catch (Exception ex) {
            throw new UtsException(ex);
        }
    }
}
