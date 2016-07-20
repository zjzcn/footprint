package com.footprint.server.platform.uts.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.footprint.client.uts.HttpFields;
import com.footprint.common.WebResult;
import com.footprint.server.platform.uts.core.lock.JobLockService;
import com.footprint.server.platform.uts.core.scheduler.SchedulerService;
import com.footprint.server.platform.uts.entity.JobRecord;
import com.footprint.server.platform.uts.service.JobRecordService;

@Controller
@RequestMapping("/uts")
public class JobClientController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private JobLockService jobLockService;
	@Autowired
	private JobRecordService jobRecortService;
	
    @RequestMapping(value = "/validateJob")
    @ResponseBody
    public WebResult validateJob(@RequestBody Map<String, Object> reqMap){
        String fireInstanceId = (String)reqMap.get(HttpFields.FIRE_INSTANCE_ID);
        JobRecord jobRecord = jobRecortService.getJobRecord(fireInstanceId);
        if(jobRecord != null && (JobRecord.JOB_STATE_INIT.equals(jobRecord.getJobState())
        		|| JobRecord.JOB_STATE_CLIENT_RECEIVED.equals(jobRecord.getJobState()))) {
        	return WebResult.newResult();
        }
        logger.warn("validateJob failure, fireInstanceId: {}", fireInstanceId);
        return WebResult.newErrorResult(null);
    }
    
    @RequestMapping(value = "/reportJob")
    @ResponseBody
    public WebResult recortJob(@RequestBody List<Map<String, Object>> instances) {
        for (Map<String, Object> instance : instances) {
            String fireInstanceId = (String) instance.get(HttpFields.FIRE_INSTANCE_ID);
            String jobGroup = (String) instance.get(HttpFields.JOB_GROUP);
            String jobName = (String) instance.get(HttpFields.JOB_NAME);
            Boolean isExecuted = (Boolean) instance.get(HttpFields.EXECUTED);
            try {
                if (isExecuted) {
                    String exceptionStackTrace = (String) instance.get("exceptionStackTrace");
                    String rootExceptionClassName = (String) instance.get("rootExceptionClassName");
                    String rootExceptionMsg = (String) instance.get("rootExceptionMsg");
                    JobDetail jobDetail = schedulerService.getJobDetail(new JobKey(jobName, jobGroup));
                    if (jobDetail != null && jobDetail.isConcurrentExectionDisallowed()) {
                        if (jobLockService.unlockByOwner(jobGroup, jobName, fireInstanceId))
                            logger.info("Unlock successfully, jobGroup is " + jobGroup +
                                    ",jobName is " + jobName + ",fireInstanceId is " + fireInstanceId);
                    }
                    //若异常堆栈字段值为空，则记入执行成功，并且有后继任务则执行后继任务；若不为空，则是执行抛出异常，根据用户配置发送告警短信、重试
                    if (StringUtils.isEmpty(exceptionStackTrace)) {
                        jobRecortService.reportJobSuccess(fireInstanceId);;
                    } else {
                    	// 截取异常堆栈信息，防止内容过长，导致sql异常
                    	if(exceptionStackTrace.length() > 4000){
                    		exceptionStackTrace = exceptionStackTrace.substring(0, 4000) + "[uts]";
                    	}
                        jobRecortService.reportJobError(fireInstanceId, rootExceptionClassName, rootExceptionMsg, exceptionStackTrace);
                    }
                } else {
                    jobRecortService.reportJobExecuting(fireInstanceId);
                }
            } catch (Exception ex) {
                logger.error("Exception occur when handler Job executing report," +
                        " fireInstanceId is " + fireInstanceId, ex);
                return WebResult.newErrorResult(ex.getMessage());
            }
        }
        return WebResult.newResult();
    }

}
