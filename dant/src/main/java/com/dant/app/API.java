package com.dant.app;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)

public class API {
	
	@POST 
	@Path("/u")
	public String createUser(@DefaultValue("Null") @FormParam("fname") String fname,
			@DefaultValue("Null") @FormParam("lname") String lname,
			@DefaultValue("Null") @FormParam("email") String email,
			@DefaultValue("Null") @FormParam("password") String password)
			{
		System.out.println(fname);
		System.out.println(lname);
		System.out.println(email);
		System.out.println(password);
		return fname.toString();}
	
	@GET
	@Path("/u")
	public String getUsers(){
		return "Hello ";
	}
	
	
	
	
}


