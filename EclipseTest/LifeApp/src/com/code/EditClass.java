package com.code;

import javafx.stage.Stage;

public abstract class EditClass implements Startable {
	private Exception exception;
	
	protected void setException(Exception e) {
		this.exception = e;
	}

	public Exception getException() {
		return exception;
	}

}
