package com.footprint.server.platform.uts.task;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.footprint.server.platform.uts.core.job.JobData;
import com.footprint.server.platform.uts.core.scheduler.SchedulerService;
import com.footprint.server.platform.uts.entity.JobRecord;
import com.footprint.server.platform.uts.service.JobRecordService;

@Component
public class CheckAndCleanTask {
	private final Logger logger = LoggerFactory.getLogger(CheckAndCleanTask.class);
	
	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private JobRecordService jobRecordService;
	
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
	private Random rnd = new Random();
	
	@PostConstruct
	public void start() {
		scheduler.scheduleAtFixedRate(
			    new Runnable() {
					@Override
					public void run() {
						checkTimeoutJobRecord();
					}
				}, rnd.nextInt(60), 60, TimeUnit.SECONDS);
		
		scheduler.scheduleAtFixedRate(
			    new Runnable() {
					@Override
					public void run() {
						cleanHistoryJobRecord();
					}
				}, rnd.nextInt(24), 24, TimeUnit.HOURS);
	}
	
	private void checkTimeoutJobRecord() {
		logger.info("Start check timeout record of job.");
		try {
			List<JobRecord> records = jobRecordService.queryMayTimeoutList();
			for(JobRecord record : records) {
				String jobName = record.getJobName();
				String jobGroup = record.getJobGroup();
				String nodeName = record.getClientNodeName();
				String fireInstanceId = record.getFireInstanceId();
				JobDetail jobDetail = schedulerService.getScheduler().getJobDetail(new JobKey(jobName, jobGroup));
				if(jobDetail == null 
						|| jobDetail.getJobDataMap()==null 
						|| jobDetail.getJobDataMap().get(JobData.DATA_KEY)==null) {
					continue;
				}
				JobData jobData = (JobData)jobDetail.getJobDataMap().get(JobData.DATA_KEY);
				long startTime = record.getCreateTime().getTime();
				long now = System.currentTimeMillis();
				if(jobData.getTimeoutM() > 0 && (now-startTime)/(60*1000)>jobData.getTimeoutM()) {
					logger.warn("Found job report timeout, jobGroup[{}], jobName[{}], fireInstanceId[{}],nodeName[{}]", jobGroup, jobName, fireInstanceId, nodeName);
					jobRecordService.markJobTimeout(fireInstanceId);
				}
			}
		} catch(Exception e) {
			logger.error("Exception:", e);
		}
        logger.info("End check timeout record of job.");
	}

	private void cleanHistoryJobRecord() {
		logger.info("Start clean history record of job.");
		try {
			jobRecordService.cleanHistoryJob();
		} catch(Exception e) {
			logger.error("Exception:", e);
		}
        logger.info("End clean history record of job.");
	}
}
