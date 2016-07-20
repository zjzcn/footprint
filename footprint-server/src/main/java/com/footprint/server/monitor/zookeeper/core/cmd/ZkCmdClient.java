package com.footprint.server.monitor.zookeeper.core.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.common.utils.StringUtils;
import com.footprint.server.monitor.zookeeper.core.cmd.model.Cmd;
import com.footprint.server.monitor.zookeeper.core.cmd.model.Mntr;
import com.footprint.server.monitor.zookeeper.core.cmd.model.Stat;
import com.footprint.server.monitor.zookeeper.core.cmd.model.Wchs;

public class ZkCmdClient {
	private static Logger logger = LoggerFactory.getLogger(ZkCmdClient.class);
	
	private static final String CMD_WCHS = "wchs";
	private static final String CMD_RUOK = "ruok";
	private static final String CMD_STAT = "stat";
	private static final String CMD_MNTR = "mntr";
	private static final String CMD_CONS = "cons";
	private static final String CMD_CONF = "conf";
	private static final String CMD_ENVI  = "envi";
	private static final String CMD_REQS  = "reqs";
	private static final String CMD_WCHC  = "wchc";
	private static final String CMD_WCHP  = "wchp";
	private static final String CMD_DUMP  = "dump";
	
	private static Pattern NUM_P = Pattern.compile("[0-9\\.]+");
	
	private String host;
	private int port;

	public ZkCmdClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
     * Zookeeper version: 3.3.3-1073969, built on 02/23/2011 22:27 GMT 
     * Clients:
     * /1.2.37.111:43681[1](queued=0,recved=434,sent=434)
     * /10.13.44.47:54811[0](queued=0,recved=1,sent=0)
     *
     * Latency min/avg/max: 0/1/227 
     * Received: 2349 
     * Sent: 2641
     * Outstanding: 0 
     * Zxid: 0xc00000243 
     * Mode: follower 
     * Node count: 8
     */
	public Stat stat() {
		Stat stat = new Stat();
		List<String> lines = sendCmd(host, port, CMD_STAT);
		stat.setContent(lines);
		for (String line : lines) {
			if(line.startsWith( "/" ) && StringUtils.containsIp(line)) {
				stat.getClients().add(line);
			} else if(line.startsWith("Latency")) {
				String value = line.split(":")[1].trim();;
				String[] lats = value.split("/");
				stat.setLatencyMin(Integer.valueOf(lats[0]));
				stat.setLatencyAvg(Integer.valueOf(lats[1]));
				stat.setLatencyMax(Integer.valueOf(lats[2]));
			} else if(line.startsWith("Received")) {
				String value = line.split(":")[1].trim();
				stat.setReceivedBytes(Long.valueOf(value));
			} else if(line.startsWith("Sent")) {
				String value = line.split(":")[1].trim();
				stat.setSentBytes(Long.valueOf(value));
			} else if(line.startsWith("Mode")) {
				String value = line.split(":")[1].trim();
				stat.setMode(value);
			} else if(line.startsWith("Node count")) {
				String value = line.split(":")[1].trim();
				stat.setNodeCount(Long.valueOf(value));
			}
		}
		return stat;
	}

	/**
	 * 175 connections watching 427 paths
	 * Total watches:719
	 */
	public Wchs wchs() {
		Wchs wchs = new Wchs();
		List<String> lines = sendCmd(host, port, CMD_WCHS);
		wchs.setContent(lines);
		
		Matcher m = NUM_P.matcher(StringUtils.join(lines.toArray(), "\n"));
		if(m.find()){
			wchs.setConnections(Integer.valueOf(m.group()));
		}
		if(m.find()){
			wchs.setPaths(Integer.valueOf(m.group()));
		}
		if(m.find()){
			wchs.setWatches(Integer.valueOf(m.group()));
		}
		return wchs;
	}
	
	/**
	 *zk_version	3.4.6-1569965, built on 02/20/2014 09:09 GMT
	 *zk_avg_latency	0
	 *zk_max_latency	66
	 *zk_min_latency	0
	 *zk_packets_received	2486
	 *zk_packets_sent	2485
	 *zk_num_alive_connections	3
	 *zk_outstanding_requests	0
	 *zk_server_state	standalone
	 *zk_znode_count	92
	 *zk_watch_count	1
	 *zk_ephemerals_count	0
	 *zk_approximate_data_size	4035 
	 */
	public Mntr mntr() {
		Mntr mntr = new Mntr();
		List<String> lines = sendCmd(host, port, CMD_MNTR);
		mntr.setContent(lines);
		
		for(String line : lines) {
			if(line.startsWith("zk_avg_latency")) {
				mntr.setAvgLatency(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_avg_latency")) {
				mntr.setAvgLatency(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_max_latency")) {
				mntr.setMaxLatency(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_min_latency")) {
				mntr.setMinLatency(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_packets_received")) {
				mntr.setPacketsReceived(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_packets_sent")) {
				mntr.setPacketsSent(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_num_alive_connections")) {
				mntr.setNumAliveConnections(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_outstanding_requests")) {
				mntr.setOutstandingRequests(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_server_state")) {
				mntr.setServerState(line.split("\\s+")[1]);
			} else if (line.startsWith("zk_znode_count")) {
				mntr.setZnodeCount(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_watch_count")) {
				mntr.setWatchCount(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_ephemerals_count")) {
				mntr.setEphemeralsCount(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_approximate_data_size")) {
				mntr.setApproximateDataSize(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_open_file_descriptor_count")) {
				mntr.setOpenFileDescriptorCount(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_max_file_descriptor_count")) {
				mntr.setMaxFileDescriptorCount(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_followers")) {
				mntr.setFollowers(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_synced_followers")) {
				mntr.setSyncedFollowers(Long.valueOf(line.split("\\s+")[1]));
			} else if (line.startsWith("zk_pending_syncs")) {
				mntr.setPendingSyncs(Long.valueOf(line.split("\\s+")[1]));
			}
		}
		return mntr;
	}
	
	/**
	 * imok
	 */
	public boolean ruok() {
		try {
			List<String> lines = sendCmd(host, port, CMD_RUOK);
			for(String line : lines) {
				if(line.contains("imok")) {
					return true;
				}
			}
		} catch(Exception e) {
			//NOOP
		}
		return false;
	}
	
	public Cmd cons() {
		List<String> lines = sendCmd(host, port, CMD_CONS);
		Cmd cmd = new Cmd();
		cmd.setContent(lines);
		return cmd;
	}

	public Cmd conf() {
		List<String> lines = sendCmd(host, port, CMD_CONF);
		Cmd cmd = new Cmd();
		cmd.setContent(lines);
		return cmd;
	}
	
	public Cmd envi() {
		List<String> lines = sendCmd(host, port, CMD_ENVI);
		Cmd cmd = new Cmd();
		cmd.setContent(lines);
		return cmd;
	}
	
	public Cmd reqs() {
		List<String> lines = sendCmd(host, port, CMD_REQS);
		Cmd cmd = new Cmd();
		cmd.setContent(lines);
		return cmd;
	}
	
	public Cmd wchp() {
		List<String> lines = sendCmd(host, port, CMD_WCHP);
		Cmd cmd = new Cmd();
		cmd.setContent(lines);
		return cmd;
	}
	
	public Cmd wchc() {
		List<String> lines = sendCmd(host, port, CMD_WCHC);
		Cmd cmd = new Cmd();
		cmd.setContent(lines);
		return cmd;
	}
	
	public Cmd dump() {
		List<String> lines = sendCmd(host, port, CMD_DUMP);
		Cmd cmd = new Cmd();
		cmd.setContent(lines);
		return cmd;
	}
	
	private List<String> sendCmd(String host, int port, String cmd) {
		List<String> list = new ArrayList<String>();
		Socket socket = null;
		PrintWriter os = null;
		BufferedReader is = null;
		try {
			socket = new Socket(host, port);
			os = new PrintWriter(socket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//send
			logger.info("Zk cmd sent, host: {}, port: {}, cmd: {}", host, port, cmd);
			os.println(cmd);
			os.flush();
			//recv
			String buf = null;
			while (!((buf = is.readLine()) == null)) {
				list.add(buf.trim());
			}
			logger.info("Zk cmd resp. cmd: {}, result:\n{}", cmd, StringUtils.join(list.toArray(), "\n"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
			try {
				if (os != null) {
				}
				if (is != null) {
					is.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				// NOOP
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws InterruptedException {
		ZkCmdClient cmd = new ZkCmdClient("10.27.25.161", 2181);
		cmd.mntr();
		cmd.stat();
		cmd.ruok();
		cmd.wchs();
		cmd.wchc();
		cmd.wchp();
		cmd.conf();
		cmd.dump();
		cmd.cons();
	}
}
