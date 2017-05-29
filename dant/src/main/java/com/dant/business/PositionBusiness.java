package com.dant.business;

import java.sql.SQLException;

import com.dant.dao.PositionDAO;

public class PositionBusiness {

	private PositionDAO positionDAO = new PositionDAO();
	//private MemcacheDAO dao = new MemcacheDAO();
	
	public void updatePosition(){
		positionDAO.updatePosition();
	}
	
	public void getFriendsPositionsP(int page){
		positionDAO.getFriendsPositionsP(page);
	}
	
	public void getFriendPosition(String id) throws SQLException{
		positionDAO.getUserById(id);
	}

	public void getFriendsPositions(int i) {
		positionDAO.getFriendsPositions();
		
	}

	public void getPosition() {
		positionDAO.getPosition();
		
	}
}
