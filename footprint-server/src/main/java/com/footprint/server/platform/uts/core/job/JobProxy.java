package com.footprint.server.platform.uts.core.job;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.client.uts.HttpFields;
import com.footprint.common.WebResult;
import com.footprint.common.utils.HttpClient;
import com.footprint.common.utils.JsonUtils;
import com.footprint.server.common.SpringContext;
import com.footprint.server.platform.uts.core.invocation.Invocation;
import com.footprint.server.platform.uts.core.lock.JobLockService;
import com.footprint.server.platform.uts.service.JobRecordService;

/**
 * @author
 * @Description 系统触发配置的客户端任务时，先触发该job，由该job准备参数、检查并发锁，然后发送调度指令给客户端，
 *              并根据返回结果做一些日志记录，以及发送指令失败时的重试及告警
 */

public class JobProxy implements Job {
	private static final Logger logger = LoggerFactory.getLogger(JobProxy.class);

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		JobRecordService jobRecortService = SpringContext.getBean(JobRecordService.class);
		
		String jobGroup = ctx.getJobDetail().getKey().getGroup();
		String jobName = ctx.getJobDetail().getKey().getName();
		String triggerGroup = ctx.getTrigger().getKey().getGroup();
		String triggerName = ctx.getTrigger().getKey().getName();
		String fireInstanceId = ctx.getFireInstanceId();
		JobLockService jobLockService = SpringContext.getBean(JobLockService.class);
		// 如果该任务不允许并发执行，尝试给该任务加锁，若任务已经加锁，即处于正在执行状态，则放弃本次执行，返回
		if (ctx.getJobDetail().isConcurrentExectionDisallowed()) {
			if (!jobLockService.tryLock(jobGroup, jobName, fireInstanceId)) {
				logger.info("locked by other executing, job will discard. jobGroup[{}], jobName[{}], fireInstanceId[{}]", jobGroup, jobName, fireInstanceId);
				return;
			}
		}
		
		// 若客户端正常反馈，则开始记录执行日志，主要是记录客户端收到指令时间
		jobRecortService.initJobRecord(fireInstanceId, jobGroup, jobName, triggerGroup, triggerName);
		
		JobDataMap jobDataMap = ctx.getJobDetail().getJobDataMap();
		JobData jobData = (JobData) jobDataMap.get(JobData.DATA_KEY);
		Invocation invocation = jobData.getInvocation();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put(HttpFields.FIRE_INSTANCE_ID, fireInstanceId);
			data.put(HttpFields.JOB_GROUP, jobGroup);
			data.put(HttpFields.JOB_NAME, jobName);
			data.put(HttpFields.TRIGGER_GROUP, triggerGroup);
			data.put(HttpFields.TRIGGER_NAME, triggerName);
			data.put(HttpFields.CONCURRENT_DISALLOWED, ctx.getJobDetail().isConcurrentExectionDisallowed());
			data.putAll(invocation.toMap());
			// 数据准备好后，向客户端发送调度指令
			String reqJson = JsonUtils.beanToJson(data);
			String respJson = HttpClient.sendByPost(invocation.getInvocationUrl(), reqJson, 30000);
			WebResult result = JsonUtils.jsonToBean(respJson, WebResult.class);
			String clientNodeName = (String) result.get(HttpFields.CLIENT_NODE_NAME);
			// 若客户端正常反馈，则开始记录执行日志，主要是记录客户端收到指令时间
			jobRecortService.reportClientReceived(fireInstanceId, clientNodeName);
			if (!result.isSuccess()) {
				logger.error("Add to Job pool error: {}", result.getErrorMsg());
				String errorMsg = (String) result.get(HttpFields.ROOT_EXCEPTION_MSG);
				String exClass = (String) result.get(HttpFields.ROOT_EXCEPTION_CLASS_NAME);
				String stack = (String) result.get(HttpFields.EXCEPTION_STACK_TRACE);
				jobRecortService.reportJobError(fireInstanceId, exClass, errorMsg, stack);
			}
			logger.info("Job command has sent successful, data: {}", JsonUtils.beanToJson(data));
		} catch (Exception ex) {
			logger.error("Can not send job command, data: {}", JsonUtils.beanToJson(data), ex);
			if (ctx.getJobDetail().isConcurrentExectionDisallowed()) {
				if (jobLockService.unlockByOwner(jobGroup, jobName, fireInstanceId)) {
					logger.info("Unlock successfully, jobGroup[{}], jobName[{}], jobOwner[{}]", jobGroup, jobName, fireInstanceId);
				}
			}
		}
	}
	
}
