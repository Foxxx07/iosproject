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

import com.dant.business.FriendshipBusiness;
import com.dant.business.UserBusiness;
import com.dant.entity.User;


@Path("/friends")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class FriendshipController {

	private FriendshipBusiness friendshipBusiness = new FriendshipBusiness();

	@GET
	public void listFriends(String id) throws SQLException{
		friendshipBusiness.listFriends(id);
		//Lister les amis de l'utilisateur connecté
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
	public void requestFriendship(@PathParam("idUser") String idUser) throws SQLException{
		String id=null;
		friendshipBusiness.requestFriendship(id,idUser);
		//Faire requete amitié
	}

	// --- DELETE
	// - /friends/{idUser}
	@DELETE
	@Path("/{iduser}")
	public void deleteFriend(@PathParam("idUser") String idUser) throws SQLException{
		String id=null;
		friendshipBusiness.deleteFriend(id,idUser);
		//Delete friendship
	}

}
