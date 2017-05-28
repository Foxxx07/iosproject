package com.dant.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dant.business.SessionManager;
import com.dant.exception.UserFoundException;
import com.dant.exception.UserNotFoundException;

public class FriendshipDAO {

	private final Connection connection = Init.getJDBC();

	public void setFriendship(String rkey, String akey, String bkey) throws SQLException{
		String sql="{call SetFriendship(?,?,?)}";
		try (CallableStatement call = connection.prepareCall(sql)) { 
			call.setString(1, rkey); 
			call.setString(2, akey);
			call.setString(3, bkey);

			if(call.execute()){ 
				//Tout va bien
			}
			else{
				throw new SQLException();
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
				throw new SQLException();
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
	
	public void listFriends(String id) throws SQLException, UserFoundException, UserNotFoundException{
		String sql = "{call ListFriends(1)}";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet req = ps.executeQuery(sql)) {
				if(req.next()){
					throw new UserFoundException();
					//Renvoyer les amis trouvés
				}
				else{
					throw new UserNotFoundException();
					//Pas d'ami
				}
			}
		}
		
	}


}
