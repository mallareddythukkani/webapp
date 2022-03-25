package com.parkingservice.webapp.util;

public class CustomMessageException extends RuntimeException {
	private static final long serialVersionUID = 1;
	private int status=2;
	public CustomMessageException(String message, Throwable cause, int status) {
		super(message, cause);
		this.status = status;
	}

	public CustomMessageException(String message, int status) {
		super(message);
		this.status = status;
	}
	
	public CustomMessageException(String message) {
		super(message);
	}

	public CustomMessageException(Throwable cause) {
		super(cause);
	}

	public int getStatus() {
		return status;
	}
	
	
	
}
