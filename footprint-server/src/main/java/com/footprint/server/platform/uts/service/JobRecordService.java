package com.footprint.server.platform.uts.service;

import java.util.List;

import com.footprint.server.common.page.PageParam;
import com.footprint.server.common.page.PageResult;
import com.footprint.server.platform.uts.entity.JobRecord;

public interface JobRecordService {

	public void initJobRecord(String fireInstanceId, String jobGroup, String jobName, String triggerGroup, String triggerName);
	
	public void reportClientReceived(String fireInstanceId, String nodeName);
	
	public void reportJobExecuting(String fireInstanceId);
	
	public void reportJobSuccess(String fireInstanceId);
	
	public void reportJobError(String fireInstanceId, String rootExceptionClassName, String rootExceptionMsg, String exceptionStackTrace);
	
	public JobRecord getJobRecord(String fireInstanceId);
	
	public void markJobTimeout(String fireInstanceId);
	
	public List<JobRecord> queryMayTimeoutList();
	
	public void cleanHistoryJob();
	
	public PageResult<JobRecord> queryList(PageParam param);
	
}
