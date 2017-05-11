package com.dant.dao; 

import net.spy.memcached.MemcachedClient;

import com.dant.entity.Session;

public class MemcacheDAO {

	private final MemcachedClient client = Init.getMemcache();

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) client.get(key);
	}

	public void set(String key, Session ob) {
		client.set(key, 10, ob);
	}


}
