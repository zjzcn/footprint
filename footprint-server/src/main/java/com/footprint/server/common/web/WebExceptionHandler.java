package com.footprint.server.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

import com.footprint.common.WebResult;

/**
 * 统一异常解析。 负责拦截所有Rest抛出的异常。
 *  
 * @author 
 * 
 */
@ControllerAdvice
public class WebExceptionHandler {
	private Logger logger;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public WebResult handleException(Exception ex, HandlerMethod handler) {
		Class<?> clazz = handler == null ? WebExceptionHandler.class : handler.getBeanType();
		logger = LoggerFactory.getLogger(clazz);
		logger.error("WebExceptionHandler:", ex);
		return WebResult.newErrorResult(ex.getMessage());
	}
	
}
