package com.footprint.server.platform.uts.core.lock;

import java.util.List;

/**
 * 对于不允许并发执行的任务，增加额外的数据库加锁功能，
 * 任务都是通过httpclient发送到业务系统，所以quartz的锁在发送完请求就已经结束，
 * 需要做额外的锁来实现，直到业务系统返回job执行结果，锁才结束。
 */
public interface JobExecutionLock {

    public boolean tryLock(String jobGroup, String jobName, String lockOwner);

    public void unlock(String jobGroup, String jobName);

    public boolean unlockByOwner(String jobGroup, String jobName, String lockOwner);

    public boolean isLocked(String jobGroup, String jobName);
    
    /**
     * 获取被锁定的任务名称
     *
     * @param jobGroup
     * @return 被锁定的任务名称集合
     */
    public List<String> getLockedJobs(String jobGroup);
}
