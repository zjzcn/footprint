package com.footprint.server.platform.uts.core.job;

import com.footprint.server.common.BaseBean;
import com.footprint.server.platform.uts.core.invocation.Invocation;

public class JobData extends BaseBean {
	private static final long serialVersionUID = 1L;
	public static final String DATA_KEY = "JOB_DATA";

	private String jobDesc;
	private boolean paused;
	private int timeoutM;
	private Invocation invocation;
	
	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public Invocation getInvocation() {
		return invocation;
	}

	public void setInvocation(Invocation invocation) {
		this.invocation = invocation;
	}

	public int getTimeoutM() {
		return timeoutM;
	}

	public void setTimeoutM(int timeoutM) {
		this.timeoutM = timeoutM;
	}

}
