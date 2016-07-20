package com.footprint.client.ucm;

import java.util.Date;
import java.util.List;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.client.ClientConfig;
import com.footprint.common.utils.StringUtils;
import com.footprint.common.utils.ZkClient;

/**
 * 統一配置客户端
 *
 * @author
 */
public class UcmClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String UCM_ROOT = "/ucm";
	private ZkClient zkClient;
	private static UcmClient ucmClient;

	private UcmClient(String connectString, String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			zkClient = ZkClient.getInstance(connectString);
		} else {
			zkClient = ZkClient.getInstance(connectString, username, password);
		}
	}

	public static UcmClient getInstance() {
		if (ucmClient == null) {
			String zkAddress = ClientConfig.getUcmZkAddress();
			String username = ClientConfig.getUcmZkUsername();
			String password = ClientConfig.getUcmZkUsername();
			ucmClient = new UcmClient(zkAddress, username, password);
		}
		return ucmClient;
	}

	/**
	 * 从zk上获取配置
	 * @param path
	 * @return
	 */
	public String getConfig(String path) {
		String realpath = getFullPath(path);
		String value = zkClient.readDataReturnNullIfPathNotExists(realpath);
		logger.info("[ucm]get config, path: {}, value: ", realpath, value);
		return value;
	}
	
	/**
	 * 对配置监控，如果有变化会得到通知
	 * @param path
	 * @param listener
	 * @return
	 */
	public String watchConfig(final String path, final DataListener listener) {
		final String realpath = getFullPath(path);
		logger.info("[ucm]watch config is invoked, path: {}", realpath);
		String data = zkClient.readData(realpath, new CuratorWatcher() {
			@Override
			public void process(WatchedEvent event) throws Exception {
				String data = zkClient.readData(realpath, this);
				if (event.getType() == EventType.NodeDataChanged) {
					logger.info("[ucm]watch config data changed, data:\n{}", data);
					if (listener != null) {
						listener.dataChanged(data);
					}
				}
			}
		});
		return data;
	}

	/////////////////////////////server use//////////////////////////////////////
	public ConfigNode getConfigNode(String path) {
		String realpath = getFullPath(path);
		Stat stat = new Stat();
		String data = zkClient.readDataReturnNullIfPathNotExists(realpath, stat);
		if(data == null) {
			return null;
		}
		ConfigNode cNode = new ConfigNode();
		cNode.setPath(path);
		cNode.setValue(data);
		cNode.setCreateTime(new Date(stat.getCtime()));
		cNode.setModifyTime(new Date(stat.getMtime()));
		cNode.setNumChildren(stat.getNumChildren());
		return cNode;
	}
	
	public List<String> getChildren(String parentPath) {
		String realpath = getFullPath(parentPath);
		return zkClient.getChildren(realpath);
	}
	
	public void writeConfig(String path, String value) {
		String realpath = getFullPath(path);
		zkClient.writeDataToCreateIfPathNotExists(realpath, value);
	}

	public void deleteConfig(String path) {
		String realpath = getFullPath(path);
		zkClient.delete(realpath);
	}

	public String getFullPath(String path) {
		String fullPath = path;
		fullPath = StringUtils.removeStart(fullPath, "/");
		fullPath = StringUtils.removeEnd(fullPath, "/");
		fullPath = UCM_ROOT + "/"+ fullPath;
		return fullPath;
	}
}
