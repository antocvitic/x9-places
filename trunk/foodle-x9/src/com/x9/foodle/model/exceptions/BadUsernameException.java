package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class BadUsernameException extends InvalidUserException {

	public BadUsernameException() {
	}

	public BadUsernameException(String msg) {
		super(msg);
	}

	public BadUsernameException(Throwable cause) {
		super(cause);
	}

	public BadUsernameException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
