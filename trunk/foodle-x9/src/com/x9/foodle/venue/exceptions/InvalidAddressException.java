package com.x9.foodle.venue.exceptions;

@SuppressWarnings("serial")
public class InvalidAddressException extends InvalidVenueException {

	public InvalidAddressException() {
		super();
	}

	public InvalidAddressException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidAddressException(String msg) {
		super(msg);
	}

	public InvalidAddressException(Throwable cause) {
		super(cause);
	}

}
