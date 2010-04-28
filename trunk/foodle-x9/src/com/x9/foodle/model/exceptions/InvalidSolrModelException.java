package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class InvalidSolrModelException extends Exception {

	public InvalidSolrModelException() {
		super();
	}

	public InvalidSolrModelException(String msg) {
		super(msg);
	}

	public InvalidSolrModelException(Throwable cause) {
		super(cause);
	}

	public InvalidSolrModelException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
