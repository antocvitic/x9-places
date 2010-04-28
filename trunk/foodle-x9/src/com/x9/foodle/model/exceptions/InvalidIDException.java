package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class InvalidIDException extends InvalidSolrModelException {

	public InvalidIDException() {
		super();
	}

	public InvalidIDException(String msg) {
		super(msg);
	}

	public InvalidIDException(Throwable cause) {
		super(cause);
	}

	public InvalidIDException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
