package com.dant.exception;

import javax.ws.rs.core.Response;

public class EmptyNameExceptionMapper {
	
	public Response toResponse(EmptyNameException e) {
		return Response.status(200).entity(e.getMessage()).type("text/plain").build();
	}
}
