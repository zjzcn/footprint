package com.footprint.server.common.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.server.common.utils.RedisClient;

public class RedisCache<K, V> implements Cache<K, V> {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String DEFAULT_KEY_PREFIX = "shiro_redis_cache:";
	
	private String keyPrefix = DEFAULT_KEY_PREFIX;
	private long expireMS = 0;
	private RedisClient redisClient;
	
	public RedisCache(RedisClient client){
		 if (client == null) {
	         throw new IllegalArgumentException("Cache argument cannot be null.");
	     }
	     this.redisClient = client;
	}
	
	public RedisCache(RedisClient client, String keyPrefix){
		this( client );
		this.keyPrefix = keyPrefix;
	}

	public RedisCache(RedisClient client, String keyPrefix, long expireMS){
		this( client, keyPrefix);
		this.expireMS = expireMS;
	}
	
	public void setExpireMS(long expireMS) {
		this.expireMS = expireMS;
	}

	/**
	 * 获得byte[]型的key
	 * @param key
	 * @return
	 */
	private String redisKey(K key){
		if(key instanceof String){
			return this.keyPrefix + key;
    	}else{
    		return new String(SerializeUtils.serialize(key));
    	}
	}
 	
	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) throws CacheException {
		logger.info("获取Redis对象 key [" + key + "]");
		try {
			if (key == null) {
	            return null;
	        }else{
	        	byte[] rawValue = redisClient.get(redisKey(key));
				V value = (V)SerializeUtils.deserialize(rawValue);
	        	return value;
	        }
		} catch (Throwable t) {
			throw new CacheException(t);
		}

	}

	@Override
	public V put(K key, V value) throws CacheException {
		logger.info("存储Redis对象 key [" + key + "]");
		try {
			if(expireMS > 0) {
				redisClient.set(redisKey(key), SerializeUtils.serialize(value), expireMS);
			} else {
				redisClient.set(redisKey(key), SerializeUtils.serialize(value));
			}
			return value;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public V remove(K key) throws CacheException {
		logger.info("删除Redis对象 key [" + key + "]");
		try {
			String k = redisKey(key);
            @SuppressWarnings("unchecked")
			V previous = (V)redisClient.get(k);
            redisClient.del(k);
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public void clear() throws CacheException {
		logger.info("从Redis中删除所有元素");
		try {
			Set<K> keys = keys();
			redisClient.del((String[])keys.toArray());
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public int size() {
		logger.info("获得Redis的数据库大小");
		try {
			Set<K> keys = keys();
            return keys.size();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		try {
            Set<String> keys = redisClient.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
            	return Collections.emptySet();
            }else{
            	Set<K> newKeys = new HashSet<K>();
            	for(String key : keys){
            		newKeys.add((K)key);
            	}
            	return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> values() {
		try {
            Set<String> keys = redisClient.keys(this.keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (String key : keys) {
					V value = get((K)key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings("unchecked")
	public void expireMS(String key, long expireMS) {
		try {
			redisClient.expire(redisKey((K)key), (int)(expireMS/1000));
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}
}
