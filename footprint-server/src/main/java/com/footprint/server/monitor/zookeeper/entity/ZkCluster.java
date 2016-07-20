package com.footprint.server.monitor.zookeeper.entity;

import com.footprint.server.common.BaseBean;

public class ZkCluster extends BaseBean {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String clusterName;
	private String nodeList; //host:port,host:port
	private String description;
	private Boolean enabled;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getNodeList() {
		return nodeList;
	}

	public void setNodeList(String nodeList) {
		this.nodeList = nodeList;
	}

}
