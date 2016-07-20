package com.footprint.server.monitor.zookeeper.job;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.footprint.server.monitor.zookeeper.core.cmd.ZkCmdClient;
import com.footprint.server.monitor.zookeeper.core.cmd.model.Mntr;
import com.footprint.server.monitor.zookeeper.entity.ZkCluster;
import com.footprint.server.monitor.zookeeper.entity.ZkMetric;
import com.footprint.server.monitor.zookeeper.service.ZkManageService;
import com.footprint.server.monitor.zookeeper.service.ZkMetricService;

@Component("zkCollectMetricJob")
public class ZkCollectMetricJob {
	private static final Logger logger = LoggerFactory.getLogger(ZkCollectMetricJob.class);
	
	@Autowired
	private ZkManageService zkManageService;
	@Autowired
	private ZkMetricService zkMetricService;
	
	public void collect() {
		logger.info("ZkCollectMetricJob is starting.");
		for(ZkCluster c : zkManageService.getAllCluster()) {
			//serverList: ip:port,ip2:port2
			String[] nodes = c.getNodeList().split(",");
			for(String node : nodes) {
				String[] ipPort = node.split(":");
				ZkMetric metric = new ZkMetric();
				metric.setClusterId(c.getId());
				metric.setIpPort(node);
				metric.setCollectTime(new Date());
				ZkCmdClient cmd = new ZkCmdClient(ipPort[0], Integer.valueOf(ipPort[1]));
				Mntr mntr = cmd.mntr();
				metric.setNodeCount(mntr.getZnodeCount());
				metric.setWatchCount(mntr.getWatchCount());

				metric.setPacketsReceived(mntr.getPacketsReceived());
				metric.setPacketsSent(mntr.getPacketsSent());
				metric.setConnectionCount(mntr.getNumAliveConnections());
				
				zkMetricService.insert(metric);
			}
		}
		logger.info("ZkCollectMetricJob is end.");
	}
}
