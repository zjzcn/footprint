package com.footprint.server.platform.uaa.dao;

import org.springframework.stereotype.Repository;

import com.footprint.server.platform.uaa.entity.User;

@Repository
public interface UserDao {

	public void insert(User user);
	
	public int update(User user);
	
	public void delete(Long id);
	
	public User getById(Long id);
	
	public User queryByUsername(String username);
	
}
