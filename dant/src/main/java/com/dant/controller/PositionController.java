package com.dant.controller;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dant.business.PositionBusiness;


@Path("/pos")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class PositionController {

	private PositionBusiness positionBusiness = new PositionBusiness();

	@POST
	public Response updatePosition(){
	positionBusiness.updatePosition();
	return Response.status(200).type("application/json").entity("{\"c\":0}").build();
		//Regle la nouvelle position du user à sa position actuelle
	}

	@GET
	public Response getPosition(){
		positionBusiness.getPosition();
		return Response.status(200).type("application/json").entity("{\"c\":0,\"data\":lat=exemple1,long=exemple2}").build();
		//Récuperer position utilisateur

	}

	// - /pos/friends
	@GET
	@Path("/friends")
	public Response getFriendsPositions(){
		positionBusiness.getFriendsPositions(0);
		//Récuperer les positions des amis de l'utilisateurs
		return Response.status(200).type("application/json").entity("{\"c\":0,\"data\":lat=exemple1,long=exemple2}").build();
	}

	// - /pos/friends/{userFriendId}
	@GET
	@Path("/friends/{n}")
	public Response getFriendsPositionsP(@PathParam("n") int page){
		positionBusiness.getFriendsPositions(page);
		return Response.status(200).type("application/json").entity("{\"c\":0,\"data\":lat=exemple1,long=exemple2}").build();
	}

	// - /pos/{idUser}
	@GET
	@Path("/{idUser}")
	public Response getFriendPosition(@PathParam("idUser") String id) throws SQLException{
		positionBusiness.getFriendPosition(id);
		return Response.status(200).type("application/json").entity("{\"c\":0,\"data\":lat=exemple1,long=exemple2}").build();
	}

	// --- POST
	// - /pos


}
