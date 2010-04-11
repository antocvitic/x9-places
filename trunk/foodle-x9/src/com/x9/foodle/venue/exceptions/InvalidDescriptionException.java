package com.x9.foodle.venue.exceptions;

@SuppressWarnings("serial")
public class InvalidDescriptionException extends InvalidVenueException {

	public InvalidDescriptionException() {
		super();
	}

	public InvalidDescriptionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidDescriptionException(String msg) {
		super(msg);
	}

	public InvalidDescriptionException(Throwable cause) {
		super(cause);
	}

}
