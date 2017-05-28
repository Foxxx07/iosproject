package com.dant.util;

import javax.ws.rs.core.Response;

import com.dant.exception.InvalidEmailException;
import com.dant.exception.InvalidUserKeyException;
import com.dant.exception.UserFoundException;

public class UserFoundExceptionMapper {
	public Response toResponse(UserFoundException e) {
		return Response.status(200).entity(e.getMessage()).type("text/plain").build();
	}
}
