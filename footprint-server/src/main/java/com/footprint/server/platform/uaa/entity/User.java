package com.footprint.server.platform.uaa.entity;

import com.footprint.server.common.BaseBean;

/**
 * @author zhangjz
 * @version [v1.0.0 2013-05-17]
 */
public class User extends BaseBean {
	private static final long serialVersionUID = 1L;

	// Fields
	private Long id;

	private String name;// 用户名

	private String username;// 登陆名

	private String password;// 密码

	private Integer gender;// 性别：0为女，1为男

	private String email;// email

	private String mobile;// mobile

	private String createTime;// 创建时间

	private Boolean enabled;// 用户状态 1：可用 0：禁用

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}