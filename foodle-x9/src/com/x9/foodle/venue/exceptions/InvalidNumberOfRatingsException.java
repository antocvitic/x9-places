package com.x9.foodle.venue.exceptions;

@SuppressWarnings("serial")
public class InvalidNumberOfRatingsException extends InvalidVenueException {

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
