package com.dant.util;

import javax.ws.rs.core.Response;

import com.dant.entity.User;
import com.dant.exception.UserFoundException;
import com.dant.filter.GsonProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserFoundExceptionMapper {
	//GsonProvider gson = new GsonProvider();
	private final Gson gson = new GsonBuilder().serializeNulls().create();
	User u = new User("Toto","Tata","t@t.fr","password");
	public Response toResponse(UserFoundException e) {
		return Response.status(200).type("application/json").entity(gson.toJson(u)).build();
	}
}
