package com.dant.exception;

import javax.ws.rs.core.Response;

public class EmailExceptionMapper {

	public Response toResponse(EmailException e) {
		return Response.status(200).entity(e.getMessage()).type("text/plain").build();
	}
	
	public Response toResponseFromAnotherfunction(EmailException e) {
		return Response.status(400).entity(e.getMessage()).type("text/plain").build();
	}
	
}
