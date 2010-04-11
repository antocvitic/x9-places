package com.x9.foodle.datastore;

@SuppressWarnings("serial")
public class SolrRuntimeException extends RuntimeException {

	public SolrRuntimeException() {
		super();
	}

	public SolrRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SolrRuntimeException(String msg) {
		super(msg);
	}

	public SolrRuntimeException(Throwable cause) {
		super(cause);
	}

}
