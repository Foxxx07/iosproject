package com.dant.controller;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dant.business.PositionBusiness;


@Path("/api/pos")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class PositionController {
	
	private PositionBusiness positionBusiness = new PositionBusiness();
	
	@POST
	public void updatePosition(){
	positionBusiness.updatePosition();
		//Regle la nouvelle position du user à sa position actuelle
	}
	
	@GET
	public String getPosition(){
		positionBusiness.getPosition();
		//Récuperer position utilisateur
		return null;
	}

	// - /pos/friends
	@GET
	@Path("/friends")
	public String getFriendsPositions(){
		positionBusiness.getFriendsPositions();
		//Récuperer les positions des amis de l'utilisateurs
		return null;
	}

	// - /pos/friends/{userFriendId}
	@GET
	@Path("/friends/{n}")
	public String getFriendsPositionsP(@PathParam("n") int page){
		positionBusiness.getFriendsPositionsP(page);
		return null;	
	}

	// - /pos/{idUser}
	@GET
	@Path("/{idUser}")
	public String getFriendPosition(@PathParam("idUser") String id) throws SQLException{
		positionBusiness.getFriendPosition(id);
		return null;
	}

	// --- POST
	// - /pos


}
