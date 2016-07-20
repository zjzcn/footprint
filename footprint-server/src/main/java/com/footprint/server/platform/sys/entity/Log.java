package com.footprint.server.platform.sys.entity;

import com.footprint.server.common.BaseBean;

/**
 * @author zhangjz
 * @version [v1.0.0 2013-05-17]
 */
public class Log extends BaseBean {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;// 用户名

	private Integer logType;// 0:操作日志；1：异常日志

	private String clientIp;

	private String createTime;

	private String name;

	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}