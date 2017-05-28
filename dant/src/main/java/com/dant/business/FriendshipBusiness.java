package com.dant.business;

import java.sql.SQLException;

import com.dant.dao.FriendshipDAO;
import com.dant.exception.UserFoundException;
import com.dant.exception.UserNotFoundException;
import com.dant.util.KeyGeneratorUtil;

public class FriendshipBusiness {
	private FriendshipDAO friendshipDAO = new FriendshipDAO();
	
	public void listFriends(String id) throws SQLException, UserFoundException, UserNotFoundException{
		friendshipDAO.listFriends(id);
	}
	
	public void getFriendship(String idA, String idB) throws SQLException{
		friendshipDAO.getFriendship(idA, idB);
	}
	
	public void requestFriendship(String idA, String idB) throws SQLException{
		friendshipDAO.setFriendship(KeyGeneratorUtil.generateKey(4), idA, idB);
	}
	
	public void deleteFriend(String id, String idUser) throws SQLException{
		friendshipDAO.deleteFriendship(id, idUser);
	}
}
