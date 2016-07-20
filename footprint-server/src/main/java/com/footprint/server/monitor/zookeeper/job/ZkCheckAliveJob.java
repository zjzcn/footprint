package com.footprint.server.monitor.zookeeper.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.footprint.server.monitor.zookeeper.core.cmd.ZkCmdClient;
import com.footprint.server.monitor.zookeeper.entity.ZkCluster;
import com.footprint.server.monitor.zookeeper.service.ZkManageService;

@Component("zkCheckAliveJob")
public class ZkCheckAliveJob {
	private static final Logger logger = LoggerFactory.getLogger(ZkCheckAliveJob.class);
	
	@Autowired
	private ZkManageService zkManageService;
	
	public void check() {
		logger.info("ZkCheckAliveJob is starting.");
		for(ZkCluster c : zkManageService.getAllCluster()) {
			//serverList: ip:port,ip2:port2
			String[] nodes = c.getNodeList().split(",");
			for(String node : nodes) {
				String[] ipPort = node.split(":");
				ZkCmdClient cmd = new ZkCmdClient(ipPort[0], Integer.valueOf(ipPort[1]));
				boolean isAlive = cmd.ruok();
				if(!isAlive) {
					System.out.println("死了？");
				} else {
					System.out.println("没死！");
				}
			}
		}
		logger.info("ZkCheckAliveJob is end.");
	}
}
