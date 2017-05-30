package com.dant.controller;

import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dant.business.FriendshipBusiness;
import com.dant.exception.SQLExceptionMapper;
import com.dant.exception.UserNotFoundException;
import com.dant.exception.UserNotFoundExceptionMapper;


@Path("/friends")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class FriendshipController {

	private FriendshipBusiness friendshipBusiness = new FriendshipBusiness();

	@GET
	//TODO x-token header
	public Response listFriends(String id){
		try {
			String str;
			str=friendshipBusiness.listFriends(id);
			return Response.status(200).type("application/json").entity("{\"c\":0,\"data\":"+str+"}").build();
			
 	} 
		catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		
		} catch (UserNotFoundException e) {
			UserNotFoundExceptionMapper ufem = new UserNotFoundExceptionMapper();
			return ufem.toResponse(e);
		}
		
	}


	// - /friends/{idUser}
	@GET
	@Path("/{idUser}")
	//TODO x-token header
	public Response isFriendWith(@PathParam("idUser") String idUser) throws SQLException{
		String id=null;
		if(friendshipBusiness.getFriendship(id,idUser)){
			return Response.status(200).type("application/json").entity("{\"c\":0}").build();
		}
		else{
			return Response.status(400).type("application/json").entity("{\"c\":8}").build();
		}
		//Retourne un boolean selon la relation en l'utilisateur connecté et idUser
	}

	// --- POST
	// - /friends/{idUser}
	@POST
	//TODO
	@Path("/{idUser}")
	public Response requestFriendship(@PathParam("idUser") String idUser){
		String id=null;
		try {
			friendshipBusiness.requestFriendship(id,idUser);
			return Response.status(200).type("application/json").entity("{\"c\":0}").build();
		} catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		}
	}

	// --- DELETE
	// - /friends/{idUser}
	@DELETE
	@Path("/{iduser}")
	public Response deleteFriend(@PathParam("idUser") String idUser){
		String id=null;
		try {
			friendshipBusiness.deleteFriend(id,idUser);
			return Response.status(200).type("application/json").entity("{\"c\":0}").build();
		} catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		}

	}

}
