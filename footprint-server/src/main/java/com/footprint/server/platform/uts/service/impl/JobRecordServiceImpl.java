package com.footprint.server.platform.uts.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footprint.server.common.page.PageParam;
import com.footprint.server.common.page.PageResult;
import com.footprint.server.platform.uts.dao.JobRecordDao;
import com.footprint.server.platform.uts.entity.JobRecord;
import com.footprint.server.platform.uts.service.JobRecordService;

@Service("jobRecordService")
public class JobRecordServiceImpl implements JobRecordService {

	@Autowired
	private JobRecordDao jobRecordDao;

	@Override
	public void initJobRecord(String fireInstanceId, String jobGroup, String jobName, String triggerGroup,
			String triggerName) {
		JobRecord jobRecord = new JobRecord();
		Date now = new Date();
		jobRecord.setFireInstanceId(fireInstanceId);
		jobRecord.setJobGroup(jobGroup);
		jobRecord.setJobName(jobName);
		jobRecord.setTriggerGroup(triggerGroup);
		jobRecord.setTriggerName(triggerName);
		jobRecord.setCreateTime(now);
		jobRecord.setJobState(JobRecord.JOB_STATE_INIT);
		jobRecordDao.insert(jobRecord);
	}

	@Override
	public void reportClientReceived(String fireInstanceId, String clientNodeName) {
		JobRecord jobRecord = jobRecordDao.getJobRecord(fireInstanceId);
		jobRecord.setJobState(JobRecord.JOB_STATE_CLIENT_RECEIVED);
		jobRecord.setClientNodeName(clientNodeName);
		Date now = new Date();
		jobRecord.setClientReceivedTime(now);
		jobRecordDao.update(jobRecord);
	}
	
	@Override
	public void reportJobExecuting(String fireInstanceId) {
		JobRecord jobRecord = jobRecordDao.getJobRecord(fireInstanceId);
		jobRecord.setJobState(JobRecord.JOB_STATE_EXECUTING);
		jobRecord.setLastReportTime(new Date());
		jobRecordDao.update(jobRecord);
	}

	@Override
	public void reportJobSuccess(String fireInstanceId) {
		JobRecord jobRecord = jobRecordDao.getJobRecord(fireInstanceId);
		Date now = new Date();
		jobRecord.setFinishTime(now);
		jobRecord.setLastReportTime(now);
		jobRecord.setJobState(JobRecord.JOB_STATE_SUCCESS);
		jobRecordDao.update(jobRecord);
	}

	@Override
	public void reportJobError(String fireInstanceId, String rootExceptionClassName, String rootExceptionMsg,
			String exceptionStackTrace) {
		JobRecord jobRecord = jobRecordDao.getJobRecord(fireInstanceId);
		jobRecord.setRootExceptionClass(rootExceptionClassName);
		jobRecord.setRootExceptionMsg(rootExceptionMsg);
		jobRecord.setExceptionStackTrace(exceptionStackTrace);
		jobRecord.setJobState(JobRecord.JOB_STATE_EXCEPTION);
		Date now = new Date();
		jobRecord.setLastReportTime(now);
		jobRecordDao.update(jobRecord);
	}

	@Override
	public JobRecord getJobRecord(String fireInstanceId) {
		return jobRecordDao.getJobRecord(fireInstanceId);
	}

	@Override
	public void markJobTimeout(String fireInstanceId) {
		JobRecord jobRecord = jobRecordDao.getJobRecord(fireInstanceId);
		jobRecord.setJobState(JobRecord.JOB_STATE_TIMEOUT);
		jobRecordDao.update(jobRecord);
	}

	@Override
	public List<JobRecord> queryMayTimeoutList() {
		return jobRecordDao.queryMayTimeoutList();
	}

	@Override
	public void cleanHistoryJob() {
		jobRecordDao.cleanHistoryJob();
	}

	@Override
	public PageResult<JobRecord> queryList(PageParam param) {
		return jobRecordDao.queryList(param);
	}

}
