package com.footprint.server.platform.uts.core.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;

import com.footprint.server.platform.uts.core.job.JobData;

public interface SchedulerService {

	public Scheduler getScheduler();
	
	public JobDetail getJobDetail(String jobGroup, String jobName);

	public void start();

	public void startDelayed(int seconds);

	public boolean isStarted();

	public void standby();

	public boolean isInStandbyMode();

	public void shutdown();

	public void shutdown(boolean waitForJobsToComplete);

	public boolean isShutdown();

	public SchedulerMetaData getMetaData();

	public List<JobExecutionContext> getCurrentlyExecutingJobs();

	public Date scheduleJob(JobDetail jobDetail, Trigger trigger);

	public Date scheduleJob(Trigger trigger);
	
	public boolean unscheduleJob(TriggerKey triggerKey);

	public boolean unscheduleJobs(List<TriggerKey> triggerKeys);

	public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger);

	public boolean deleteJob(JobKey jobKey);

	public boolean deleteJobs(List<JobKey> jobKeys);

	public void triggerJob(JobKey jobKey);

	public void triggerJob(JobKey jobKey, JobDataMap data);

	/**
	 * TASKII-4 暂停任务后添加“PAUSED”标记
	 */
	public void pauseJob(JobKey jobKey);

	public void pauseJobs(GroupMatcher<JobKey> matcher);

	public void pauseTrigger(TriggerKey triggerKey);

	public void pauseTriggers(GroupMatcher<TriggerKey> matcher);

	public void resumeJob(JobKey jobKey);

	public void resumeJobs(GroupMatcher<JobKey> matcher);

	public void resumeTrigger(TriggerKey triggerKey);

	public void resumeTriggers(GroupMatcher<TriggerKey> matcher);

	public void pauseAll();

	public void resumeAll();

	public List<String> getJobGroupNames();

	public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher);

	public List<? extends Trigger> getTriggersOfJob(JobKey jobKey);

	public List<String> getTriggerGroupNames();

	public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher);

	public Set<String> getPausedTriggerGroups();

	public JobDetail getJobDetail(JobKey jobKey);

	public Trigger getTrigger(TriggerKey triggerKey);

	public Trigger.TriggerState getTriggerState(TriggerKey triggerKey);

	public void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers);

	public boolean deleteCalendar(String calName);

	public Calendar getCalendar(String calName);

	public List<String> getCalendarNames();

	public boolean interrupt(JobKey jobKey);

	public boolean interrupt(String fireInstanceId);

	public boolean checkExists(JobKey jobKey);

	public boolean checkExists(TriggerKey triggerKey);

	public void clear();

	public void createJobDetail(String jobName, String jobGroup, boolean concurrentDisallow, JobData jobData);
	
	public void updateJobDetail(String jobName, String jobGroup, boolean concurrentDisallow, JobData jobData);

	public JobData getJobData(String jobName, String jobGroup);
	
	public boolean isJobConcurrentDisallowed(String jobName, String jobGroup);
}