package com.x9.foodle.user.exceptions;

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
