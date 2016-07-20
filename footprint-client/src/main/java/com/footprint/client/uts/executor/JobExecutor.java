package com.footprint.client.uts.executor;

import com.footprint.client.uts.JobContext;
import com.footprint.client.uts.UtsException;

public interface JobExecutor {

    public void execute(JobContext jobContext) throws UtsException;

}
