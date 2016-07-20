package com.footprint.server.monitor.zookeeper.service.impl;

import java.util.List;

import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footprint.common.utils.Assert;
import com.footprint.common.utils.ZkClient;
import com.footprint.server.monitor.zookeeper.dao.ZkClusterDao;
import com.footprint.server.monitor.zookeeper.entity.ZkCluster;
import com.footprint.server.monitor.zookeeper.model.ZkNode;
import com.footprint.server.monitor.zookeeper.service.ZkManageService;

@Service
public class ZkManageServiceImpl implements ZkManageService {

	@Autowired
	private ZkClusterDao zkClusterDao;

	@Override
	public 
	List<ZkCluster> getAllCluster() {
		return zkClusterDao.queryAll();
	}
	
	@Override
	public ZkNode getNode(Long clusterId, String path) {
		ZkClient zkClient = getZkClient(clusterId);
		Stat stat = new Stat();
		String data = zkClient.readData(path, stat);
		ZkNode zkNode = new ZkNode();
		zkNode.setPath(path);
		zkNode.setData(data);
		BeanUtils.copyProperties(stat, zkNode);
		return zkNode;
	}
	
	@Override
	public List<String> getChildren(Long clusterId, String path) {
		ZkClient zkClient = getZkClient(clusterId);
		List<String> list = zkClient.getChildren(path);
		return list;
	}
	
	@Override
	public void createNode(Long clusterId, String path, String data) {
		ZkClient zkClient = getZkClient(clusterId);
		zkClient.createPersistent(path, data);
	}
	
	@Override
	public void writeData(Long clusterId, String path, String data) {
		ZkClient zkClient = getZkClient(clusterId);
		zkClient.writeData(path, data);
	}
	
	@Override
	public void deleteNode(Long clusterId, String path) {
		ZkClient zkClient = getZkClient(clusterId);
		zkClient.delete(path);
	}
	
	@Override
	public ZkClient getZkClient(Long clusterId) {
		ZkCluster zkCluster = zkClusterDao.getZkCluster(clusterId);
		Assert.notNull(zkCluster, "无法查到ZkCluster clusterId: " + clusterId);
		String zkAddr = zkCluster.getNodeList();
		ZkClient zkClient = ZkClient.getInstance(zkAddr);
		return zkClient;
	}

}
