package com.x9.foodle.venue.exceptions;

@SuppressWarnings("serial")
public class InvalidAverageRatingException extends InvalidVenueException {

	public InvalidAverageRatingException() {
		super();
	}

	public InvalidAverageRatingException(String msg) {
		super(msg);
	}

	public InvalidAverageRatingException(Throwable cause) {
		super(cause);
	}

	public InvalidAverageRatingException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
