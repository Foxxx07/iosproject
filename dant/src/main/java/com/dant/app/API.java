package com.dant.app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.codec.binary.Hex;
import org.mariadb.jdbc.Driver;

import com.dant.dao.JDBCCalls;
import com.dant.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)

public class API {

	// ------ Collection /u/
	// --- GET	
	// -- /u/?search
	// -- /u/search?&n
	@GET
	@Path("/u")
	public String searchP(@DefaultValue("Null") @QueryParam("search") String query, @DefaultValue("0") @QueryParam("n") int page) throws SQLException{
		int limit = 10;
		if (1 > page) {
			limit = -1;
			page = 0;
		}

		JDBCCalls.SearchUser(query, page, limit, false);
		return null;
	}


	// -- /u/{idUser}
	@GET
	@Path("/u/{id}")
	public String listMetaDataForUser(@PathParam("id") String id) throws SQLException {
		JDBCCalls.getUserById(id);
		return null;

	}

	// -- /u/me
	@GET
	@Path("/u/me")
	public String listMetaData(){

		// Si connecté, on récupère les méta-données, puis HTTP 200 OK
		// Sinon, HTTP 404 Not Found
		//Session s = Session.getSess(...)
		//if (s == NULL) {
		// 404

		//	}
		return null;
	}

	// --- POST
	// - /u/
	@POST 
	@Path("/u")
	public String createUser(
			@DefaultValue("") @FormParam("fname") String fname,
			@DefaultValue("") @FormParam("lname") String lname,
			@DefaultValue("") @FormParam("email") String email,
			@DefaultValue("") @FormParam("password") String password) throws SQLException, NoSuchAlgorithmException
			{
	 System.out.println("Input:");
	 System.out.println("fname ::" +fname);
	 System.out.println("lname ::" +lname);
	 System.out.println("email::" +email);
	 System.out.println("password::" +password);
		JDBCCalls.CreateUser(new User(fname,lname,email,password));	
		return "J'ai créé le user";
			}

	// - /u/me/
	@POST
	@Path("/u/me")
	public String updateUser(
			@DefaultValue("Null") @FormParam("fname") String fname,
			@DefaultValue("Null") @FormParam("lname") String lname,
			@DefaultValue("Null") @FormParam("email") String email,
			@DefaultValue("Null") @FormParam("password") String password
			)
	{
		// Si connecté, alors on met à jour ses données.
		/*
		 * @DefaultValue("Null") @FormParam("fname") String fname,
			@DefaultValue("Null") @FormParam("lname") String lname,
			@DefaultValue("Null") @FormParam("email") String email,
			@DefaultValue("Null") @FormParam("password") String password
		 * */

		// Sinon, on le connecte.
		/*
			@DefaultValue("Null") @FormParam("email") String email,
			@DefaultValue("Null") @FormParam("password") String password
		 * */

		return "";
	}

	// ------ Collection /friends/
	// --- GET
	// - /friends
	@GET
	@Path("/friends")
	public void listFriends(){
		//Lister les amis de l'utilisateur connecté
	}
	
	
	// - /friends/{idUser}
	@GET
	@Path("/friends/{idUser}")
	public boolean isFriendWith(@PathParam("idUser") String idUser){
		//Retourne un boolean selon la relation en l'utilisateur connecté et idUser
		return false;
	}

	// --- POST
	// - /friends/{idUser}
	@POST
	@Path("/friends/{idUser}")
	public void requestFriendship(@PathParam("idUser") String idUser){
		//Faire requete amitié
	}

	// --- DELETE
	// - /friends/{idUser}
	@DELETE
	@Path("/firends/{iduser}")
	public void deleteFriend(@PathParam("idUser") String iduser){
		//Delete friendship
	}

	// ------ Collection /pos/
	// --- GET
	// - /pos/
	@GET
	@Path("/pos")
	public String getPosition(){
		//Récuperer position utilisateur
		return null;
	}

	// - /pos/friends
	@GET
	@Path("/pos/friends")
	public String getFriendsPositions(){
		//Récuperer les positions des amis de l'utilisateurs
		return null;
	}

	// - /pos/friends/{userFriendId}
	@GET
	@Path("/pos/friends/{n}")
	public String getFriendsPositionsP(@PathParam("n") int page){

		return null;
	}

	// - /pos/{idUser}
	@GET
	@Path("/pos/{idUser}")
	public String getFriendPosition(@PathParam("idUser") String id) throws SQLException{
		JDBCCalls.getUserById(id);
		return null;
	}

	// --- POST
	// - /pos
	@POST
	@Path("/pos")
	public void updatePosition(){
		//Regle la nouvelle position du user à sa position actuelle
	}





}


