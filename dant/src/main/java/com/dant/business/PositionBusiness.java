package com.dant.business;

import java.sql.SQLException;

import com.dant.dao.PositionDAO;

public class PositionBusiness {

	private PositionDAO positionDAO = new PositionDAO();
	
	public void updatePosition(){
		positionDAO.updatePosition();
	}
	
	public void getPosition(){
		positionDAO.getPositon();
	}
	
	public void getFriendsPositions(){
		positionDAO.getFriendsPositions();
	}
	
	public void getFriendsPositionsP(int page){
		positionDAO.getFriendsPositionsP(page);
	}
	
	public void getFriendPosition(String id) throws SQLException{
		positionDAO.getUserById(id);
	}
}
