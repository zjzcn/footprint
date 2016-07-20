package com.footprint.server.monitor.zookeeper.core.jmx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.management.ObjectName;

import org.apache.zookeeper.server.ConnectionMXBean;
import org.apache.zookeeper.server.DataTreeMXBean;
import org.apache.zookeeper.server.ZooKeeperServerMXBean;
import org.apache.zookeeper.server.quorum.FollowerMXBean;
import org.apache.zookeeper.server.quorum.LeaderMXBean;
import org.apache.zookeeper.server.quorum.LocalPeerMXBean;

import com.footprint.common.utils.JsonUtils;
import com.footprint.server.common.utils.JmxClient;

public class ZkJmxClient {
	private static ConcurrentMap<String, ZkJmxClient> clientCache = new ConcurrentHashMap<String, ZkJmxClient>();
	
	private JmxClient jmxClient;
	private Boolean isCluster;
	private Boolean isLeader;
	
	private String cacheKey = null;
	
	private ZkJmxClient(String hostname, int port, String username, String password) {
		jmxClient = JmxClient.getInstance(hostname, port, username, password);
		this.cacheKey = getCacheKey(hostname, port, username, password);
	}
	private static String getCacheKey(String hostname, int port, String username, String password) {
		return (hostname == null ? "" : hostname) + ":" + port + ":" + (username == null ? "" : username) + ":"
				+ (password == null ? "" : password);
	}
	
	public static ZkJmxClient getInstance(String hostname, int port, String username, String password) {
		String key = getCacheKey(hostname, port, username, password);
		ZkJmxClient jmxClient = clientCache.get(key);
		if (jmxClient == null) {
			jmxClient = new ZkJmxClient(hostname, port, username, password);
			clientCache.put(key, jmxClient);
		}
		return jmxClient;
	}
	
	public void close() {
		if(jmxClient != null) {
			jmxClient.close();
		}
		jmxClient = null;
		clientCache.remove(cacheKey);
	}
	
	public boolean isCluster() {
		if(isCluster == null) {
			String domain = "org.apache.ZooKeeperService";
			Set<ObjectName> names = jmxClient.queryNamesByDomain(domain);
			for(ObjectName o : names) {
				String name = o.getCanonicalName();
				if(name.contains("Leader") || name.contains("Follower")) {
					isCluster = true;
					return isCluster;
				}
			}
			isCluster = false;
			return isCluster;
		}
		return isCluster;
	}
	
	public boolean isLeader() {
		if(isLeader == null) {
			String domain = "org.apache.ZooKeeperService";
			Set<ObjectName> names = jmxClient.queryNamesByDomain(domain);
			for(ObjectName o : names) {
				String name = o.getCanonicalName();
				if(name.contains("Leader")) {
					isLeader = true;
					return isLeader;
				}
			}
			isLeader = false;
			return isLeader;
		}
		return isLeader;
	}
	
	public DataTreeMXBean getDataTreeMXBean() {
		String mxbeanName = "org.apache.ZooKeeperService:name0=StandaloneServer_port-*,name1=InMemoryDataTree";
		if(isCluster()) {
			mxbeanName = "org.apache.ZooKeeperService:name0=ReplicatedServer_id*,name1=*,name2=*,name3=InMemoryDataTree";
		}
		Set<ObjectName> names = jmxClient.queryNames(mxbeanName, DataTreeMXBean.class);
		if(names == null || names.isEmpty()) {
			return null;
		}
		DataTreeMXBean mxbean = jmxClient.getMXBean((ObjectName)names.toArray()[0], DataTreeMXBean.class);
		return mxbean;
	}
	
	public ZooKeeperServerMXBean getZooKeeperServerMXBean() {
		String mxbeanName = "org.apache.ZooKeeperService:name0=StandaloneServer_port-*";
		Class<?> clazz = ZooKeeperServerMXBean.class;
		if(isCluster()) {
			mxbeanName = "org.apache.ZooKeeperService:name0=ReplicatedServer_id*,name1=*,name2=*";
			if(isLeader()) {
				clazz = LeaderMXBean.class;
			} else {
				clazz = FollowerMXBean.class;
			}
		}
		Set<ObjectName> names = jmxClient.queryNames(mxbeanName, clazz);
		if(names == null || names.isEmpty()) {
			return null;
		}
		ZooKeeperServerMXBean mxbean = (ZooKeeperServerMXBean)jmxClient.getMXBean((ObjectName)names.toArray()[0], clazz);
		return mxbean;
	}
	
	public List<ConnectionMXBean> getConnectionMXBean() {
		String mxbeanName = "org.apache.ZooKeeperService:name0=StandaloneServer_port-*,name1=Connections,name2=*,name3=*";
		if(isCluster()) {
			mxbeanName = "org.apache.ZooKeeperService:name0=ReplicatedServer_id*,name1=*,name2=*,name3=Connections,name4=*,name5=*";
		}
		Set<ObjectName> names = jmxClient.queryNames(mxbeanName, ConnectionMXBean.class);
		if(names == null || names.isEmpty()) {
			return Collections.emptyList();
		}
		List<ConnectionMXBean> list = new ArrayList<ConnectionMXBean>();
		for(ObjectName o : names) {
			ConnectionMXBean mxbean = jmxClient.getMXBean(o, ConnectionMXBean.class);
			list.add(mxbean);
		}
		return list;
	}

	//===================Cluster====================
	public LocalPeerMXBean getLocalPeerMXBeanCluster() {
		String mxbeanName = "org.apache.ZooKeeperService:name0=ReplicatedServer_id*,name1=*";
		Set<ObjectName> names = jmxClient.queryNames(mxbeanName, LocalPeerMXBean.class);
		if(names == null || names.isEmpty()) {
			return null;
		}
		LocalPeerMXBean mxbean = jmxClient.getMXBean((ObjectName)names.toArray()[0], LocalPeerMXBean.class);
		return mxbean;
	}
	
	public static void main(String[] args) {
		ZkJmxClient client = getInstance("localhost", 5001, null, null);
		ZooKeeperServerMXBean mxbean = client.getZooKeeperServerMXBean();
		System.out.println(JsonUtils.beanToJson(mxbean));
		System.out.println(client.isCluster());
		System.out.println(client.isLeader());
		
//		FollowerMXBean mxbean1 =ZkJmxClient2.getFollowerMXBeanCluster("localhost", 5001, null, null);
//		System.out.println(JsonUtils.beanToJson(mxbean1));
//		
//		LocalPeerMXBean mxbean2 =ZkJmxClient2.getLocalPeerMXBeanCluster("localhost", 5001, null, null);
//		System.out.println(JsonUtils.beanToJson(mxbean2));
//		LocalPeerMXBean mxbean3 =ZkJmxClient2.getLocalPeerMXBeanCluster("localhost", 5002, null, null);
//		System.out.println(JsonUtils.beanToJson(mxbean3));
//		LocalPeerMXBean mxbean4 =ZkJmxClient2.getLocalPeerMXBeanCluster("localhost", 5003, null, null);
//		System.out.println(JsonUtils.beanToJson(mxbean4));
//		DataTreeMXBean mxbean5 =ZkJmxClient2.getDataTreeMXBeanCluster("localhost", 5003, null, null);
//		System.out.println(JsonUtils.beanToJson(mxbean5));
//		List<ConnectionMXBean> mxbean6 =ZkJmxClient2.getConnectionMXBeanCluster("localhost", 5002, null, null);
//		System.out.println(JsonUtils.beanToJson(mxbean6));
//		
//		List<ConnectionMXBean> mxbean7 =ZkJmxClient2.getConnectionMXBeanStandalone("localhost", 5000, null, null);
//		System.out.println(JsonUtils.beanToJson(mxbean7));
		
	}
}
