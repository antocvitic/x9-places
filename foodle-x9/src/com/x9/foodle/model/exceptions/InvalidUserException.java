package com.x9.foodle.model.exceptions;

import com.x9.foodle.util.URLUtils;

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

	public String getURLEncodedMessage() {
		return URLUtils.encodeLatin(getMessage());
	}

}
