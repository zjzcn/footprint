package com.footprint.server.common.utils;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

public class RedisClient {
	
	private Pool<Jedis> jedisPool = null;
	
	public RedisClient() {
		jedisPool = new JedisPool();
	}
	
	public RedisClient(String host, int port) {
		this(host, port, null);
	}

	public RedisClient(String host, int port, String password) {
		this(host, port, password, Protocol.DEFAULT_DATABASE);
	}
	
	public RedisClient(String host, int port, String password, int db) {
		jedisPool = new JedisPool(new JedisPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT, password, db);
	}
	
	public RedisClient(String masterName, Set<String> sentinels) {
		this(masterName, sentinels, null);
	}
	
	public RedisClient(String masterName, Set<String> sentinels, int db) {
		this(masterName, sentinels, null, db);
	}
	
	public RedisClient(String masterName, Set<String> sentinels, String password) {
		this(masterName, sentinels, null, Protocol.DEFAULT_DATABASE);
	}
	
	public RedisClient(String masterName, Set<String> sentinels, String password, int db) {
		jedisPool = new JedisSentinelPool(masterName, sentinels, new JedisPoolConfig(), Protocol.DEFAULT_TIMEOUT, password, db);
	}
	
	public void destroy() {
		if(jedisPool != null) {
			jedisPool.close();
		}
	}

	public byte[] get(String key) {
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key.getBytes());
		} finally {
			jedis.close();
		}
		return value;
	}
	
	public void set(String key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key.getBytes(), value);
		} finally {
			jedis.close();
		}
	}
	
	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public void set(String key, byte[] value, long expireMS) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key.getBytes(), value);
			if (expireMS != 0) {
				jedis.pexpire(key, expireMS);
			}
		} finally {
			jedis.close();
		}
	}

	/**
	 * expire
	 * 
	 * @param key
	 * @param expire 0: ttl失效，-1: 立即删除
	 * @return
	 */
	public void expire(String key, int expireSeconds) {
		Jedis jedis = jedisPool.getResource();
		try {
			if (expireSeconds > 0) {
				jedis.expire(key, expireSeconds);
			}
		} finally {
			jedis.close();
		}
	}
	
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			long ttl = jedis.ttl(key);
			return ttl;
		} finally {
			jedis.close();
		}
	}
	
	/**
	 * del
	 * 
	 * @param key
	 */
	public void del(String... key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	/**
	 * flush
	 */
	public void flushDB() {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.flushDB();
		} finally {
			jedis.close();
		}
	}

	/**
	 * size
	 */
	public Long dbSize() {
		Long dbSize = 0L;
		Jedis jedis = jedisPool.getResource();
		try {
			dbSize = jedis.dbSize();
		} finally {
			jedis.close();
		}
		return dbSize;
	}

	/**
	 * keys
	 * 
	 * @param regex
	 * @return
	 */
	public Set<String> keys(String pattern) {
		Set<String> keys = null;
		Jedis jedis = jedisPool.getResource();
		try {
			keys = jedis.keys(pattern);
		} finally {
			jedis.close();
		}
		return keys;
	}

	public String info() {
		String info = null;
		Jedis jedis = jedisPool.getResource();
		try {
			info = jedis.info();
		} finally {
			jedis.close();
		}
		return info;
	}
	
	public String clientList() {
		String info = null;
		Jedis jedis = jedisPool.getResource();
		try {
			info = jedis.clientList();
		} finally {
			jedis.close();
		}
		return info;
	}
	
	public static void main(String[] args) {
		RedisClient client = new RedisClient();
		client.expire("dddd", 0);
		System.out.println(client.info());
		System.out.println(client.clientList());
	}
}
