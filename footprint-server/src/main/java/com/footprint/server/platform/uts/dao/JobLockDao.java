package com.footprint.server.platform.uts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.footprint.server.platform.uts.entity.JobLock;

@Repository
public interface JobLockDao {

	public void insert(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName, @Param("lockOwner") String lockOwner);
	
	public int lock(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName, @Param("lockOwner") String lockOwner);
	
	public int unlockByOwner(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName, @Param("lockOwner") String lockOwner);
	
	public int unlock(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName);
	
    public JobLock getLockInfo(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName);
    
    public List<String> getLockedJobs(@Param("jobGroup") String jobGroup);
}
