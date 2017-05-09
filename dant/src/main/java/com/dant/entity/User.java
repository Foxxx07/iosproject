package com.dant.entity;
import java.security.Timestamp;
import java.sql.SQLException;
import java.util.Date;
import java.util.TimeZone;
import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.dant.business.SessionManager;
import com.dant.dao.JDBCCalls;

public class User implements Serializable {
	
	String key,fname,lname,email,password;
	long registration;
	DateTimeFormatter parserDate = DateTimeFormat.forPattern("yyyy-MM-dd HH:MM:SS");

	
	public User(String fname, String lname, String email, String password) throws SQLException{
	
		while (true) {
			this.key=SessionManager.generateKey(4);
			if (!JDBCCalls.keyAlreadyExists(key))
				break;
		}
		
		this.fname=fname;
		this.lname=lname;
		this.email=email;
		this.password=password;
		
		//Pour convertir un long en date
		//DateTime date = new DateTime(registration,DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris")));
		
	}

	public User(String key, String fname, String lname, String email, String password, long registration){
		this.key=key;
		this.fname=fname;
		this.lname=lname;
		this.email=email;
		this.password=password;
		this.registration=registration;
	}

	
	public String getKey() {
		return key;
	}



	public void setKey(String key) {
		this.key = key;
	}



	public String getFname() {
		return fname;
	}



	public void setFname(String fname) {
		this.fname = fname;
	}



	public String getLname() {
		return lname;
	}



	public void setLname(String lname) {
		this.lname = lname;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String toString(){
		String str;
		str="Vous etes : ";
		str+="fname: "+fname;
		str+=" lname: "+lname;
		str+=" email: "+email;
		str+=" password: "+password;
		str+=" registrationDate: "+ parserDate.print(new DateTime(registration,DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"))));
		return str;
	}
}
