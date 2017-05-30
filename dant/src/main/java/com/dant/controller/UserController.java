package com.dant.controller;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mariadb.jdbc.internal.util.dao.QueryException;

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
import com.dant.exception.HexadecimalException;
import com.dant.exception.HexadecimalExceptionMapper;
import com.dant.exception.InvalidEmailException;
import com.dant.exception.InvalidEmailExceptionMapper;
import com.dant.exception.InvalidTokenException;
import com.dant.exception.InvalidTokenExceptionMapper;
import com.dant.exception.InvalidUserKeyException;
import com.dant.exception.InvalidUserKeyExceptionMapper;
import com.dant.exception.QueryExceptionMapper;
import com.dant.exception.SQLExceptionMapper;
import com.dant.exception.UserFoundException;
import com.dant.exception.UserNotFoundException;
import com.dant.exception.UserNotFoundExceptionMapper;


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
		@DefaultValue("") @FormParam("password") String password
	) {
		try {
			return Response.status(200).type("application/json").entity("{\"c\":0, \"data\":\"" + userBusiness.createUser(fname, lname, email, password) + "\"}").build();
		} catch (EmptyNameException e) {
			return new EmptyNameExceptionMapper().toResponse(e);
		} catch (EmailException e) {
			return new EmailExceptionMapper().toResponse(e);
		} catch(EmptyEmailException e) {
			return new EmptyEmailExceptionMapper().toResponse(e);
		} catch(EmptyPasswordException e) {
			return new EmptyPasswordExceptionMapper().toResponse(e);
		} catch(InvalidEmailException e) {
			return new InvalidEmailExceptionMapper().toResponse(e);
		} catch(SQLException e) {
			return new SQLExceptionMapper().toResponse(e);
		}
	}

	@GET
	public Response searchP(@DefaultValue("") @QueryParam("search") String query, @DefaultValue("0") @QueryParam("n") int page) throws SQLException, UserFoundException{
		try {
			return Response.status(200).type("application/json").entity("{\"c\":0,\"data\":" + userBusiness.searchUser(query, page) + "}").build();
		} catch (SQLException e) {
			return new SQLExceptionMapper().toResponse(e);
		}
	}

	@Path("/{id}")
	@GET
	public Response listMetaDataForUser(@PathParam("id") String id, @DefaultValue("") @HeaderParam("x-token") String sessionId) {
		User tmp;
		if (id.equals("me")) {
			id = userBusiness.getUser(sessionId);
		}

		try {
			tmp = userBusiness.getUserById(id);
			return Response.status(200).type("application/json").entity("{\"c\":0,\"data\":{\"fname\":\""+tmp.getFname()+"\",\"lname\":\""+tmp.getLname()+"\"}").build();
		} catch (HexadecimalException e) {
			return new HexadecimalExceptionMapper().toResponse(e);
		} catch (InvalidUserKeyException e) {
			return new InvalidUserKeyExceptionMapper().toResponse(e);
		} catch (UserNotFoundException e) {
			return new UserNotFoundExceptionMapper().toResponse(e);
		} catch (SQLException e) {
			return new SQLExceptionMapper().toResponse(e);
		}
	}


	@Path("/me")
	@POST
	//TODO x-token header
	public Response updateUser(
		@DefaultValue("") @FormParam("fname") String fname,
		@DefaultValue("") @FormParam("lname") String lname,
		@DefaultValue("") @FormParam("email") String email,
		@DefaultValue("") @FormParam("password") String password,
		@DefaultValue("") @HeaderParam("x-token") String sessionId
	) {
		try {
			userBusiness.updateUser(sessionId, fname, lname, email, password);
			return Response.status(200).type("application/json").entity("{\"c\":0}").build();
		} catch (QueryException e) {
			return new QueryExceptionMapper().toResponse(e);
		} catch (SQLException e) {
			return new SQLExceptionMapper().toResponse(e);
		} catch (EmptyNameException e) {
			return new EmptyNameExceptionMapper().toResponse(e);
		} catch(EmptyEmailException e) {
			return new EmptyEmailExceptionMapper().toResponse(e);
		} catch(EmptyPasswordException e) {
			return new EmptyPasswordExceptionMapper().toResponse(e);
		} catch(InvalidEmailException e) {
			return new InvalidEmailExceptionMapper().toResponse(e);
		} catch (HexadecimalException e) {
			return new HexadecimalExceptionMapper().toResponse(e);
		} catch (InvalidUserKeyException e) {
			return new InvalidUserKeyExceptionMapper().toResponse(e);
		} catch (UserNotFoundException e) {
			return new UserNotFoundExceptionMapper().toResponse(e);
		} catch (InvalidTokenException e) {
			return new InvalidTokenExceptionMapper().toResponse(e);
		}
	}
}
