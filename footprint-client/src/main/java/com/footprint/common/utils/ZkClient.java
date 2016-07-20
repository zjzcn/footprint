package com.footprint.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class ZkClient {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int DEFAULT_SESSION_TIMEOUT_MS = 60 * 1000;
	private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 15 * 1000;
	private static final int CONNECT_RETRY_INTERVAL_MS = 5000;
	private static final String DEFAULT_DATA = "";

	// Map<zkAddress, zkClient>
	private static Map<String, ZkClient> zkclients = new HashMap<String, ZkClient>();

	private CuratorFramework client;

	private ZkClient(String zkAddress, String username, String password) {
		List<AuthInfo> authInfos = new ArrayList<AuthInfo>();
		if (username != null && password != null) {
			AuthInfo authInfo = new AuthInfo("digest", (username + ":" + password).getBytes());
			authInfos.add(authInfo);
		}
		client = CuratorFrameworkFactory.builder().defaultData(DEFAULT_DATA.getBytes()).connectString(zkAddress)
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, CONNECT_RETRY_INTERVAL_MS))
				.sessionTimeoutMs(DEFAULT_SESSION_TIMEOUT_MS).connectionTimeoutMs(DEFAULT_CONNECTION_TIMEOUT_MS)
				.authorization(authInfos).build();
		client.start();
	}

	public static synchronized ZkClient getInstance(String zkAddress) {
		if (!zkclients.containsKey(zkAddress)) {
			ZkClient zkclient = new ZkClient(zkAddress, null, null);
			zkclients.put(zkAddress, zkclient);
		}
		return zkclients.get(zkAddress);
	}

	public static synchronized ZkClient getInstance(String zkAddress, String username, String password) {
		if (!zkclients.containsKey(zkAddress)) {
			ZkClient zkclient = new ZkClient(zkAddress, username, password);
			zkclients.put(zkAddress, zkclient);
		}
		return zkclients.get(zkAddress);
	}

	public CuratorFramework getCuratorClient() {
		return this.client;
	}

	public void close() {
		client.close();
	}

	public int countChildren(String path) {
		try {
			Stat stat = new Stat();
			this.readData(path, stat);
			return stat.getNumChildren();
		} catch (Exception e) {
			return -1;
		}
	}

	public void create(String path, byte[] data, CreateMode mode) {
		try {
			client.create().withMode(mode).forPath(path, data);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void createEphemeral(String path) {
		try {
			client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void createEphemeral(String path, byte[] data) {
		try {
			client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void createEphemeralSequential(String path) {
		try {
			client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void createPersistent(String path) {
		try {
			client.create().withMode(CreateMode.PERSISTENT).forPath(path);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void createPersistentIfParentsNotExists(String path) {
		try {
			client.create().withMode(CreateMode.PERSISTENT).forPath(path);
		} catch (NodeExistsException e) {
			// nothing
		} catch (NoNodeException e) {
			String parentDir = path.substring(0, path.lastIndexOf('/'));
			createPersistentIfParentsNotExists(parentDir);
			createPersistentIfParentsNotExists(path);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void createPersistent(String path, String data) {
		try {
			client.create().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes(DEFAULT_CHARSET));
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void createPersistentSequential(String path, String data) {
		try {
			client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path, data.getBytes(DEFAULT_CHARSET));
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public boolean delete(String path) {
		try {
			client.delete().forPath(path);
			return true;
		} catch (NoNodeException e) {
			return false;
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public boolean deleteRecursive(String path) {
		List<String> children;
		try {
			children = getChildren(path);
		} catch (ZkException e) {
			if (e.getCause() instanceof NoNodeException) {
				return false;
			}
			throw e;
		}

		for (String subPath : children) {
			if (!deleteRecursive(path + "/" + subPath)) {
				return false;
			}
		}
		return delete(path);
	}

	public boolean exists(String path) {
		try {
			Stat stat = client.checkExists().forPath(path);
			return stat != null;
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public List<String> getChildren(String path) {
		try {
			return client.getChildren().forPath(path);
		} catch (NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public String readData(String path) {
		try {
			byte[] data = client.getData().forPath(path);
			return data==null ? null : new String(data, DEFAULT_CHARSET);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public String readDataReturnNullIfPathNotExists(String path) {
		try {
			byte[] data = client.getData().forPath(path);
			return data==null ? null : new String(data, DEFAULT_CHARSET);
		} catch (NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public String readDataReturnNullIfPathNotExists(String path, Stat stat) {
		try {
			byte[] data = client.getData().storingStatIn(stat).forPath(path);
			return data==null ? null : new String(data, DEFAULT_CHARSET);
		} catch (NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}
	
	public String readData(String path, Stat stat) {
		try {
			byte[] data = client.getData().storingStatIn(stat).forPath(path);
			return data==null ? null : new String(data, DEFAULT_CHARSET);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public String readData(String path, CuratorWatcher watcher) {
		try {
			byte[] data = client.getData().usingWatcher(watcher).forPath(path);
			return data==null ? null : new String(data, DEFAULT_CHARSET);
		} catch (NoNodeException e) {
			createPersistentIfParentsNotExists(path);
			return readData(path, watcher);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public Stat writeData(String path, String data) {
		try {
			return client.setData().forPath(path, data.getBytes(DEFAULT_CHARSET));
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public Stat writeDataToCreateIfPathNotExists(String path, String data) {
		try {
			return client.setData().forPath(path, data.getBytes(DEFAULT_CHARSET));
		} catch (NoNodeException e) {
			createPersistentIfParentsNotExists(path);
			return writeData(path, data);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public Stat writeData(String path, String data, int expectedVersion) {
		try {
			return client.setData().withVersion(expectedVersion).forPath(path, data.getBytes(DEFAULT_CHARSET));
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	/**
	 * 
	 * @param path
	 * @param perms
	 *            see {@link ZooDefs.Perms}
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	public void setDigestACL(String path, int perms, String username, String password) throws Exception {
		Id id = new Id("digest", DigestAuthenticationProvider.generateDigest(username + ":" + password));
		ACL acl = new ACL(perms, id);
		List<ACL> acls = new ArrayList<ACL>();
		acls.add(acl);
		client.setACL().withACL(acls).forPath(path);
	}

	public boolean isConnected() {
		if (client.getState() != CuratorFrameworkState.STARTED) {
			return false;
		}
		return true;
	}

	public static class ZkException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public ZkException() {
			super();
		}

		public ZkException(String message, Throwable cause) {
			super(message, cause);
		}

		public ZkException(String message) {
			super(message);
		}

		public ZkException(Throwable cause) {
			super(cause);
		}
	}

	public static void main(String[] args) {
		ZkClient client = ZkClient.getInstance("10.27.25.161:2181", "zjz", "123");
		client.createPersistentIfParentsNotExists("/admin/test/dddd");
	}
}
