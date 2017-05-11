package com.dant.util;

public class KeyGeneratorUtil {
	
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
