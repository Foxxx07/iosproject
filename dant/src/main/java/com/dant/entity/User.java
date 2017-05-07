package com.dant.entity;
import java.security.Timestamp;
import java.util.Date;
import java.util.TimeZone;
import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class User implements Serializable {

	String key,fname,lname,email,password;
	long registration;
	DateTimeFormatter parserDate = DateTimeFormat.forPattern("yyyy-MM-dd HH:MM:SS,XXX");


	public User(String fname, String lname, String email, String password){
		key=generateKey(4);
		this.fname=fname;
		this.lname=lname;
		this.email=email;
		this.password=password;
		registration = System.currentTimeMillis()/1000;
		//Pour convertir un long en date
		//DateTime date = new DateTime(registration,DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris")));

	}

	public String generateKey(int n){
	    String key = "";

	    for(int x=0;x<n;x++)
	    {
	       key+=String.format("%02X", (int)Math.floor(Math.random() * 255));
	    }
	    System.out.println(key);
	    return key;
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
