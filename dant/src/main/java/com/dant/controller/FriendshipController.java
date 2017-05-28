package com.dant.controller;

import java.sql.SQLException;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dant.business.FriendshipBusiness;
import com.dant.business.UserBusiness;
import com.dant.entity.User;
import com.dant.exception.SQLExceptionMapper;
import com.dant.exception.UserFoundException;
import com.dant.exception.UserNotFoundException;
import com.dant.exception.UserNotFoundExceptionMapper;
import com.dant.util.UserFoundExceptionMapper;


@Path("/friends")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class FriendshipController {

	private FriendshipBusiness friendshipBusiness = new FriendshipBusiness();

	@GET
	public Response listFriends(String id){
		try {
			friendshipBusiness.listFriends(id);
 	} catch (UserFoundException e) {
			UserFoundExceptionMapper ufem = new UserFoundExceptionMapper();
			return ufem.toResponse(e);
			//Lister les amis de l'utilisateur connecté
		}
		catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		
		} catch (UserNotFoundException e) {
			UserNotFoundExceptionMapper ufem = new UserNotFoundExceptionMapper();
			return ufem.toResponse(e);
		}
		return null;
		
	}


	// - /friends/{idUser}
	@GET
	@Path("/{idUser}")
	public boolean isFriendWith(@PathParam("idUser") String idUser) throws SQLException{
		String id=null;
		friendshipBusiness.getFriendship(id,idUser);
		//Retourne un boolean selon la relation en l'utilisateur connecté et idUser
		return false;
	}

	// --- POST
	// - /friends/{idUser}
	@POST
	@Path("/{idUser}")
	public Response requestFriendship(@PathParam("idUser") String idUser){
		String id=null;
		try {
			friendshipBusiness.requestFriendship(id,idUser);
		} catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		}
		return null;
	}

	// --- DELETE
	// - /friends/{idUser}
	@DELETE
	@Path("/{iduser}")
	public Response deleteFriend(@PathParam("idUser") String idUser){
		String id=null;
		try {
			friendshipBusiness.deleteFriend(id,idUser);
		} catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		}
		//Delete friendship
		return null;
	}

}
