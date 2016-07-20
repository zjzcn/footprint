package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.server.platform.uts.dao.JobLockDao;
import com.footprint.server.platform.uts.entity.JobLock;

@Transactional(transactionManager="transactionManager")
@Rollback(true)  
public class JobLockDaoTest extends BaseTest {

	@Autowired
	private JobLockDao jobLockDao;
	
	@Test
	public void test() {
		jobLockDao.insert("ddd", "ddd", "ddddddddddd");
		jobLockDao.unlockByOwner("ddd", "ddd", "ddddddddddd");
		jobLockDao.lock("ddd", "ddd", "sssssssssssssss");
		jobLockDao.unlock("ddd", "ddd");
		jobLockDao.lock("ddd", "ddd", "sssssssssssssss");
		JobLock l = jobLockDao.getLockInfo("ddd", "ddd");
		List<String> j = jobLockDao.getLockedJobs("ddd");
		
		Assert.assertNotNull(l);
		Assert.assertTrue(!j.isEmpty());
		
	}
}
