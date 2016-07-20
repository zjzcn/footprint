package com.footprint.server.monitor.zookeeper.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.footprint.server.monitor.zookeeper.dao.ZkMetricDao;
import com.footprint.server.monitor.zookeeper.entity.ZkMetric;
import com.footprint.server.monitor.zookeeper.service.ZkMetricService;

@Service
public class ZkMetricServiceImpl implements ZkMetricService {

	private ZkMetricDao zkMetricDao;
	
	@Override
	public void insert(ZkMetric zkMetric) {
		zkMetricDao.insert(zkMetric);

	}

	@Override
	public List<ZkMetric> queryList(Date startTime, Date endTime) {
		return zkMetricDao.queryList(startTime, endTime);
	}

	@Override
	public void cleanHistory() {
		zkMetricDao.cleanHistory();
	}

}
