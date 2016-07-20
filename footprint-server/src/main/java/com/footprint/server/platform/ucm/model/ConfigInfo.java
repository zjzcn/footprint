package com.footprint.server.platform.ucm.model;

import java.util.Date;

import com.footprint.server.common.BaseBean;

public class ConfigInfo extends BaseBean {
	private static final long serialVersionUID = 1L;
	
	private String path;
	private String value;
	private Date createTime;
	private Date modifyTime;
	private int numChildren;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}
}
