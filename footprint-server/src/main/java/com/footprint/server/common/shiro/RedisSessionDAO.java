package com.footprint.server.common.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSessionDAO extends AbstractSessionDAO {
	private static final Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
	private static final String SESSION_KEY_PREFIX = "shiro_redis_session:";
	
	private RedisCache<String, Session> cache;
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}
	
	/**
	 * save session
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException{
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		cache.put((String)session.getId(), session);
		cache.expireMS((String)session.getId(), session.getTimeout());
	}

	@Override
	public void delete(Session session) {
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		cache.remove((String)session.getId());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();
		Set<String> keys = cache.keys();
		if(keys != null && keys.size()>0){
			for(String key : keys){
				Session s = cache.get(key);
				sessions.add(s);
			}
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId == null){
			logger.error("session id is null");
			return null;
		}
		Session session = cache.get((String)sessionId);
		if(session != null) {
			cache.expireMS((String)sessionId, session.getTimeout());
		}
		return session;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setCacheManager(RedisCacheManager cacheManager) {
		cache = (RedisCache)cacheManager.getCache(SESSION_KEY_PREFIX);
		cache.setExpireMS(0);
	}	

}
