package com.dant.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PositionDAO {
	private final Connection connection = Init.getJDBC();
	
	public void updatePosition(){
		
	}
	
	public void getPositon(){
		
	}
	
	public void getFriendsPositions(){
		
	}
	
	public void getFriendsPositionsP(int page){
		
	}
	
	public void getUserById(String id) throws SQLException{
		String sql="{call getUserById(?)}";
		try (CallableStatement call = connection.prepareCall(sql)) { 
			call.setString(1,id);
			if(call.execute()){ 
				//Tout va bien
			}
			else{
				//Tout va mal
			}
		}

}
}
