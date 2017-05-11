package com.dant.dao; 

import net.spy.memcached.MemcachedClient;

import com.dant.entity.Session;

public class MemcacheDAO {

	private final MemcachedClient memcachedClient = Init.getMemcache();

	

	public <T> T get(String key) {
		return (T) memcachedClient.get(key);
	}

	public void setSessionByIdUser(String userKey, Session ob) {
	//	memcachedClient.set
	}


}
