package com.footprint.server.platform.uts.core.lock;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.footprint.server.platform.uts.dao.JobLockDao;
import com.footprint.server.platform.uts.entity.JobLock;

/**
 * @author 
 * 对于不允许并发执行的任务，定义锁相关的操作
 */
@Component
public class JobExecutionLockImpl implements JobExecutionLock {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLockImpl.class);

    @Autowired
    private JobLockDao jobLockDao;

    /**
     * 尝试锁定任务，若该任务已经记入“uts_job_lock”表则更新记录为锁定状态，若任务尚未记入表则插入状态为锁定的记录
     * {@inheritDoc}
     */
    public boolean tryLock(String jobGroup, String jobName, String lockOwner) {
        if (jobLockDao.lock(jobGroup, jobName, lockOwner)==0) {
            try {
                jobLockDao.insert(jobGroup, jobName, lockOwner);
            } catch (DuplicateKeyException ex) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用户手动解锁
     * {@inheritDoc}
     */
    public void unlock(String jobGroup, String jobName) {
        if (jobLockDao.unlock(jobGroup, jobName)==0) {
            try {
                jobLockDao.insert(jobGroup, jobName, null);
            } catch (DuplicateKeyException ex) {
                //NOOP
            }
        }
    }

    /**
     * 任务执行结束代码调用的解锁
     * {@inheritDoc}
     */
    public boolean unlockByOwner(String jobGroup, String jobName, String lockOwner) {
        if (jobLockDao.unlockByOwner(jobGroup, jobName, lockOwner)==0) {
            JobLock jobLock = jobLockDao.getLockInfo(jobGroup, jobName);
            logger.warn("Invalid unlock request: request lockOwner is: {}, current lock info is: {}", lockOwner, jobLock);
            return false;
        }
        return true;
    }

    public boolean isLocked(String jobGroup, String jobName) {
        JobLock jobLock = jobLockDao.getLockInfo(jobGroup, jobName);
        return jobLock == null || StringUtils.isBlank(jobLock.getLockOwner());
    }
    
    public List<String> getLockedJobs(String jobGroup) {
        return jobLockDao.getLockedJobs(jobGroup);
    }
}
