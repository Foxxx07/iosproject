package com.dant.dao; 

import net.spy.memcached.MemcachedClient;

import com.dant.entity.Session;
import com.dant.exception.UserNotFoundException;

public class MemcacheDAO {

	private final MemcachedClient memcachedClient = Init.getMemcache();



	public <T> T get(String key) throws UserNotFoundException {
		if((T) memcachedClient.get(key)==null){
			throw new UserNotFoundException();
		}
		else{
			return (T) memcachedClient.get(key);
		}

	}

	public void setSessionByIdUser(String userKey, Session ob) {
		//	memcachedClient.set
	}


}
