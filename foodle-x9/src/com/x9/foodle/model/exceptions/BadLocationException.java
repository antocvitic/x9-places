package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class BadLocationException extends InvalidUserException {

	public BadLocationException() {
	}

	public BadLocationException(String msg) {
		super(msg);
	}

	public BadLocationException(Throwable cause) {
		super(cause);
	}

	public BadLocationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
