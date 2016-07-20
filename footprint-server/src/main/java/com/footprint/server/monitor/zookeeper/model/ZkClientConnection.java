package com.footprint.server.monitor.zookeeper.model;

import com.footprint.server.common.BaseBean;

public class ZkClientConnection extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String sourceIP;
	private Long avgLatencyMs;
	private Long minLatencyMs;
	private Long maxLatencyMs;
	private String[] ephemeralNodes;
	private String lastOperation;

	private Long packetsReceived;
	private Long packetsSent;

	private String startedTime;
	private String lastResponseTime;

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public Long getAvgLatencyMs() {
		return avgLatencyMs;
	}

	public void setAvgLatencyMs(Long avgLatencyMs) {
		this.avgLatencyMs = avgLatencyMs;
	}

	public Long getMinLatencyMs() {
		return minLatencyMs;
	}

	public void setMinLatencyMs(Long minLatency) {
		this.minLatencyMs = minLatency;
	}

	public Long getMaxLatencyMs() {
		return maxLatencyMs;
	}

	public void setMaxLatencyMs(Long maxLatency) {
		this.maxLatencyMs = maxLatency;
	}

	public String[] getEphemeralNodes() {
		return ephemeralNodes;
	}

	public void setEphemeralNodes(String[] ephemeralNodes) {
		this.ephemeralNodes = ephemeralNodes;
	}

	public String getLastOperation() {
		return lastOperation;
	}

	public void setLastOperation(String lastOperation) {
		this.lastOperation = lastOperation;
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

	public String getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(String startedTime) {
		this.startedTime = startedTime;
	}

	public String getLastResponseTime() {
		return lastResponseTime;
	}

	public void setLastResponseTime(String lastResponseTime) {
		this.lastResponseTime = lastResponseTime;
	}

}
