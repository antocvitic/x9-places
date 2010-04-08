package com.x9.foodle.user.exceptions;

@SuppressWarnings("serial")
public class BadEmailException extends InvalidUserException {

	public BadEmailException() {
	}

	public BadEmailException(String msg) {
		super(msg);
	}

	public BadEmailException(Throwable cause) {
		super(cause);
	}

	public BadEmailException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
