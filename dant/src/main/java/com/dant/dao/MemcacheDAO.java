package com.dant.dao; 

import com.dant.entity.Position;
import com.dant.entity.Session;

import net.spy.memcached.CachedData;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;

public class MemcacheDAO {
	
	private final MemcachedClient client = MemcacheInit.getInstance();
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) client.get(key);
	}
	
	public void set(String key, Session ob) {
		client.set(key, 10, ob);
	}
	
	public void set(String key, Position ob) {
		client.set(key, 10, ob);
	}

}
