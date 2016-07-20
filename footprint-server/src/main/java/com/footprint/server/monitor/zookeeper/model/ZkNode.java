package com.footprint.server.monitor.zookeeper.model;

import com.footprint.server.common.BaseBean;

public class ZkNode extends BaseBean {
	private static final long serialVersionUID = 1L;
	
	private String path;
	private String data;
	// stat
	private long czxid;
	private long mzxid;
	private long ctime;
	private long mtime;
	private int version;
	private int cversion;
	private int aversion;
	private long ephemeralOwner;
	private int dataLength;
	private int numChildren;
	private long pzxid;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getCzxid() {
		return czxid;
	}

	public void setCzxid(long czxid) {
		this.czxid = czxid;
	}

	public long getMzxid() {
		return mzxid;
	}

	public void setMzxid(long mzxid) {
		this.mzxid = mzxid;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public long getMtime() {
		return mtime;
	}

	public void setMtime(long mtime) {
		this.mtime = mtime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getCversion() {
		return cversion;
	}

	public void setCversion(int cversion) {
		this.cversion = cversion;
	}

	public int getAversion() {
		return aversion;
	}

	public void setAversion(int aversion) {
		this.aversion = aversion;
	}

	public long getEphemeralOwner() {
		return ephemeralOwner;
	}

	public void setEphemeralOwner(long ephemeralOwner) {
		this.ephemeralOwner = ephemeralOwner;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	public long getPzxid() {
		return pzxid;
	}

	public void setPzxid(long pzxid) {
		this.pzxid = pzxid;
	}

}
