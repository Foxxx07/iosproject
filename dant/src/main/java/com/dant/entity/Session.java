package com.dant.entity;
import java.io.Serializable;

import org.json.*;

import com.dant.util.KeyGeneratorUtil;
@SuppressWarnings("serial")
public class Session implements Serializable {

	private String sessionId;
	private String idUser;
	private float latitude;
	private float longitude;
	private long time;

	public Session(String idUser, String rawData){
		if(idUser.length()==0){
		}
		else if(rawData.length()==0 ){
			this.idUser=idUser;
			this.sessionId=KeyGeneratorUtil.generateKey(4);
			this.latitude=0;
			this.longitude=0;
			this.time=0;
		}
		else{
			JSONObject obj = new JSONObject(rawData);
			this.idUser=idUser;
			this.sessionId= obj.getString("sId");
			this.latitude= Float.parseFloat(obj.getString("lt"));
			this.longitude= Float.parseFloat(obj.getString("lg"));
			this.time= Long.parseLong(obj.getString("time"));
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getIdUser(){
		return this.idUser;
	}

}

