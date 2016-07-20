package com.footprint.server.monitor.zookeeper.core.cmd.model;

import java.util.ArrayList;
import java.util.List;

public class Stat extends Cmd {
	
	private List<String> clients = new ArrayList<String>();
	private int latencyMin;
	private int latencyAvg;
	private int latencyMax;
	private long receivedBytes;
	private long sentBytes;
	private long outstanding;
	private long zxidEpoch;
	private long zxidCount;
	private String mode;
	private long nodeCount;

	public List<String> getClients() {
		return clients;
	}

	public void setClients(List<String> clients) {
		this.clients = clients;
	}

	public int getLatencyMin() {
		return latencyMin;
	}

	public void setLatencyMin(int latencyMin) {
		this.latencyMin = latencyMin;
	}

	public int getLatencyAvg() {
		return latencyAvg;
	}

	public void setLatencyAvg(int latencyAvg) {
		this.latencyAvg = latencyAvg;
	}

	public int getLatencyMax() {
		return latencyMax;
	}

	public void setLatencyMax(int latencyMax) {
		this.latencyMax = latencyMax;
	}

	public long getReceivedBytes() {
		return receivedBytes;
	}

	public void setReceivedBytes(long receivedBytes) {
		this.receivedBytes = receivedBytes;
	}

	public long getSentBytes() {
		return sentBytes;
	}

	public void setSentBytes(long sentBytes) {
		this.sentBytes = sentBytes;
	}

	public long getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(long outstanding) {
		this.outstanding = outstanding;
	}

	public long getZxidEpoch() {
		return zxidEpoch;
	}

	public void setZxidEpoch(long zxidEpoch) {
		this.zxidEpoch = zxidEpoch;
	}

	public long getZxidCount() {
		return zxidCount;
	}

	public void setZxidCount(long zxidCount) {
		this.zxidCount = zxidCount;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public long getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(long nodeCount) {
		this.nodeCount = nodeCount;
	}

}
