package com.footprint.server.monitor.zookeeper.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.footprint.server.monitor.zookeeper.dao.ZkMetricDao;

@Component("zkCleanDataJob")
public class ZkCleanDataJob {
	private static final Logger logger = LoggerFactory.getLogger(ZkCleanDataJob.class);
	
	@Autowired
	private ZkMetricDao zkMetricStatDao;
	
	public void clean() {
		logger.info("ZkCleanDataJob is starting.");
		zkMetricStatDao.cleanHistory();
		logger.info("ZkCleanDataJob is end.");
	}
	
}
