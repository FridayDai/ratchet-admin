package com.ratchethealth.admin.exceptions

class ApiAccessException extends Exception {
	public ApiAccessException() {
		super();
	}

	public ApiAccessException(String message) {
		super(message);
	}
}