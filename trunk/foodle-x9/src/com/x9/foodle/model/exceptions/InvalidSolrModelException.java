package com.x9.foodle.model.exceptions;

import com.x9.foodle.util.MessageDispatcher.ErrorMessage;
import com.x9.foodle.util.MessageDispatcher.Message;

@SuppressWarnings("serial")
public class InvalidSolrModelException extends Exception {

	public InvalidSolrModelException() {
		super();
	}

	public InvalidSolrModelException(String msg) {
		super(msg);
	}

	public InvalidSolrModelException(Throwable cause) {
		super(cause);
	}

	public InvalidSolrModelException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public Message toMessage(String prefix) {
		return new ErrorMessage(prefix + getMessage());
	}
}
