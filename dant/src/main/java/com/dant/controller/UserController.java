package com.dant.controller;

import java.sql.SQLException;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.dant.business.UserBusiness;
import com.dant.entity.User;


@Path("/api/u")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

	private UserBusiness userBusiness = new UserBusiness();

	@POST
	public User createUser(
			@DefaultValue("") @FormParam("fname") String fname,
			@DefaultValue("") @FormParam("lname") String lname,
			@DefaultValue("") @FormParam("email") String email,
			@DefaultValue("") @FormParam("password") String password) throws SQLException
			{

		return userBusiness.createUser(fname,lname,email,password);	
			}

	@GET
	public String searchP(@DefaultValue("Null") @QueryParam("search") String query, @DefaultValue("0") @QueryParam("n") int page) throws SQLException{
		userBusiness.searchUser(query, page);
		return null;
	}


	@Path("/{id}")
	@GET
	public String listMetaDataForUser(@PathParam("id") String id) throws SQLException {
		userBusiness.getUserById(id);
		return null;

	}


	@Path("/me")
	@GET 
	public String listMetaData(String id) throws SQLException{
		userBusiness.listUserMetaData(id);
		// Si connecté, on récupère les méta-données, puis HTTP 200 OK
		// Sinon, HTTP 404 Not Found
		//Session s = Session.getSess(...)
		//if (s == NULL) {
		// 404

		//	}
		return null;
	}
	
	
	@Path("/me")
	@POST
	public String updateUser(
			@DefaultValue("Null") @FormParam("fname") String fname,
			@DefaultValue("Null") @FormParam("lname") String lname,
			@DefaultValue("Null") @FormParam("email") String email,
			@DefaultValue("Null") @FormParam("password") String password
			) throws SQLException
	{
		//Récupérer l'id
		String id=null;
		userBusiness.updateUser(id,fname,lname,email,password);
		return "";
	}

}
