package com.footprint.server.platform.sys.dao;

import org.springframework.stereotype.Repository;

import com.footprint.server.common.page.PageParam;
import com.footprint.server.common.page.PageResult;
import com.footprint.server.platform.sys.entity.Log;

@Repository
public interface LogDao {

	public void insert(Log log);
	
	public void delete(Long id);
	
	public Log getById(Long id);
	
	public PageResult<Log> queryLogs(PageParam param);
}
