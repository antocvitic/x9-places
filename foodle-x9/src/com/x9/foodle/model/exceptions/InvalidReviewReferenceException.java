package com.x9.foodle.model.exceptions;

@SuppressWarnings("serial")
public class InvalidReviewReferenceException extends InvalidSolrModelException {

	public InvalidReviewReferenceException() {
		super();
	}

	public InvalidReviewReferenceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidReviewReferenceException(String msg) {
		super(msg);
	}

	public InvalidReviewReferenceException(Throwable cause) {
		super(cause);
	}

}
