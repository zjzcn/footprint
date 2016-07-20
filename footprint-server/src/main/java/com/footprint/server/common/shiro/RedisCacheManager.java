package com.footprint.server.common.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.footprint.server.common.utils.RedisClient;

public class RedisCacheManager extends AbstractCacheManager {

	private RedisClient redisClient;

	/**
	 * The Redis key prefix for caches 
	 */
	private String keyPrefix = "shiro_redis_cache:";
	
	private int expireMS = 0;
	
	/**
	 * Returns the Redis session keys
	 * prefix.
	 * @return The prefix
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * Sets the Redis sessions key 
	 * prefix.
	 * @param keyPrefix The prefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
	
	public void setExpireMS(int expireMS) {
		this.expireMS = expireMS;
	}

	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
	}

	@Override
	protected Cache<?, ?> createCache(String keyPrefix) throws CacheException {
		 return new RedisCache<Object, Object>(redisClient, keyPrefix, expireMS);
	}
	
}
