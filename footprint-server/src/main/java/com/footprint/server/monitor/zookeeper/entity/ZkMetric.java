package com.footprint.server.monitor.zookeeper.entity;

import java.util.Date;

import com.footprint.server.common.BaseBean;

public class ZkMetric extends BaseBean {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long clusterId;
	private String ipPort;
	private Long nodeCount;
	private Long watchCount;
	private Long connectionCount;
	private Long packetsSent;
	private Long packetsReceived;
	private Date collectTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	public Long getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(Long nodeCount) {
		this.nodeCount = nodeCount;
	}

	public Long getWatchCount() {
		return watchCount;
	}

	public void setWatchCount(Long watchCount) {
		this.watchCount = watchCount;
	}

	public Long getConnectionCount() {
		return connectionCount;
	}

	public void setConnectionCount(Long connectionCount) {
		this.connectionCount = connectionCount;
	}

	public Long getPacketsSent() {
		return packetsSent;
	}

	public void setPacketsSent(Long packetsSent) {
		this.packetsSent = packetsSent;
	}

	public Long getPacketsReceived() {
		return packetsReceived;
	}

	public void setPacketsReceived(Long packetsReceived) {
		this.packetsReceived = packetsReceived;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

}
