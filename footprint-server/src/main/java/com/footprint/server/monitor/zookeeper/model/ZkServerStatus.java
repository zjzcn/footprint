package com.footprint.server.monitor.zookeeper.model;

import com.footprint.server.common.BaseBean;

public class ZkServerStatus extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String hostname;
	private Integer port;
	private Boolean isCluster;
	private Boolean isLeader;
	private String clientPort;
	private Long numAliveConnections;
	private Long packetsReceived;
	private Long packetsSent;

	private int nodeCount;
	private int watchCount;

	private Long minRequestLatencyMs;
	private Long maxRequestLatencyMs;
	private Long avgRequestLatencyMs;

	private String state;
	private String startTime;
	
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getIsCluster() {
		return isCluster;
	}

	public void setIsCluster(Boolean isCluster) {
		this.isCluster = isCluster;
	}

	public Boolean getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}

	public String getClientPort() {
		return clientPort;
	}

	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}

	public Long getNumAliveConnections() {
		return numAliveConnections;
	}

	public void setNumAliveConnections(Long numAliveConnections) {
		this.numAliveConnections = numAliveConnections;
	}

	public Long getPacketsReceived() {
		return packetsReceived;
	}

	public void setPacketsReceived(Long packetsReceived) {
		this.packetsReceived = packetsReceived;
	}

	public Long getPacketsSent() {
		return packetsSent;
	}

	public void setPacketsSent(Long packetsSent) {
		this.packetsSent = packetsSent;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}

	public int getWatchCount() {
		return watchCount;
	}

	public void setWatchCount(int watchCount) {
		this.watchCount = watchCount;
	}

	public Long getMinRequestLatencyMs() {
		return minRequestLatencyMs;
	}

	public void setMinRequestLatencyMs(Long minRequestLatencyMs) {
		this.minRequestLatencyMs = minRequestLatencyMs;
	}

	public Long getMaxRequestLatencyMs() {
		return maxRequestLatencyMs;
	}

	public void setMaxRequestLatencyMs(Long maxRequestLatencyMs) {
		this.maxRequestLatencyMs = maxRequestLatencyMs;
	}

	public Long getAvgRequestLatencyMs() {
		return avgRequestLatencyMs;
	}

	public void setAvgRequestLatencyMs(Long avgRequestLatencyMs) {
		this.avgRequestLatencyMs = avgRequestLatencyMs;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
