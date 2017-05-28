package com.dant.exception;

import javax.ws.rs.core.Response;

public class SQLExceptionMapper {

	public Response toResponse(java.sql.SQLException e) {
		return Response.status(500).entity(e.getMessage()).type("text/plain").build();
	}
}
