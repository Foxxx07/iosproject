package com.dant.business;

import java.sql.SQLException;

import com.dant.dao.UserDAO;
import com.dant.entity.User;

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
			key = SessionManager.generateKey(4);
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
	
}
