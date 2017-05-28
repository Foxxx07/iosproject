package com.dant.exception;

import javax.ws.rs.core.Response;

public class UserNotConnectedExceptionMapper {
	public Response toResponse(UserNotConnectedException e) {
		return Response.status(400).entity(e.getMessage()).type("text/plain").build();
	}
}
