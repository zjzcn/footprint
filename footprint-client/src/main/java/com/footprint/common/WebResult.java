package com.footprint.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WebResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean success;
	
	private String errorMsg;
	
	private Map<String, Object> data = new HashMap<String, Object>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void put(String key, Object value) {
		data.put(key, value);
	}
	
	public Object get(String key) {
		return data.get(key);
	}
	
	public void putAll(Map<String, ?> map) {
		data.putAll(map);
	}
	
	public static WebResult newResult() {
		WebResult result = new WebResult();
		result.setSuccess(true);
		return result;
	}
	
	public static WebResult newErrorResult(String errorMsg) {
		WebResult result = new WebResult();
		result.setSuccess(false);
		result.setErrorMsg(errorMsg);
		return result;
	}
}
