package com.ratchethealth.admin.exceptions

class ServerException extends RuntimeException {

	private Integer statusId;

	ServerException() {
		super()
	}

	ServerException(String message) {
		super(message)
	}

	ServerException(Integer status, String message) {
		super(message)
		this.statusId = status
	}
}
