package com.footprint.server.monitor.zookeeper.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.footprint.server.monitor.zookeeper.entity.ZkCluster;

@Repository
public interface ZkClusterDao {

	public void insert(ZkCluster zkCluster);
	
	public int update(ZkCluster zkCluster);
	
	public void delete(Long id);
	
	public ZkCluster getZkCluster(Long id);
	
	public List<ZkCluster> queryAll();
	
}
