package com.dant.exception;

import javax.ws.rs.core.Response;

public class InvalidEmailExceptionMapper {


	public Response toResponse(InvalidEmailException e) {
		return Response.status(200).entity(e.getMessage()).type("text/plain").build();
	}
	
}
