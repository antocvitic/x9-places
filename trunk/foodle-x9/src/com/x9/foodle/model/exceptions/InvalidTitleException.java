package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class InvalidTitleException extends InvalidSolrModelException {

	public InvalidTitleException() {
		super();
	}

	public InvalidTitleException(String msg) {
		super(msg);
	}

	public InvalidTitleException(Throwable cause) {
		super(cause);
	}

	public InvalidTitleException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
