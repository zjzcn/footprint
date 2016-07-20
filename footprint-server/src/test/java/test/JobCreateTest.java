package test;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;

import com.footprint.server.platform.uts.core.invocation.SpringBeanInvocation;
import com.footprint.server.platform.uts.core.job.JobData;
import com.footprint.server.platform.uts.core.job.JobProxy;
import com.footprint.server.platform.uts.core.scheduler.SchedulerService;

public class JobCreateTest extends BaseTest {

	@Autowired
	private SchedulerService schedulerService;
	
	@Test
	public void test() {
		for(int i=0; i<5; i++) {
		try {
			SpringBeanInvocation inv = new SpringBeanInvocation();
			inv.setInvocationUrl("http://localhost:7080/footprint/utsServlet");
			inv.setBeanId("test");
			inv.setMethodName("test");
			JobData jobData = new JobData();
			jobData.setInvocation(inv);
			JobDataMap map = new JobDataMap();
			map.put(JobData.DATA_KEY, jobData);
			JobDetail jobDetail = JobBuilder.newJob(JobProxy.class).setJobData(map).storeDurably(true)
			.withIdentity(new JobKey("name"+i, "group")).build();
//			schedulerService.addJob(jobDetail, true);
			
			TriggerKey triggerKey = TriggerKey.triggerKey("name"+i, "group");
			 
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
	 
			//按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
	 
			schedulerService.scheduleJob(jobDetail, trigger);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		try {
			Thread.sleep(100000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
