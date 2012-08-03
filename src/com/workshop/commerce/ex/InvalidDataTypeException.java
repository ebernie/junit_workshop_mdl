package com.workshop.commerce.ex;

public class InvalidDataTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5233136726355428721L;

	public InvalidDataTypeException() {
		super();
	}

	public InvalidDataTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDataTypeException(String message) {
		super(message);
	}

	public InvalidDataTypeException(Throwable cause) {
		super(cause);
	}
}
