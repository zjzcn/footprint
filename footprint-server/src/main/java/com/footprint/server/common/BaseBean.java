package com.footprint.server.common;

import java.io.Serializable;

import com.footprint.common.utils.JsonUtils;

public abstract class BaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		try {
			return JsonUtils.beanToJson(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return super.toString();
	}
	
}
