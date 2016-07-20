package com.footprint.server.monitor.zookeeper.core.cmd.model;

public class Wchs extends Cmd {

	private int connections;
	private int paths;
	private int watches;

	public int getConnections() {
		return connections;
	}

	public void setConnections(int connections) {
		this.connections = connections;
	}

	public int getPaths() {
		return paths;
	}

	public void setPaths(int paths) {
		this.paths = paths;
	}

	public int getWatches() {
		return watches;
	}

	public void setWatches(int watches) {
		this.watches = watches;
	}

}
