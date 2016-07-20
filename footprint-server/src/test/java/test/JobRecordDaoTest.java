package test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.footprint.server.platform.uts.dao.JobRecordDao;
import com.footprint.server.platform.uts.entity.JobRecord;

public class JobRecordDaoTest extends BaseTest {

	@Autowired
	private JobRecordDao jobLockDao;
	
	@Test
	public void test() {
		JobRecord i = new JobRecord();
		i.setFireInstanceId("ddd");
		jobLockDao.insert(i);
		JobRecord j =jobLockDao.getJobRecord("ddd");
		
		Assert.assertNotNull(j);
		
	}
}
