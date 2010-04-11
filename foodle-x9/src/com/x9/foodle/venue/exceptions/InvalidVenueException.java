package com.x9.foodle.venue.exceptions;

@SuppressWarnings("serial")
public class InvalidVenueException extends Exception {

	public InvalidVenueException() {
		super();
	}

	public InvalidVenueException(String msg) {
		super(msg);
	}

	public InvalidVenueException(Throwable cause) {
		super(cause);
	}

	public InvalidVenueException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
