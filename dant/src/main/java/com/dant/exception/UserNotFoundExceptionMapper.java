package com.dant.exception;

import javax.ws.rs.core.Response;

public class UserNotFoundExceptionMapper {

	public Response toResponse(UserNotFoundException e) {
		return Response.status(200).entity(e.getMessage()).type("text/plain").build();
	}
}
