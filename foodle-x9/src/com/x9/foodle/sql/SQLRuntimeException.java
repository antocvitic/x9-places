package com.x9.foodle.sql;

@SuppressWarnings("serial")
public class SQLRuntimeException extends RuntimeException {

	public SQLRuntimeException() {
	}

	public SQLRuntimeException(String msg) {
		super(msg);
	}

	public SQLRuntimeException(Throwable cause) {
		super(cause);
	}

	public SQLRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
