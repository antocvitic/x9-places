package com.x9.foodle.user.exceptions;

@SuppressWarnings("serial")
public class InvalidUserException extends Exception {

	public InvalidUserException() {
	}

	public InvalidUserException(String msg) {
		super(msg);
	}

	public InvalidUserException(Throwable cause) {
		super(cause);
	}

	public InvalidUserException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
