package com.dant.business;

import net.spy.memcached.CachedData;
import net.spy.memcached.transcoders.BaseSerializingTranscoder;
import net.spy.memcached.transcoders.SerializingTranscoder;
import net.spy.memcached.transcoders.Transcoder;

import com.dant.dao.MemcacheDAO;
import com.dant.entity.Session;

public class SessionManager {

	private MemcacheDAO dao = new MemcacheDAO();

	public Session getSession(String idUser) {
		Session session = new Session(idUser,(String)dao.get(idUser)); 
		return session;

	}

	public void setSession(Session s){
		//dao.set(s.getIdUser(), s);
	}

	public void storeSession(Session s){
		dao.setSessionByIdUser(s.getIdUser(),s);
	}
	
	public void testTranscoder(Object str){
		SerializingTranscoder transcoder = new SerializingTranscoder();
		System.out.println(transcoder.encode(str));
		System.out.println(transcoder.toString());
	}
	
}
