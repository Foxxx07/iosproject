package com.dant.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.dant.business.SessionManager;

public class FriendshipDAO {

	private final Connection connection = Init.getJDBC();

	public void setFriendship(String rkey, String akey, String bkey) throws SQLException{
		String sql="{call SetFriendship(?,?,?)}";
		try (CallableStatement call = connection.prepareCall(sql)) { 
			call.setString(1,SessionManager.generateKey(4)); 
			call.setString(2, akey);
			call.setString(3, bkey);

			if(call.execute()){ 
				//Tout va bien
			}
			else{
				//Tout va mal
			}
		}
	}

	public void deleteFriendship(String akey, String bkey) throws SQLException{
		String sql="{call DeleteFriendship(?,?)}";
		try (CallableStatement call = connection.prepareCall(sql)) {
			call.setString(1, akey);
			call.setString(2, bkey);

			if(call.execute()){ 
				//Tout va bien
			}
			else{
				//Tout va mal
			}
		}
	}

	public void getFriendship(String akey, String bkey) throws SQLException{
		String sql="{call GetFriendship(?,?)}";
		try (CallableStatement call = connection.prepareCall(sql)) { 
			call.setString(1, akey);
			call.setString(2, bkey);

			if(call.execute()){ 
				//Tout va bien
			}
			else{
				//Tout va mal
			}
		}
	}


}
