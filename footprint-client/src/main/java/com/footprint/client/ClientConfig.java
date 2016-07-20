package com.footprint.client;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.common.utils.Assert;
import com.footprint.common.utils.Props;

public class ClientConfig {
	
	public static final String DEFAULT_CHARSET_STRING = "UTF-8";
	public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_STRING);
	
	public static final String CONFIG_FILE = "/client.properties";

	private static Props props = null;

	private static Props getProps() {
		if (props == null) {
			props = new Props(CONFIG_FILE);
		}
		return props;
	}

	public static String getUtsServerUrl() {
		String val = getProps().getString("uts.server.url");
		Assert.notEmpty(val, "uts.server.url must be set in " + ClientConfig.CONFIG_FILE);
		return val;
	}

	public static Integer getUtsCoreJobThreads() {
		return getProps().getInt("uts.core.job.threads", 2);
	}

	public static Integer getUtsMaxJobThreads() {
		return getProps().getInt("uts.max.job.threads", 4);
	}

	public static Integer getUtsJobQueueSize() {
		return props.getInt("uts.job.queue.size", 0);
	}

	public static String getUcmZkAddress() {
		String val = getProps().getString("ucm.zk.address");
		Assert.notEmpty(val, "ucm.zk.address must be set in " + ClientConfig.CONFIG_FILE);
		return val;
	}

	public static String getUcmZkUsername() {
		return getProps().getString("ucm.zk.username");
	}

	public static String getUcmZkPassword() {
		return getProps().getString("ucm.zk.password");
	}
	
	public static boolean getLogIsRemote() {
		boolean val = getProps().getBoolean("log.is.remote", false);
		return val;
	}
	
	public static String getLogNodePath() {
		String val = getProps().getString("log.node.path");
		if(getLogIsRemote()) {
			Assert.notEmpty(val, "log.node.path must be set in " + ClientConfig.CONFIG_FILE);
		}
		return val;
	}
	
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger("ddddddddd");
		logger.debug(null, new RuntimeException());
	}
}
