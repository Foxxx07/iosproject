package com.dant.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.binary.Hex;

import com.dant.entity.User;
import com.dant.exception.UserFoundException;
import com.dant.exception.UserNotFoundException;
import com.dant.util.CryptoUtil;

public class UserDAO {

	private final Connection connection = Init.getJDBC();

	public void createUser(User u) throws SQLException {
		String sql="{call CreateUser(0x" + u.getKey() + ",?,?,?, 0x" + CryptoUtil.encrypt(u.getPassword()) + ")}";

		try (CallableStatement call = connection.prepareCall(sql)) { 
			//e.g. passage de la chaÃ®ne userId comme valeur du premier paramÃ¨tre 
			call.setString(1,u.getFname()); 
			call.setString(2,u.getLname()); 
			call.setString(3,u.getEmail()); 
			call.execute();

//			if (1 != call.getUpdateCount()) {
//				System.out.println("UpdateCount : " + call.getUpdateCount());
//
//			}
//			System.out.println("UpdateCount : " + call.getUpdateCount());
		}
	}

	public void getUserByCredentials(String email, String password) throws SQLException {
		String sql="{call getUserByCredentials(?,0x" + CryptoUtil.encrypt(password)  + ")}";
		try (CallableStatement call = connection.prepareCall(sql)) {
			call.setString(1,email);
			if(call.execute()){ 
				//Tout va bien
			}
			else{
				//Tout va mal
			}
		}
	}

	public void getUserById(String id) throws UserNotFoundException, SQLException, UserFoundException{
				String sql="{call getUserById(?)}";
				try (CallableStatement call = connection.prepareCall(sql)) { 
					call.setString(1,id);
					if(call.execute()){ 
						throw new UserFoundException();
					}
					else{
						throw new UserNotFoundException();
					}
				}
	
	}

	public void searchUser(String search_str, int offset_pos, int limit_l, boolean in_bool_mode) throws SQLException, UserFoundException{
		String sql="{call SearchUser(?,?,?,?)}";
		try (CallableStatement call = connection.prepareCall(sql);) { 
			call.setString(1, search_str);
			call.setInt(2, offset_pos);
			call.setInt(3, limit_l);
			call.setBoolean(4, in_bool_mode);
			if(call.execute()){ 
				//Tout va bien
				//Boucle json formée depuis les users renvoyés
				throw new UserFoundException();
			}
			else{
				throw new SQLException();
			}
		}
	}

	public boolean keyAlreadyExists(String key) throws SQLException{
		String sql="Select 1 from users where `key`=0x"+key;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet req = ps.executeQuery(sql)) {
				return req.next();
			}
		}
	}

	public boolean emailAlreadyExists(String email) throws SQLException{
		String sql="Select 1 from users where `email`= '" + email + "'";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet req = ps.executeQuery(sql)) {
				return req.next();
			}
		}
	}
	
	public void updateUser(String id, String fname, String lname, String email, String password){
		//Requete mise Ã  jour
	}

}
