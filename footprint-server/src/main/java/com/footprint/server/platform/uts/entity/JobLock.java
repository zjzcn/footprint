package com.footprint.server.platform.uts.entity;

import com.footprint.server.common.BaseBean;

public class JobLock extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	private String jobGroup;
	
	private String jobName;
	
	private String lockOwner;

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getLockOwner() {
		return lockOwner;
	}

	public void setLockOwner(String lockOwner) {
		this.lockOwner = lockOwner;
	}

}
