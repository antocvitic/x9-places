package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class InvalidNumberOfRatingsException extends InvalidSolrModelException {

	public InvalidNumberOfRatingsException() {
		super();
	}

	public InvalidNumberOfRatingsException(String msg) {
		super(msg);
	}

	public InvalidNumberOfRatingsException(Throwable cause) {
		super(cause);
	}

	public InvalidNumberOfRatingsException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
