package com.footprint.server.platform.uaa.entity;

import com.footprint.server.common.BaseBean;


/**
 * @classDescription 角色类
 * @author zhangjz
 * @version [v1.0.0 2013-05-17]
 */
public class Role extends BaseBean
{
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String name;//角色名称
    
    private Boolean superAdmin;//是否为管理员
    
    private String description;//角色介绍

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

	public Boolean getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(Boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}