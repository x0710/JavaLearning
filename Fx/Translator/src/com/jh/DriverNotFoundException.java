package com.jh;

import java.sql.SQLException;

public class DriverNotFoundException extends SQLException {
	private static final long serialVersionUID = -4278759037986585813L;

	public DriverNotFoundException() {}

	public DriverNotFoundException(String reason) {
		super(reason);
	}

}
