package com.footprint.server.monitor.zookeeper.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.footprint.server.monitor.zookeeper.entity.ZkMetric;

@Repository
public interface ZkMetricDao {

	public void insert(ZkMetric zkMetricStat);
	
	public List<ZkMetric> queryList(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
	
	public void cleanHistory();
}
