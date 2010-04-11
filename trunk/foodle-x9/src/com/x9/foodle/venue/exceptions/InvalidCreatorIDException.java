package com.x9.foodle.venue.exceptions;

@SuppressWarnings("serial")
public class InvalidCreatorIDException extends InvalidVenueException {

	public InvalidCreatorIDException() {
		super();
	}

	public InvalidCreatorIDException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidCreatorIDException(String msg) {
		super(msg);
	}

	public InvalidCreatorIDException(Throwable cause) {
		super(cause);
	}

}
