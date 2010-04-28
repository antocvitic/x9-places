package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class InvalidTextException extends InvalidSolrModelException {
	public InvalidTextException() {
		super();
	}

	public InvalidTextException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidTextException(String msg) {
		super(msg);
	}

	public InvalidTextException(Throwable cause) {
		super(cause);
	}
}
