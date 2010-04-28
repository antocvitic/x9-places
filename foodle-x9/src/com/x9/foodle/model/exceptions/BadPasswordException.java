package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class BadPasswordException extends InvalidUserException {

	public BadPasswordException() {
	}

	public BadPasswordException(String msg) {
		super(msg);
	}

	public BadPasswordException(Throwable cause) {
		super(cause);
	}

	public BadPasswordException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
