package com.footprint.server.monitor.zookeeper.core.cmd.model;

public class Mntr extends Cmd {

	public static final String STATE_STANDALONE = "standalone";
	public static final String STATE_LEADER = "leader";
	public static final String STATE_FOLLOWER = "follower";
	public static final String STATE_OBSERVER= "observer";
	
	private long avgLatency;
	private long maxLatency;
	private long minLatency;
	private long packetsReceived;
	private long packetsSent;
	private long numAliveConnections;
	private long outstandingRequests;
	private String serverState;
	private long znodeCount;
	private long watchCount;
	private long ephemeralsCount;
	private long approximateDataSize;
	private long openFileDescriptorCount;
	private long maxFileDescriptorCount;
	private long followers;
	private long syncedFollowers;
	private long pendingSyncs;

	public long getAvgLatency() {
		return avgLatency;
	}

	public void setAvgLatency(long avgLatency) {
		this.avgLatency = avgLatency;
	}

	public long getMaxLatency() {
		return maxLatency;
	}

	public void setMaxLatency(long maxLatency) {
		this.maxLatency = maxLatency;
	}

	public long getMinLatency() {
		return minLatency;
	}

	public void setMinLatency(long minLatency) {
		this.minLatency = minLatency;
	}

	public long getPacketsReceived() {
		return packetsReceived;
	}

	public void setPacketsReceived(long packetsReceived) {
		this.packetsReceived = packetsReceived;
	}

	public long getPacketsSent() {
		return packetsSent;
	}

	public void setPacketsSent(long packetsSent) {
		this.packetsSent = packetsSent;
	}

	public long getNumAliveConnections() {
		return numAliveConnections;
	}

	public void setNumAliveConnections(long numAliveConnections) {
		this.numAliveConnections = numAliveConnections;
	}

	public long getOutstandingRequests() {
		return outstandingRequests;
	}

	public void setOutstandingRequests(long outstandingRequests) {
		this.outstandingRequests = outstandingRequests;
	}

	public String getServerState() {
		return serverState;
	}

	public void setServerState(String serverState) {
		this.serverState = serverState;
	}

	public long getZnodeCount() {
		return znodeCount;
	}

	public void setZnodeCount(long znodeCount) {
		this.znodeCount = znodeCount;
	}

	public long getWatchCount() {
		return watchCount;
	}

	public void setWatchCount(long watchCount) {
		this.watchCount = watchCount;
	}

	public long getEphemeralsCount() {
		return ephemeralsCount;
	}

	public void setEphemeralsCount(long ephemeralsCount) {
		this.ephemeralsCount = ephemeralsCount;
	}

	public long getApproximateDataSize() {
		return approximateDataSize;
	}

	public void setApproximateDataSize(long approximateDataSize) {
		this.approximateDataSize = approximateDataSize;
	}

	public long getOpenFileDescriptorCount() {
		return openFileDescriptorCount;
	}

	public void setOpenFileDescriptorCount(long openFileDescriptorCount) {
		this.openFileDescriptorCount = openFileDescriptorCount;
	}

	public long getMaxFileDescriptorCount() {
		return maxFileDescriptorCount;
	}

	public void setMaxFileDescriptorCount(long maxFileDescriptorCount) {
		this.maxFileDescriptorCount = maxFileDescriptorCount;
	}

	public long getFollowers() {
		return followers;
	}

	public void setFollowers(long followers) {
		this.followers = followers;
	}

	public long getSyncedFollowers() {
		return syncedFollowers;
	}

	public void setSyncedFollowers(long syncedFollowers) {
		this.syncedFollowers = syncedFollowers;
	}

	public long getPendingSyncs() {
		return pendingSyncs;
	}

	public void setPendingSyncs(long pendingSyncs) {
		this.pendingSyncs = pendingSyncs;
	}

}
