package com.footprint.server.monitor.zookeeper.service;

import java.util.Date;
import java.util.List;

import com.footprint.server.monitor.zookeeper.entity.ZkMetric;

public interface ZkMetricService {
	
	public void insert(ZkMetric zkMetric);
	
	public List<ZkMetric> queryList(Date startTime, Date endTime);
	
	public void cleanHistory();
	
}