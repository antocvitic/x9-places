package com.x9.foodle.model.exceptions;

import com.x9.foodle.util.MessageDispatcher.ErrorMessage;
import com.x9.foodle.util.MessageDispatcher.Message;

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
	
	public Message toMessage(String prefix) {
		return new ErrorMessage(prefix + getMessage());
	}

}
