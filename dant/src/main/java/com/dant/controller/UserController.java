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
import javax.ws.rs.core.Response;

import org.mariadb.jdbc.internal.util.ExceptionMapper;

import com.dant.business.UserBusiness;
import com.dant.entity.User;
import com.dant.exception.EmailException;
import com.dant.exception.EmailExceptionMapper;
import com.dant.exception.EmptyEmailException;
import com.dant.exception.EmptyEmailExceptionMapper;
import com.dant.exception.EmptyNameException;
import com.dant.exception.EmptyNameExceptionMapper;
import com.dant.exception.EmptyPasswordException;
import com.dant.exception.EmptyPasswordExceptionMapper;
import com.dant.exception.InvalidEmailException;
import com.dant.exception.InvalidEmailExceptionMapper;


@Path("/api/u")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

	private UserBusiness userBusiness = new UserBusiness();

	@POST
	public Response createUser(
			@DefaultValue("") @FormParam("fname") String fname,
			@DefaultValue("") @FormParam("lname") String lname,
			@DefaultValue("") @FormParam("email") String email,
			@DefaultValue("") @FormParam("password") String password) throws SQLException
			{


		try {
			userBusiness.createSession(userBusiness.createUser(fname,lname,email,password));
			//Créer la session
			return Response.status(200).build();
		} 
		catch (EmptyNameException e) {
			EmptyNameExceptionMapper enem = new EmptyNameExceptionMapper();
			return enem.toResponse(e);
		}

		catch (EmailException e) {
			EmailExceptionMapper eem = new EmailExceptionMapper();
			return eem.toResponse(e);
		}	
		
		catch(EmptyEmailException e){
			EmptyEmailExceptionMapper eeem = new EmptyEmailExceptionMapper();
			return eeem.toResponse(e);
		}
		
		catch(EmptyPasswordException e){
			EmptyPasswordExceptionMapper epem = new EmptyPasswordExceptionMapper();
			return epem.toResponse(e);
		}
		
		catch(InvalidEmailException e){
			InvalidEmailExceptionMapper ieem = new InvalidEmailExceptionMapper();
			return ieem.toResponse(e);
		}
		
		catch(SQLException e){
			return Response.status(500).build();
		}
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
