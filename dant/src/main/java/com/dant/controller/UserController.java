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

import com.dant.business.SessionManager;
import com.dant.business.UserBusiness;
import com.dant.entity.Session;
import com.dant.entity.User;
import com.dant.exception.EmailException;
import com.dant.exception.EmailExceptionMapper;
import com.dant.exception.EmptyEmailException;
import com.dant.exception.EmptyEmailExceptionMapper;
import com.dant.exception.EmptyNameException;
import com.dant.exception.EmptyNameExceptionMapper;
import com.dant.exception.EmptyPasswordException;
import com.dant.exception.EmptyPasswordExceptionMapper;
import com.dant.exception.HexadecimalException;
import com.dant.exception.HexadecimalExceptionMapper;
import com.dant.exception.InvalidEmailException;
import com.dant.exception.InvalidEmailExceptionMapper;
import com.dant.exception.InvalidUserKeyException;
import com.dant.exception.InvalidUserKeyExceptionMapper;
import com.dant.exception.SQLExceptionMapper;
import com.dant.exception.UserFoundException;
import com.dant.exception.UserNotFoundException;
import com.dant.exception.UserNotFoundExceptionMapper;
import com.dant.util.UserFoundExceptionMapper;


@Path("/u")
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
	public Response searchP(@DefaultValue("Null") @QueryParam("search") String query, @DefaultValue("0") @QueryParam("n") int page) throws SQLException{

		try {
			userBusiness.searchUser(query, page);

		} catch (UserFoundException e) {
			UserFoundExceptionMapper ufem = new UserFoundExceptionMapper();
			return ufem.toResponse(e);
		}
		catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		}

		return null;
	}


	@Path("/{id}")
	@GET
	public Response listMetaDataForUser(@PathParam("id") String id) {
		try {
			userBusiness.getUserById(id);
		} catch (HexadecimalException e) {
			HexadecimalExceptionMapper hem = new HexadecimalExceptionMapper();
			return hem.toResponse(e);
		} catch (InvalidUserKeyException e) {
			InvalidUserKeyExceptionMapper iukem = new InvalidUserKeyExceptionMapper();
			return iukem.toResponse(e);
		} catch (UserNotFoundException e) {
			UserNotFoundExceptionMapper iukem = new UserNotFoundExceptionMapper();
			return iukem.toResponse(e);
		} catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		} catch (UserFoundException e) {
			UserFoundExceptionMapper ufem = new UserFoundExceptionMapper();
			return ufem.toResponse(e);
		}
		return null;

	}


	@Path("/me")
	@GET
	public Response listMetaData(String id){

		// Si connecté, on récupère les méta-données, puis HTTP 200 OK
		// Sinon, HTTP 404 Not Found

		try {
			userBusiness.getUserById(id);
		} catch (HexadecimalException e) {
			HexadecimalExceptionMapper hem = new HexadecimalExceptionMapper();
			return hem.toResponse(e);
		} catch (InvalidUserKeyException e) {
			InvalidUserKeyExceptionMapper iukem = new InvalidUserKeyExceptionMapper();
			return iukem.toResponse(e);
		} catch (UserNotFoundException e) {
			UserNotFoundExceptionMapper iukem = new UserNotFoundExceptionMapper();
			return iukem.toResponse(e);
		} catch (SQLException e) {
			SQLExceptionMapper sem = new SQLExceptionMapper();
			return sem.toResponse(e);
		} catch (UserFoundException e) {
			UserFoundExceptionMapper ufem = new UserFoundExceptionMapper();
			return ufem.toResponse(e);
		}

		return null;
		//TODO renvoyer response userFound avec les infos

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
