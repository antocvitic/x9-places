package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class InvalidAddressException extends InvalidSolrModelException {

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
