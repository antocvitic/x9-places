package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class BadNameException extends InvalidUserException {

	public BadNameException() {
	}

	public BadNameException(String msg) {
		super(msg);
	}

	public BadNameException(Throwable cause) {
		super(cause);
	}

	public BadNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
