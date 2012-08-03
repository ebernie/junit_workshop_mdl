package com.workshop.commerce.ex;

public class InvalidOrderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2791229421843989437L;

	public InvalidOrderException() {
	}

	public InvalidOrderException(String message) {
		super(message);
	}

	public InvalidOrderException(Throwable cause) {
		super(cause);
	}

	public InvalidOrderException(String message, Throwable cause) {
		super(message, cause);
	}

}
