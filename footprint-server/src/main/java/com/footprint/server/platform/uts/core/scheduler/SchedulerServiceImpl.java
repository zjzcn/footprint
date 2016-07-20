package com.footprint.server.platform.uts.core.scheduler;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.quartz.Calendar;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.footprint.server.platform.uts.core.job.DisallowConcurrentJobProxy;
import com.footprint.server.platform.uts.core.job.JobData;
import com.footprint.server.platform.uts.core.job.JobProxy;

/**
 * @author
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	public Scheduler getScheduler() {
		return schedulerFactoryBean.getScheduler();
	}

	@Override
	public JobDetail getJobDetail(String jobGroup, String jobName) {
		JobKey jobKey = new JobKey(jobName, jobGroup);
		try {
			return getScheduler().getJobDetail(jobKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void start() {
		try {
			getScheduler().start();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void startDelayed(int seconds) {
		try {
			getScheduler().startDelayed(seconds);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean isStarted() {
		try {
			return getScheduler().isStarted();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void standby() {
		try {
			getScheduler().standby();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean isInStandbyMode() {
		try {
			return getScheduler().isInStandbyMode();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void shutdown() {
		try {
			getScheduler().shutdown();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void shutdown(boolean waitForJobsToComplete) {
		try {
			getScheduler().shutdown(waitForJobsToComplete);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean isShutdown() {
		try {
			return getScheduler().isShutdown();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public SchedulerMetaData getMetaData() {
		try {
			return getScheduler().getMetaData();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public List<JobExecutionContext> getCurrentlyExecutingJobs() {
		try {
			return getScheduler().getCurrentlyExecutingJobs();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Date scheduleJob(JobDetail jobDetail, Trigger trigger) {
		try {
			Date result = getScheduler().scheduleJob(jobDetail, trigger);
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			if (jobDataMap == null) {
				return result;
			}
			JobData jobData = (JobData) jobDataMap.get(JobData.DATA_KEY);
			if (jobData == null) {
				return result;
			}
			// TASKII-4 添加触发器操作，如果任务之前是暂停的，则该触发器也应该暂停掉
			if (jobData.isPaused()) {
				getScheduler().pauseTrigger(trigger.getKey());
			}
			return result;
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Date scheduleJob(Trigger trigger) {
		try {
			Date result = getScheduler().scheduleJob(trigger);
			JobDetail jobDetail = getScheduler().getJobDetail(trigger.getJobKey());
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			if (jobDataMap == null) {
				return result;
			}
			JobData jobData = (JobData) jobDataMap.get(JobData.DATA_KEY);
			if (jobData == null) {
				return result;
			}
			// TASKII-4 添加触发器操作，如果任务之前是暂停的，则该触发器也应该暂停掉
			if (jobData.isPaused()) {
				getScheduler().pauseTrigger(trigger.getKey());
			}
			return result;
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean unscheduleJob(TriggerKey triggerKey) {
		// 代码修复，之前将getTrigger放置在scheduler.unscheduleJob之后，是获取不到trigger的，因为该trigger已经被删除
		// 所以之前系统中不存在删除触发器的日志
		try {
			Trigger trigger = getScheduler().getTrigger(triggerKey);
			if (trigger == null) {
				return false;
			}
			boolean result = getScheduler().unscheduleJob(triggerKey);
			return result;
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean unscheduleJobs(List<TriggerKey> triggerKeys) {
		// 代码修复，之前将getTrigger放置在scheduler.unscheduleJob之后，是获取不到trigger的，因为该trigger已经被删除
		// 所以之前系统中不存在删除触发器的日志
		try {
			return getScheduler().unscheduleJobs(triggerKeys);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger) {
		try {
			Date result = getScheduler().rescheduleJob(triggerKey, newTrigger);
			JobDetail jobDetail = getScheduler().getJobDetail(getScheduler().getTrigger(triggerKey).getJobKey());
			// TASKII-4 更新触发器操作，如果任务之前是暂停的，则该触发器也应该暂停掉
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			if (jobDataMap == null) {
				return result;
			}
			JobData jobData = (JobData) jobDataMap.get(JobData.DATA_KEY);
			if (jobData == null) {
				return result;
			}
			// TASKII-4 添加触发器操作，如果任务之前是暂停的，则该触发器也应该暂停掉
			if (jobData.isPaused()) {
				getScheduler().pauseTrigger(triggerKey);
			}
			return result;
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean deleteJob(JobKey jobKey) {
		try {
			return getScheduler().deleteJob(jobKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean deleteJobs(List<JobKey> jobKeys) {
		try {
			return getScheduler().deleteJobs(jobKeys);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void triggerJob(JobKey jobKey) {
		try {
			getScheduler().triggerJob(jobKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void triggerJob(JobKey jobKey, JobDataMap data) {
		try {
			getScheduler().triggerJob(jobKey, data);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	/**
	 * TASKII-4 暂停任务后添加“PAUSED”标记
	 */
	@Override
	public void pauseJob(JobKey jobKey) {
		try {
			JobDetail jobDetail = getScheduler().getJobDetail(jobKey);
			if (jobDetail == null) {
				return;
			}
			
			getScheduler().pauseJob(jobKey);
			
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			if (jobDataMap == null) {
				jobDataMap = new JobDataMap();
			}
			JobData jobData = (JobData) jobDataMap.get(JobData.DATA_KEY);
			if (jobData == null) {
				jobData = new JobData();
				jobDataMap.put(JobData.DATA_KEY, jobData);
			}
			jobData.setPaused(true);
			// 将“PAUSED”标记添加到job数据中
			getScheduler().addJob(
					JobBuilder
							.newJob(jobDetail.isConcurrentExectionDisallowed() ? DisallowConcurrentJobProxy.class
									: JobProxy.class).withIdentity(jobKey).usingJobData(jobDataMap).storeDurably()
							.build(), true);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void pauseJobs(GroupMatcher<JobKey> matcher) {
		try {
			getScheduler().pauseJobs(matcher);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void pauseTrigger(TriggerKey triggerKey) {
		try {
			getScheduler().pauseTrigger(triggerKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void pauseTriggers(GroupMatcher<TriggerKey> matcher) {
		try {
			getScheduler().pauseTriggers(matcher);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void resumeJob(JobKey jobKey) {
		try {
			JobDetail jobDetail = getScheduler().getJobDetail(jobKey);
			if (jobDetail == null) {
				return;
			}
			
			getScheduler().resumeJob(jobKey);
			
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			JobData jobData = (JobData) jobDataMap.get(JobData.DATA_KEY);
			if (jobData == null) {
				jobData = new JobData();
				jobDataMap.put(JobData.DATA_KEY, jobData);
			}
			jobData.setPaused(false);
			// 将“PAUSED”标记添加到job数据中
			getScheduler().addJob(
					JobBuilder
							.newJob(jobDetail.isConcurrentExectionDisallowed() ? DisallowConcurrentJobProxy.class
									: JobProxy.class).withIdentity(jobKey).usingJobData(jobDataMap).storeDurably()
							.build(), true);
			if (jobDataMap == null || jobData == null || jobData.isPaused()) {
				getScheduler().resumeJob(jobKey);
				jobData.setPaused(false);
			}
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void resumeJobs(GroupMatcher<JobKey> matcher) {
		try {
			getScheduler().resumeJobs(matcher);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void resumeTrigger(TriggerKey triggerKey) {
		try {
			getScheduler().resumeTrigger(triggerKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void resumeTriggers(GroupMatcher<TriggerKey> matcher) {
		try {
			getScheduler().resumeTriggers(matcher);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void pauseAll() {
		try {
			getScheduler().pauseAll();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void resumeAll() {
		try {
			getScheduler().resumeAll();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public List<String> getJobGroupNames() {
		try {
			return getScheduler().getJobGroupNames();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) {
		try {
			return getScheduler().getJobKeys(matcher);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public List<? extends Trigger> getTriggersOfJob(JobKey jobKey) {
		try {
			return getScheduler().getTriggersOfJob(jobKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public List<String> getTriggerGroupNames() {
		try {
			return getScheduler().getTriggerGroupNames();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) {
		try {
			return getScheduler().getTriggerKeys(matcher);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Set<String> getPausedTriggerGroups() {
		try {
			return getScheduler().getPausedTriggerGroups();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public JobDetail getJobDetail(JobKey jobKey) {
		try {
			return getScheduler().getJobDetail(jobKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Trigger getTrigger(TriggerKey triggerKey) {
		try {
			return getScheduler().getTrigger(triggerKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) {
		try {
			return getScheduler().getTriggerState(triggerKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers) {
		try {
			getScheduler().addCalendar(calName, calendar, replace, updateTriggers);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean deleteCalendar(String calName) {
		try {
			return getScheduler().deleteCalendar(calName);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public Calendar getCalendar(String calName) {
		try {
			return getScheduler().getCalendar(calName);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public List<String> getCalendarNames() {
		try {
			return getScheduler().getCalendarNames();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean interrupt(JobKey jobKey) {
		try {
			return getScheduler().interrupt(jobKey);
		} catch (UnableToInterruptJobException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean interrupt(String fireInstanceId) {
		try {
			return getScheduler().interrupt(fireInstanceId);
		} catch (UnableToInterruptJobException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean checkExists(JobKey jobKey) {
		try {
			return getScheduler().checkExists(jobKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public boolean checkExists(TriggerKey triggerKey) {
		try {
			return getScheduler().checkExists(triggerKey);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void clear() {
		try {
			getScheduler().clear();
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void createJobDetail(String jobName, String jobGroup, boolean concurrentDisallow, JobData jobData) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(JobData.DATA_KEY, jobData);
		try {
			JobBuilder jobBuilder = JobBuilder
					.newJob(concurrentDisallow ? DisallowConcurrentJobProxy.class : JobProxy.class)
					.withIdentity(jobName, jobGroup).usingJobData(jobDataMap).storeDurably();
			getScheduler().addJob(jobBuilder.build(), false);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public void updateJobDetail(String jobName, String jobGroup, boolean concurrentDisallow, JobData jobData) {
		try {
			JobDetail jobDetail = getScheduler().getJobDetail(new JobKey(jobName, jobGroup));
			if (jobDetail == null) {
				throw new RuntimeException(MessageFormat.format("getJobDetail() is null, jobGroup[{}], jobName[{}].",
						jobGroup, jobName));
			}
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			if (jobDataMap == null) {
				jobDataMap = new JobDataMap();
			}
			jobDataMap.put(JobData.DATA_KEY, jobData);
			JobBuilder jobBuilder = JobBuilder
					.newJob(concurrentDisallow ? DisallowConcurrentJobProxy.class : JobProxy.class)
					.withIdentity(jobName, jobGroup).usingJobData(jobDataMap).storeDurably();
			getScheduler().addJob(jobBuilder.build(), true);
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
	}

	@Override
	public JobData getJobData(String jobName, String jobGroup) {
		JobDetail jobDetail;
		try {
			jobDetail = getScheduler().getJobDetail(new JobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		if (jobDataMap == null) {
			return null;
		}
		return (JobData) jobDataMap.get(JobData.DATA_KEY);
	}

	@Override
	public boolean isJobConcurrentDisallowed(String jobName, String jobGroup) {
		JobDetail jobDetail;
		try {
			jobDetail = getScheduler().getJobDetail(new JobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			throw new JobException(e);
		}
		return jobDetail.isConcurrentExectionDisallowed();
	}
}
