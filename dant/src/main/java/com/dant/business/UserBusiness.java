package com.dant.business;

import java.sql.SQLException;

import com.dant.dao.UserDAO;
import com.dant.entity.User;
import com.dant.util.KeyGeneratorUtil;

public class UserBusiness {

	private UserDAO userDAO = new UserDAO();

	public User createUser(String fname, String lname, String email, String password) throws SQLException {
		User user = new User(fname,lname,email,password);

		boolean emailExist = userDAO.emailAlreadyExists(email);
		if (emailExist) {
			throw new RuntimeException();
		}

		String key = null;
		while (true) {
			key = KeyGeneratorUtil.generateKey(4);
			if (!userDAO.keyAlreadyExists(key))
				break;
		}

		user.setKey(key);
		userDAO.createUser(user);
		return user;
	}

	public void searchUser(String query,int page) throws SQLException{
		int limit = 10;
		if (1 > page) {
			limit = -1;
			page = 0;
		}
		userDAO.searchUser(query, page, limit, false);
	}

	public void getUserById(String id) throws SQLException{
		boolean isHexa=true;
		if(id.length()==8){
			for(int i=0; i<8;i++){
				if(Character.digit(id.charAt(i),16)==-1)
					isHexa=false;
				break;
				//pas hexa
			}
			if(isHexa){
				userDAO.getUserById(id);

			}
			else{//pas une clé en hexa
			}
		}

		else{
			//pas une clé valide
		}
	}
	
	public void listUserMetaData(String id) throws SQLException{
		userDAO.getUserById(id);
		
	}
	
	public void updateUser(String id, String fname, String lname, String email, String password) throws SQLException{
	
		// Si connecté, alors on met à jour ses données.
		userDAO.updateUser(id, fname, lname, email, password);
				/*
				 * @DefaultValue("Null") @FormParam("fname") String fname,
					@DefaultValue("Null") @FormParam("lname") String lname,
					@DefaultValue("Null") @FormParam("email") String email,
					@DefaultValue("Null") @FormParam("password") String password
				 * */

				// Sinon, on le connecte.
		userDAO.getUserByCredentials(email, password);
				/*
					@DefaultValue("Null") @FormParam("email") String email,
					@DefaultValue("Null") @FormParam("password") String password
				 * */

	}

}
