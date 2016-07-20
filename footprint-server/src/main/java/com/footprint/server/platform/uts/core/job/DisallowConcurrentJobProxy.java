package com.footprint.server.platform.uts.core.job;

import org.quartz.DisallowConcurrentExecution;

/**
 * @author 
 * 不允许并发执行的定时任务使用本类作为job代理
 */

@DisallowConcurrentExecution
public class DisallowConcurrentJobProxy extends JobProxy {
	
}
