package com.dant.entity;
import java.io.Serializable;

import org.json.*;
@SuppressWarnings("serial")
public class Session implements Serializable {
	
	private String sessionId;
	private String idUser;
	private float latitude;
	private float longitude;
	private long time;

	public Session(String idUser, String rawData){
		if(rawData.length()==0 || idUser.length()==0){}
		else{
		JSONObject obj = new JSONObject(rawData);
		this.idUser=idUser;
		this.sessionId= obj.getString("sId");
		this.latitude= Float.parseFloat(obj.getString("lt"));
		this.longitude= Float.parseFloat(obj.getString("lg"));
		this.time= Long.parseLong(obj.getString("time"));
		}
	}
	
	public Session(String idUser, String fname, String lname, String email, String password){
		
	}
		
		public String getIdUser(){
			return this.idUser;
		}
		
	}

