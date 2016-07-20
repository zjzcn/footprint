package com.footprint.server.monitor.zookeeper.service;

import java.util.List;

import com.footprint.common.utils.ZkClient;
import com.footprint.server.monitor.zookeeper.entity.ZkCluster;
import com.footprint.server.monitor.zookeeper.model.ZkNode;

public interface ZkManageService {

	List<ZkCluster> getAllCluster();
	
	ZkNode getNode(Long clusterId, String path);

	List<String> getChildren(Long clusterId, String path);

	void createNode(Long clusterId, String path, String data);

	void writeData(Long clusterId, String path, String data);

	void deleteNode(Long clusterId, String path);

	ZkClient getZkClient(Long clusterId);

}