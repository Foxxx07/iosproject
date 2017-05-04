package com.dant.business;

import com.dant.dao.MemcacheDAO;
import com.dant.entity.Session;

public class SessionManager {

	private MemcacheDAO dao = new MemcacheDAO();

	public Session getSession(String idUser) {
		Session session = new Session(idUser,(String)dao.get(idUser)); 
		return session;

	}

	public void setSession(Session s){
		dao.set(s.getIdUser(), s);
	}

	public void storeSession(Session s){
		dao.set(s.getIdUser(),s);
	}
}
