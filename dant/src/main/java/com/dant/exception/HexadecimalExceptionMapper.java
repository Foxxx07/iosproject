package com.dant.exception;

import javax.ws.rs.core.Response;

public class HexadecimalExceptionMapper {
	public Response toResponse(HexadecimalException e) {
		return Response.status(200).entity(e.getMessage()).type("text/plain").build();
	}
}
