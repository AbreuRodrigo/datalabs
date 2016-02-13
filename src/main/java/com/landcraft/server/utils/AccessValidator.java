package com.landcraft.server.utils;

import javax.ws.rs.core.HttpHeaders;

public class AccessValidator {
	private final String USER = "landcraftserveruser";
	private final String PASSWORD = "xkjhahsd98h98hodkln038n2oij2309";
	private final String ACCESS_DENIED_MESSAGE = "Access denied";
		
	public void validate(HttpHeaders headers) throws Exception {
		final String user = headers.getRequestHeader("user").get(0);
		final String password = headers.getRequestHeader("password").get(0);
		
		if(!(USER.equals(user) && PASSWORD.equals(password))) {
			throw new Exception(ACCESS_DENIED_MESSAGE);
		}
	}
}
