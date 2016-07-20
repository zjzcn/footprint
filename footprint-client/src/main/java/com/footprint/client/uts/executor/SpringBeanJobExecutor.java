package com.footprint.client.uts.executor;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;

import com.footprint.client.uts.JobContext;
import com.footprint.client.uts.UtsException;
import com.footprint.common.utils.StringUtils;

public class SpringBeanJobExecutor implements JobExecutor {
	public void execute(JobContext jobContext) throws UtsException {
        String beanId = (String) jobContext.getData("beanId");
        if (StringUtils.isEmpty(beanId)) {
            throw new UtsException("Can not find beanId in jobExecutionContext data.");
        }
        String methodName = (String) jobContext.getData("methodName");
        if (StringUtils.isEmpty(methodName)) {
            throw new UtsException("Can not find methodName in jobExecutionContext data.");
        }
        try {
            Class<?> webApplicationContextUtilsClass = Class.forName("org.springframework.web.context.support.WebApplicationContextUtils");
			Method getWebAppContextMethod = webApplicationContextUtilsClass.getMethod("getWebApplicationContext", ServletContext.class);
            Object appContext = getWebAppContextMethod.invoke(null, jobContext.getServletContext());
            Method getBeanMethod = appContext.getClass().getMethod("getBean", String.class);
            Object bean = getBeanMethod.invoke(appContext, beanId);
            Class<?>[] argumentTypes = (Class[]) jobContext.getData("argumentTypes");
            if (argumentTypes == null || argumentTypes.length == 0) {
                Method method = bean.getClass().getMethod(methodName);
                method.invoke(bean);
            } else {
                Method method = bean.getClass().getMethod(methodName, argumentTypes);
                Object[] arguments = (Object[]) jobContext.getData("arguments");
                method.invoke(bean, arguments);
            }
        } catch (Exception ex) {
            throw new UtsException(ex);
        }
    }
}
