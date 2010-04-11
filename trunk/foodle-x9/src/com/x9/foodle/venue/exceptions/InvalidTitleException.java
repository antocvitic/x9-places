package com.x9.foodle.venue.exceptions;

@SuppressWarnings("serial")
public class InvalidTitleException extends InvalidVenueException {

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
