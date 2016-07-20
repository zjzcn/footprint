package com.footprint.server.common.utils;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.management.JMX;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmxClient {
	private static final Logger logger = LoggerFactory.getLogger(JmxClient.class);

	private static Map<String, JmxClient> clientCache = new HashMap<String, JmxClient>();
	private ConnectionState connectionState = ConnectionState.DISCONNECTED;

	private String hostname = null;
	private int port = 0;
	private String username = null;
	private String password = null;
	
	private boolean hasPlatformMXBeans = false;
	private boolean supportDeadLockCheck = false;

	private JMXServiceURL jmxUrl = null;
	private JMXConnector jmxc = null;
	private MBeanServerConnection mbsc = null;

	static {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if (clientCache.isEmpty()) {
					return;
				}
				for (Entry<String, JmxClient> entry : clientCache.entrySet()) {
					JmxClient client = entry.getValue();
					if (null != client && !client.isConnected()) {
						try {
							client.disconnect();
							client.connect();
						} catch (Exception e) {
							logger.error("Reconnect Exception: ", e);
						}
					}
				}
			}
		}, 1, 1, TimeUnit.MINUTES);
	}

	private JmxClient(String hostname, int port, String username, String password) {
		// Create an RMI connector client and connect it to
		// the RMI connector mbsc
		String urlPath = "/jndi/rmi://" + hostname + ":" + port + "/jmxrmi";
		try {
			this.jmxUrl = new JMXServiceURL("rmi", "", 0, urlPath);
		} catch (MalformedURLException e) {
			throw new JmxClientException(e);
		}
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public ConnectionState getConnectionState() {
		return this.connectionState;
	}

	public boolean isConnected() {
		return connectionState.equals(ConnectionState.CONNECTED);
	}

	public synchronized void connect() {
		if(connectionState == ConnectionState.CONNECTED) {
			return;
		}
		connectionState = ConnectionState.CONNECTING;
		try {
			doConnect();
			connectionState = ConnectionState.CONNECTED;
		} catch (Exception e) {
			disconnect();
			throw new JmxClientException(e);
		}
	}

	private void doConnect() throws Exception {
		if (username == null && password == null) {
			this.jmxc = JMXConnectorFactory.connect(jmxUrl);
		} else {
			Map<String, String[]> env = new HashMap<String, String[]>();
			env.put(JMXConnector.CREDENTIALS, new String[] { username, password });
			this.jmxc = JMXConnectorFactory.connect(jmxUrl, env);
		}

		this.mbsc = jmxc.getMBeanServerConnection();
		ObjectName on = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
		this.hasPlatformMXBeans = mbsc.isRegistered(on);
		// check if it has 6.0 new APIs
		if (this.hasPlatformMXBeans) {
			MBeanOperationInfo[] mopis = mbsc.getMBeanInfo(on).getOperations();
			// look for findDeadlockedThreads operations;
			for (MBeanOperationInfo op : mopis) {
				if (op.getName().equals("findDeadlockedThreads")) {
					this.supportDeadLockCheck = true;
					break;
				}
			}
		}
		if (hasPlatformMXBeans) {
			// WORKAROUND for bug 5056632
			// Check if the access role is correct by getting a RuntimeMXBean
			getRuntimeMXBean();
		}
	}

	public synchronized void disconnect() {
		// Close MBeanServer connection
		if (jmxc != null) {
			try {
				jmxc.close();
			} catch (IOException e) {
				throw new JmxClientException(e);
			}
		}
		connectionState = ConnectionState.DISCONNECTED;
	}

	public void close() {
		disconnect();
		String key = getCacheKey(hostname, port, username, password);
		clientCache.remove(key);
	}
	
	public Object invoke(ObjectName name, String operationName, Object[] params, String[] signature) {
		Object result = null;
		try {
			result = mbsc.invoke(name, operationName, params, signature);
		} catch (Exception e) {
			throw new JmxClientException(e);
		}
		return result;
	}

	public synchronized ClassLoadingMXBean getClassLoadingMXBean() {
		if (hasPlatformMXBeans) {
			try {
				return ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.CLASS_LOADING_MXBEAN_NAME,
						ClassLoadingMXBean.class);
			} catch (IOException e) {
				throw new JmxClientException(e);
			}
		}
		return null;
	}

	public synchronized Collection<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
		List<GarbageCollectorMXBean> garbageCollectorMBeans = new ArrayList<GarbageCollectorMXBean>();
		try {
			ObjectName gcName = new ObjectName(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");
			Set<?> mbeans = mbsc.queryNames(gcName, null);
			if (mbeans != null) {
				Iterator<?> iterator = mbeans.iterator();
				while (iterator.hasNext()) {
					ObjectName on = (ObjectName) iterator.next();
					String name = ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",name="
							+ on.getKeyProperty("name");

					GarbageCollectorMXBean mBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, name,
							GarbageCollectorMXBean.class);
					garbageCollectorMBeans.add(mBean);
				}
			}
		} catch (Exception e) {
			throw new JmxClientException(e);
		}
		return garbageCollectorMBeans;
	}

	public synchronized Collection<MemoryPoolMXBean> getMemoryPoolMXBeans() throws IOException {
		List<MemoryPoolMXBean> memoryPoolMXBeans = new ArrayList<MemoryPoolMXBean>();
		ObjectName poolName = null;
		try {
			poolName = new ObjectName(ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE + ",*");
		} catch (MalformedObjectNameException e) {
			// should not reach here
			assert(false);
		}
		Set<?> mbeans = mbsc.queryNames(poolName, null);
		if (mbeans != null) {
			Iterator<?> iterator = mbeans.iterator();
			while (iterator.hasNext()) {
				ObjectName on = (ObjectName) iterator.next();
				String name = ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE + ",name=" + on.getKeyProperty("name");
				MemoryPoolMXBean mBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, name, MemoryPoolMXBean.class);
				memoryPoolMXBeans.add(mBean);
			}
		}
		return memoryPoolMXBeans;
	}

	public synchronized MemoryMXBean getMemoryMXBean() {
		if (!hasPlatformMXBeans) {
			return null;
		}
		try {
			return ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.MEMORY_MXBEAN_NAME,
					MemoryMXBean.class);
		} catch (IOException e) {
			throw new JmxClientException(e);
		}
	}

	public synchronized RuntimeMXBean getRuntimeMXBean() {
		if (!hasPlatformMXBeans) {
			return null;
		}
		try {
			return ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.RUNTIME_MXBEAN_NAME,
					RuntimeMXBean.class);
		} catch (IOException e) {
			throw new JmxClientException(e);
		}
	}

	public synchronized ThreadMXBean getThreadMXBean() {
		if (!hasPlatformMXBeans) {
			return null;
		}
		try {
			return ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.THREAD_MXBEAN_NAME,
					ThreadMXBean.class);
		} catch (IOException e) {
			throw new JmxClientException(e);
		}
	}

	public synchronized OperatingSystemMXBean getOperatingSystemMXBean() {
		if (!hasPlatformMXBeans) {
			return null;
		}
		try {
			return ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
					OperatingSystemMXBean.class);
		} catch (IOException e) {
			throw new JmxClientException(e);
		}
	}

	/**
	 * @return deadlocked thread id array
	 */
	public synchronized long[] findDeadlockedThreads() {
		ThreadMXBean tm = getThreadMXBean();
		// 测试 Java 虚拟机是否支持使用 可拥有同步器的监视
		if (supportDeadLockCheck && tm.isSynchronizerUsageSupported()) {
			return tm.findDeadlockedThreads();
		} else {
			// 处于监视器死锁状态的线程（如果有）的 ID 数组；否则返回 null。
			return tm.findMonitorDeadlockedThreads();
		}
	}

	public <T> T getMXBean(String name, Class<T> interfaceClass) {
		ObjectName objectName = null;
		try {
			objectName = new ObjectName(name);
		} catch (MalformedObjectNameException e) {
			// should not reach here
			assert(false);
		}
		return JMX.newMXBeanProxy(mbsc, objectName, interfaceClass);
	}

	public <T> T getMXBean(ObjectName objectName, Class<T> interfaceClass) {
		return JMX.newMXBeanProxy(mbsc, objectName, interfaceClass);
	}
	
	public Set<ObjectName> queryNamesByDomain(String domain) {
		ObjectName mbeanName = null;
		try {
			if (domain == null) {
				mbeanName = new ObjectName("*");
			} else {
				mbeanName = new ObjectName(domain + ":*");
			}
		} catch (MalformedObjectNameException e) {
			// should not reach here
			assert (false);
		}
		Set<ObjectName> names = null;
		try {
			names = mbsc.queryNames(mbeanName, null);
			logger.info("query ObjectName result: {}", names);
		} catch (IOException e) {
			throw new JmxClientException(e);
		}
		return names;
	}
	
	public Set<ObjectName> queryNames(String name) {
		return queryNames(name, null);
	}
	
	public Set<ObjectName> queryNames(String name, Class<?> interfaceClass) {
		ObjectName mbeanName = null;
		try {
			if (name == null) {
				mbeanName = new ObjectName("*");
			} else {
				mbeanName = new ObjectName(name);
			}
		} catch (MalformedObjectNameException e) {
			// should not reach here
			assert (false);
		}
		Set<ObjectName> names = null;
		try {
			if(interfaceClass == null) {
				names = mbsc.queryNames(mbeanName, null);
			} else {
				names = mbsc.queryNames(mbeanName, Query.isInstanceOf(Query.value(interfaceClass.getName())));
			}
			logger.info("query ObjectName result: {}", names);
		} catch (IOException e) {
			throw new JmxClientException(e);
		}
		return names;
	}
	
	////////////////////// static method ////////////////////
	public static synchronized JmxClient getInstance(String hostname, int port, String username, String password) {
		String key = getCacheKey(hostname, port, username, password);
		JmxClient jmxClient = clientCache.get(key);
		if (jmxClient == null) {
			jmxClient = new JmxClient(hostname, port, username, password);
			clientCache.put(key, jmxClient);
		}
		if(!jmxClient.isConnected()) {
			jmxClient.connect();
		}
		return jmxClient;
	}

	private static String getCacheKey(String hostname, int port, String username, String password) {
		return (hostname == null ? "" : hostname) + ":" + port + ":" + (username == null ? "" : username) + ":"
				+ (password == null ? "" : password);
	}

	/////////////////////// inner class //////////////////
	public enum ConnectionState {
		DISCONNECTED, CONNECTED, CONNECTING
	}

	public static class JmxClientException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public JmxClientException() {
			super();
		}

		public JmxClientException(String message, Throwable cause) {
			super(message, cause);
		}

		public JmxClientException(String message) {
			super(message);
		}

		public JmxClientException(Throwable cause) {
			super(cause);
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		JmxClient client = JmxClient.getInstance("localhost", 5000, null, null);
		client.connect();
		System.out.println(client.getMemoryMXBean().getHeapMemoryUsage().getUsed());

//		Thread.sleep(5000);
//
//		System.out.println(client.getMemoryMXBean().getHeapMemoryUsage().getUsed());
//		Thread.sleep(5000);
//
//		System.out.println(client.getMemoryMXBean().getHeapMemoryUsage().getUsed());
		
		Set<ObjectName> set = client.queryNamesByDomain("org.apache.ZooKeeperService");
		System.out.println(set);

		Set<ObjectName> set1 = client.queryNames("org.apache.ZooKeeperService:name0=StandaloneServer_port*,name1=Connections,name2=*,name3=*");
		System.out.println(set1);
	}

}
