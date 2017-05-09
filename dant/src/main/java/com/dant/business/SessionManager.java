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
	
	public static String generateKey(int n){
	    String key = "";
	    for(int x=0;x<n;x++)
	    {
	       key+=String.format("%02X", (int)Math.floor(Math.random() * 255));
	    }
	    System.out.println(key);
	    return key;
	}
}
