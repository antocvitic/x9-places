package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class InvalidVenueReferenceException extends InvalidSolrModelException {
	public InvalidVenueReferenceException() {
		super();
	}

	public InvalidVenueReferenceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidVenueReferenceException(String msg) {
		super(msg);
	}

	public InvalidVenueReferenceException(Throwable cause) {
		super(cause);
	}
}
