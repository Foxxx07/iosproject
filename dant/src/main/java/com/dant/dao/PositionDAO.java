package com.dant.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mariadb.jdbc.internal.util.dao.QueryException;

import com.dant.entity.User;
import com.dant.exception.UserNotFoundException;
import com.dant.util.CryptoUtil;

public class PositionDAO {
	private final Connection connection = Init.getJDBC();
	private MemcacheDAO dao = new MemcacheDAO();

	public void updatePosition(String sessionId, String lat, String longi){
		dao.updateSessionPosition(sessionId, lat, longi);

	}

	public String getPosition(String id){
		return null;
	}

	public String getFriendsPositions(int page, String id) throws SQLException, UserNotFoundException{
		String sql="select user_b from friends where user_a = "+id;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery(sql)) {
				String str = null;
				if(rs.next()){
					while(rs.next()){
						if (0 < str.length()) {
							str += ",";
						}
						str += rs.getString("user_b");
					}
					return "[" + str + "]";
				}
				else{throw new UserNotFoundException();}
			}
		}
	}

	public String getFriendsPositionsP(int page, String id) throws SQLException, UserNotFoundException{
		String sql="select user_b from friends where user_a = "+id +"limit "+(page-1*10)+","+page*10;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery(sql)) {
				String str = null;
				if(rs.next()){
					while(rs.next()){
						if (0 < str.length()) {
							str += ",";
						}
						str += rs.getString("user_b");
					}
					return "[" + str + "]";
				}
				else{throw new UserNotFoundException();}
			}
		}

	}

	public String getUserById(String id) {
		return dao.getSessionByUserKey(id);

	}
}
