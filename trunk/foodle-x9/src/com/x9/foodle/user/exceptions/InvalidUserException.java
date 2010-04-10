package com.x9.foodle.user.exceptions;

import com.x9.foodle.util.QuickURLEncoder;

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
		return getMessage();//QuickURLEncoder.encode(getMessage());
	}

}
