package com.dant.exception;

import javax.ws.rs.core.Response;

public class InvalidUserKeyExceptionMapper {

	public Response toResponse(InvalidUserKeyException e) {
		return Response.status(200).entity(e.getMessage()).type("text/plain").build();
	}
}
