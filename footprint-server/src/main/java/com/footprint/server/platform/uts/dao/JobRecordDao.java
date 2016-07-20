package com.footprint.server.platform.uts.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.footprint.server.common.page.PageParam;
import com.footprint.server.common.page.PageResult;
import com.footprint.server.platform.uts.entity.JobRecord;

@Repository
public interface JobRecordDao {

	public void insert(JobRecord jobRecord);
	
	public int update(JobRecord jobRecord);
	
	public void delete(String fireInstanceId);
	
	public JobRecord getJobRecord(String fireInstanceId);
	
	public List<JobRecord> queryMayTimeoutList();
	
	public void cleanHistoryJob();
	
	public PageResult<JobRecord> queryList(PageParam param);
}
