package com.footprint.server.platform.uts.controller;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.footprint.client.uts.HttpFields;
import com.footprint.common.WebResult;
import com.footprint.common.utils.HttpClient;
import com.footprint.common.utils.JsonUtils;
import com.footprint.server.common.page.PageResult;
import com.footprint.server.platform.uts.controller.vo.JobInfo;
import com.footprint.server.platform.uts.controller.vo.TriggerInfo;
import com.footprint.server.platform.uts.core.invocation.Invocation;
import com.footprint.server.platform.uts.core.invocation.SpringBeanInvocation;
import com.footprint.server.platform.uts.core.invocation.StaticMethodInvocation;
import com.footprint.server.platform.uts.core.job.JobData;
import com.footprint.server.platform.uts.core.lock.JobLockService;
import com.footprint.server.platform.uts.core.scheduler.SchedulerService;

/**
 * 
 * job管理controller，包括任务的创建、修改、删除、暂停、恢复、立即执行、解锁、查询， 包括触发器的创建、修改、删除
 */
@Controller
@RequestMapping("/uts")
public class JobManageController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<String, Class<?>> stringToType = new HashMap<String, Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			put("String", String.class);
			put("Integer", Integer.class);
			put("Long", Long.class);
			put("Short", Short.class);
			put("Double", Double.class);
			put("Float", Float.class);
			put("Boolean", Boolean.class);
			put("Character", Character.class);
			put("int", int.class);
			put("long", long.class);
			put("short", short.class);
			put("double", double.class);
			put("float", float.class);
			put("boolean", boolean.class);
			put("char", char.class);
			put("String[]", String[].class);
			put("Integer[]", Integer[].class);
			put("Long[]", Long[].class);
			put("Short[]", Short[].class);
			put("Double[]", Double[].class);
			put("Float[]", Float[].class);
			put("Boolean[]", Boolean[].class);
			put("Character[]", Character[].class);
			put("int[]", int[].class);
			put("long[]", long[].class);
			put("short[]", short[].class);
			put("double[]", double[].class);
			put("float[]", float[].class);
			put("boolean[]", boolean[].class);
			put("char[]", char[].class);
		}
	};

	private final Map<Class<?>, String> typeToString = new HashMap<Class<?>, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(String.class, "String");
			put(Integer.class, "Integer");
			put(Long.class, "Long");
			put(Short.class, "Short");
			put(Double.class, "Double");
			put(Float.class, "Float");
			put(Boolean.class, "Boolean");
			put(Character.class, "Character");
			put(int.class, "int");
			put(long.class, "long");
			put(short.class, "short");
			put(double.class, "double");
			put(float.class, "float");
			put(boolean.class, "boolean");
			put(char.class, "char");
			put(String[].class, "String[]");
			put(Integer[].class, "Integer[]");
			put(Long[].class, "Long[]");
			put(Short[].class, "Short[]");
			put(Double[].class, "Double[]");
			put(Float[].class, "Float[]");
			put(Boolean[].class, "Boolean[]");
			put(Character[].class, "Character[]");
			put(int[].class, "int[]");
			put(long[].class, "long[]");
			put(short[].class, "short[]");
			put(double[].class, "double[]");
			put(float[].class, "float[]");
			put(boolean[].class, "boolean[]");
			put(char[].class, "char[]");
		}
	};

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private JobLockService jobLockService;

	@RequestMapping(value = "/jobList")
	@ResponseBody
	public WebResult jobList(String jobName, String jobGroup, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize) {
		GroupMatcher<JobKey> jobGroupMatcher = null;
		if (StringUtils.isBlank(jobGroup)) {
			jobGroupMatcher = GroupMatcher.anyJobGroup();
		} else {
			jobGroupMatcher = GroupMatcher.jobGroupContains(jobGroup);
		}
		Set<JobKey> jobSet = schedulerService.getJobKeys(jobGroupMatcher);

		Set<JobKey> jobKeySet = new HashSet<JobKey>();
		if (StringUtils.isBlank(jobName)) {
			jobKeySet = jobSet;
		} else {
			Iterator<JobKey> iterator = jobSet.iterator();
			JobKey key;
			while (iterator.hasNext()) {
				key = iterator.next();
				if (key.getName().contains(jobName)) {
					jobKeySet.add(key);
				}
			}
		}

		int totalCount = jobKeySet.size();
		JobKey[] jobKeyArray = new JobKey[totalCount];
		jobKeySet.toArray(jobKeyArray);
		Arrays.sort(jobKeyArray);

		List<JobInfo> jobList = new ArrayList<JobInfo>();
		for (int i = 0, j = (pageNo - 1) * pageSize; i < pageSize && j < jobKeyArray.length; i++, j++) {
			JobKey jobKey = jobKeyArray[j];
			JobInfo jobInfo = new JobInfo();
			jobInfo.setJobName(jobKey.getName());
			jobInfo.setJobGroup(jobKey.getGroup());
			jobInfo.setExecuting(jobLockService.isLocked(jobKey.getGroup(), jobKey.getName()));
			jobInfo.setConcurrentDisallowed(schedulerService.isJobConcurrentDisallowed(jobKey.getName(),
					jobKey.getGroup()));
			JobData jobData = schedulerService.getJobData(jobKey.getName(), jobKey.getGroup());
			if(jobData != null) {
				jobInfo.setPaused(jobData.isPaused());
				jobInfo.setJobDesc(jobData.getJobDesc());
				Invocation invocation = jobData.getInvocation();

				jobInfo.setInvocationUrl(invocation.getInvocationUrl());
				if (invocation instanceof SpringBeanInvocation) {
					jobInfo.setInvocationType(JobInfo.INVOCATION_TYPE_SPRING_BEAN);
					SpringBeanInvocation inv = (SpringBeanInvocation) invocation;
					jobInfo.setBeanId(inv.getBeanId());
					jobInfo.setMethodName(inv.getMethodName());
					jobInfo.setArgumentTypesString(typesToStrings(inv.getArgumentTypes()));
					jobInfo.setArgumentsString(objectsToStrings(inv.getArguments()));
				} else if (invocation instanceof StaticMethodInvocation) {
					jobInfo.setInvocationType(JobInfo.INVOCATION_TYPE_STATIC_METHOD);
					StaticMethodInvocation inv = (StaticMethodInvocation) invocation;
					jobInfo.setClassName(inv.getClassName());
					jobInfo.setStaticMethodName(inv.getStaticMethodName());
					jobInfo.setArgumentTypesString(typesToStrings(inv.getArgumentTypes()));
					jobInfo.setArgumentsString(objectsToStrings(inv.getArguments()));
				}
			}
			
			List<? extends Trigger> triggers = schedulerService.getTriggersOfJob(jobKey);

			List<TriggerInfo> triggerList = new ArrayList<TriggerInfo>();
			for (Trigger trigger : triggers) {
				if (trigger instanceof CronTrigger) {
					TriggerInfo triggerInfo = new TriggerInfo();
					triggerInfo.setTriggerGroup(trigger.getKey().getGroup());
					triggerInfo.setTriggerName(trigger.getKey().getName());
					if (trigger.getPreviousFireTime() != null) {
						triggerInfo.setLastFireTime(dateFormat.format(trigger.getPreviousFireTime()));
					}
					if (trigger.getNextFireTime() != null) {
						triggerInfo.setNextFireTime(dateFormat.format(trigger.getNextFireTime()));
					}
					triggerInfo.setCronExpression(((CronTrigger) trigger).getCronExpression());
					triggerInfo.setPriority(String.valueOf(trigger.getPriority()));
					triggerInfo.setTriggerState(schedulerService.getTriggerState(trigger.getKey()).toString());
					triggerList.add(triggerInfo);
				}
			}
			jobInfo.setTriggerList(triggerList);
			jobList.add(jobInfo);
		}
		PageResult<JobInfo> page = new PageResult<JobInfo>(pageNo, pageSize, totalCount, jobList);
		WebResult result = WebResult.newResult();
		result.put("jobList", page);
		return result;
	}

	@RequestMapping("/testConnection")
	@ResponseBody
	public WebResult testConnection(String invocationUrl) throws Exception {
		List<String> urlList = new ArrayList<String>();
		urlList.add(invocationUrl);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(HttpFields.TEST, null);
		String json = JsonUtils.beanToJson(data);
		String respJson = HttpClient.sendByPost(invocationUrl, json, 30000);
		WebResult result = JsonUtils.jsonToBean(respJson, WebResult.class);
		return result;
	}
	
	@RequestMapping("/checkJobExists")
	@ResponseBody
	public WebResult checkJobExists(String jobName, String jobGroup) {
		WebResult result = WebResult.newResult();
		boolean isExist = schedulerService.checkExists(new JobKey(jobName, jobGroup));
		result.put("isExist", isExist);
		return result;
	}

	@RequestMapping("/createJob")
	@ResponseBody
	public WebResult createJob(JobInfo jobInfo) {
		JobKey jobKey = new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup());
		boolean isExists = schedulerService.checkExists(jobKey);
		if (isExists) {
			return WebResult.newErrorResult("Job is exists");
		}
		JobData jobData = new JobData();
		jobData.setJobDesc(jobInfo.getJobDesc());
		jobData.setPaused(false);
		if (JobInfo.INVOCATION_TYPE_SPRING_BEAN.equals(jobInfo.getInvocationType())) {
			SpringBeanInvocation inv = new SpringBeanInvocation();
			inv.setInvocationUrl(jobInfo.getInvocationUrl());
			inv.setBeanId(jobInfo.getBeanId());
			inv.setMethodName(jobInfo.getMethodName());
			inv.setArgumentTypes(stringsToTypes(jobInfo.getArgumentTypesString()));
			inv.setArguments(stringsToObjects(jobInfo.getArgumentTypesString(), jobInfo.getArgumentsString()));
			jobData.setInvocation(inv);
		} else if (JobInfo.INVOCATION_TYPE_STATIC_METHOD.equals(jobInfo.getInvocationType())) {
			StaticMethodInvocation inv = new StaticMethodInvocation();
			inv.setInvocationUrl(jobInfo.getInvocationUrl());
			inv.setClassName(jobInfo.getClassName());
			inv.setStaticMethodName(jobInfo.getStaticMethodName());
			inv.setArgumentTypes(stringsToTypes(jobInfo.getArgumentTypesString()));
			inv.setArguments(stringsToObjects(jobInfo.getArgumentTypesString(), jobInfo.getArgumentsString()));
			jobData.setInvocation(inv);
		}
		schedulerService.createJobDetail(jobInfo.getJobName(), jobInfo.getJobGroup(),
				jobInfo.getConcurrentDisallowed(), jobData);
		return WebResult.newResult();
	}

	@RequestMapping("/modifyJob")
	@ResponseBody
	public WebResult modifyJob(JobInfo jobInfo) {
		JobKey jobKey = new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup());
		boolean isExists = schedulerService.checkExists(jobKey);
		if (!isExists) {
			return WebResult.newErrorResult("Job is not exists");
		}
		JobData jobData = new JobData();
		jobData.setJobDesc(jobInfo.getJobDesc());
		jobData.setPaused(false);
		if (JobInfo.INVOCATION_TYPE_SPRING_BEAN.equals(jobInfo.getInvocationType())) {
			SpringBeanInvocation inv = new SpringBeanInvocation();
			inv.setInvocationUrl(jobInfo.getInvocationUrl());
			inv.setBeanId(jobInfo.getBeanId());
			inv.setMethodName(jobInfo.getMethodName());
			inv.setArgumentTypes(stringsToTypes(jobInfo.getArgumentTypesString()));
			inv.setArguments(stringsToObjects(jobInfo.getArgumentTypesString(), jobInfo.getArgumentsString()));
			jobData.setInvocation(inv);
		} else if (JobInfo.INVOCATION_TYPE_STATIC_METHOD.equals(jobInfo.getInvocationType())) {
			StaticMethodInvocation inv = new StaticMethodInvocation();
			inv.setInvocationUrl(jobInfo.getInvocationUrl());
			inv.setClassName(jobInfo.getClassName());
			inv.setStaticMethodName(jobInfo.getStaticMethodName());
			inv.setArgumentTypes(stringsToTypes(jobInfo.getArgumentTypesString()));
			inv.setArguments(stringsToObjects(jobInfo.getArgumentTypesString(), jobInfo.getArgumentsString()));
			jobData.setInvocation(inv);
		}
		schedulerService.updateJobDetail(jobInfo.getJobName(), jobInfo.getJobGroup(),
				jobInfo.getConcurrentDisallowed(), jobData);
		return WebResult.newResult();
	}

	@RequestMapping("/pauseJob")
	@ResponseBody
	public WebResult pauseJob(String jobName, String jobGroup) {
		JobKey jobKey = new JobKey(jobName, jobGroup);
		schedulerService.pauseJob(jobKey);
		return WebResult.newResult();
	}

	@RequestMapping("/resumeJob")
	@ResponseBody
	public WebResult resumeJob(String jobName, String jobGroup) {
		JobKey jobKey = new JobKey(jobName, jobGroup);
		schedulerService.resumeJob(jobKey);
		return WebResult.newResult();
	}

	@RequestMapping("/deleteJob")
	@ResponseBody
	public WebResult deleteJob(String jobName, String jobGroup) {
		JobKey jobKey = new JobKey(jobName, jobGroup);
		boolean success = schedulerService.deleteJob(jobKey);
		if (success) {
			return WebResult.newResult();
		} else {
			return WebResult.newErrorResult(null);
		}
	}

	@RequestMapping("/createTrigger")
	@ResponseBody
	public WebResult createTrigger(TriggerInfo triggerInfo) throws Exception {
		TriggerKey triggerKey = new TriggerKey(triggerInfo.getTriggerName(), triggerInfo.getTriggerGroup());
		if (schedulerService.checkExists(triggerKey)) {
			return WebResult.newErrorResult("触发器已存在，请更换名称！");
		}
		CronExpression cronExpression;
		try {
			cronExpression = new CronExpression(triggerInfo.getCronExpression());
		} catch (ParseException e) {
			logger.error("触发规则表达式不符合规范！", e);
			return WebResult.newErrorResult("触发规则表达式不符合规范！");
		}
		Field f = CronExpression.class.getDeclaredField("seconds");
		f.setAccessible(true);
		Set<?> seconds = (Set<?>) f.get(triggerInfo.getCronExpression());
		if (seconds != null && seconds.size() > 2) {
			return WebResult.newErrorResult("触发规则表达式不符合规范,不允许30秒以内频率的任务添加！");
		}

		Date startTime, endTime = null;
		if (StringUtils.isNotBlank(triggerInfo.getStartTime())) {
			startTime = dateFormat.parse(triggerInfo.getStartTime());
		} else {
			startTime = DateBuilder.nextGivenSecondDate(new Date(), 1);
		}
		if (StringUtils.isNotBlank(triggerInfo.getEndTime())) {
			endTime = dateFormat.parse(triggerInfo.getEndTime());
		}
		int priority;
		try {
			priority = Integer.parseInt(triggerInfo.getPriority());
		} catch (Exception e) {
			priority = 5;
		}
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(triggerInfo.getTriggerName(), triggerInfo.getTriggerGroup())
				.forJob(new JobKey(triggerInfo.getJobName(), triggerInfo.getJobGroup()))
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).startAt(startTime)
				.withPriority(priority).build();
		if (endTime != null) {
			trigger = trigger.getTriggerBuilder().endAt(endTime).build();
		}
		schedulerService.scheduleJob(trigger);
		return WebResult.newResult();
	}

	@RequestMapping("/modifyTrigger")
	@ResponseBody
	public WebResult modifyTrigger(TriggerInfo triggerInfo) throws Exception {
		CronExpression cronExpression;
		try {
			cronExpression = new CronExpression(triggerInfo.getCronExpression());
		} catch (ParseException e) {
			logger.error("触发规则表达式不符合规范！", e);
			return WebResult.newErrorResult("触发规则表达式不符合规范！");
		}
		TriggerKey triggerKey = new TriggerKey(triggerInfo.getTriggerName(), triggerInfo.getTriggerGroup());
		//
		Field f = CronExpression.class.getDeclaredField("seconds");
		f.setAccessible(true);
		Set<?> seconds = (Set<?>) f.get(triggerInfo.getCronExpression());
		if (seconds != null && seconds.size() > 2) {
			return WebResult.newErrorResult("触发规则表达式不符合规范,不允许30秒以内频率的任务添加！");
		}
		@SuppressWarnings("unchecked")
		TriggerBuilder<CronTrigger> triggerBuilder = (TriggerBuilder<CronTrigger>) schedulerService.getTrigger(
				triggerKey).getTriggerBuilder();
		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
		Date startTime;
		if (StringUtils.isNotBlank(triggerInfo.getStartTime())) {
			startTime = dateFormat.parse(triggerInfo.getStartTime());
		} else {
			startTime = DateBuilder.nextGivenSecondDate(new Date(), 1);
		}
		triggerBuilder.startAt(startTime);
		Date endTime = null;
		if (StringUtils.isNotBlank(triggerInfo.getEndTime())) {
			endTime = dateFormat.parse(triggerInfo.getEndTime());
		}
		if (endTime != null) {
			triggerBuilder.endAt(endTime);
		}
		int priority;
		try {
			priority = Integer.parseInt(triggerInfo.getPriority());
		} catch (Exception e) {
			priority = 5;
		}
		triggerBuilder.withPriority(priority);
		Trigger trigger = triggerBuilder.build();
		schedulerService.rescheduleJob(triggerKey, trigger);
		return WebResult.newResult();
	}

	@RequestMapping("/deleteTrigger")
	@ResponseBody
	public WebResult deleteTrigger(String triggerName, String triggerGroup) {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		schedulerService.unscheduleJob(triggerKey);
		return WebResult.newResult();
	}
	
	@RequestMapping("/triggerNow")
	@ResponseBody
	public WebResult triggerNow(String jobName, String jobGroup) {
		if (jobLockService.isLocked(jobName, jobGroup)) {
			jobLockService.unlock(jobName, jobGroup);
		}
		JobKey jobKey = new JobKey(jobName, jobGroup);
		schedulerService.triggerJob(jobKey);
		return WebResult.newResult();
	}

	@RequestMapping("/unlockJob")
	@ResponseBody
	public WebResult unlockJob(String jobName, String jobGroup) {
		jobLockService.unlock(jobName, jobGroup);
		return WebResult.newResult();
	}
	
	// private method begin==============================================================
	private Class<?>[] stringsToTypes(String[] argTypesString) {
		if (argTypesString == null) {
			return null;
		}
		Class<?>[] argumentTypes = new Class[argTypesString.length];
		for (int i = 0; i < argTypesString.length; i++) {
			argumentTypes[i] = (Class<?>) stringToType.get(argTypesString[i]);
		}
		return argumentTypes;
	}

	private Object[] stringsToObjects(String[] argTypesString, String[] argsString) {
		if (argTypesString == null || argsString == null) {
			return null;
		}
		int alen = argTypesString.length;
		Class<?>[] argumentTypes = new Class[alen];
		Object[] arguments = new Object[alen];
		for (int i = 0; i < alen; i++) {
			argumentTypes[i] = (Class<?>) stringToType.get(argTypesString[i]);
			if (argsString[i].equals("null")) {
				arguments[i] = null;
			} else {
				arguments[i] = conversionService.convert(argsString[i], argumentTypes[i]);
			}
		}
		return arguments;
	}

	private String[] typesToStrings(Class<?>[] types) {
		if(types == null) {
			return null;
		}
		String[] typesStrArr = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			typesStrArr[i] = (String) typeToString.get(types[i]);
		}
		return typesStrArr;
	}

	private static String[] objectsToStrings(Object[] args) {
		if(args == null) {
			return null;
		}
		String[] argsStrArr = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && args[i].getClass().isArray()) {
				argsStrArr[i] = ArrayUtils.toString(args[i], "null");
				argsStrArr[i] = argsStrArr[i].substring(1, argsStrArr[i].length() - 1);
			} else {
				argsStrArr[i] = ArrayUtils.toString(args[i], "null");
			}
		}
		return argsStrArr;
	}

	// private method
	// end==============================================================

	public static void main(String[] args) {
		String expression = "0/30 * * * * ?";
		try {
			CronExpression cronExpression = new CronExpression(expression);
			System.out.println(cronExpression.getCronExpression());
			System.out.println(cronExpression.getExpressionSummary());
			Field f = CronExpression.class.getDeclaredField("seconds");
			f.setAccessible(true);
			System.out.println(f.get(cronExpression));
			System.out.println(((Set<?>) f.get(cronExpression)).size());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}