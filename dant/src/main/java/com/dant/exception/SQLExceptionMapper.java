package com.dant.exception;

import javax.ws.rs.core.Response;

public class SQLExceptionMapper {

	public Response toResponse(java.sql.SQLException e) {
		return Response.status(503).type("application/json").entity("{\"c\":10}").build();
	}
}
