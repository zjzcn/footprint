package com.footprint.server.platform.uaa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.footprint.server.platform.uaa.entity.Role;

@Repository
public interface RoleDao {

	public void insert(Role role);
	
	public int update(Role role);
	
	public void delete(Long id);
	
	public Role getById(Long id);
	
	public List<Role> queryListByUserId(Long userId);
	
	public List<String> queryPermListByRoleId(Long roleId);
}
