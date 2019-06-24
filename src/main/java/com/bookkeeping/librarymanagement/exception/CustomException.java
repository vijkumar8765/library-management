package com.bookkeeping.librarymanagement.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Setter
@Getter
public class CustomException extends ResponseStatusException {

	private String message;
	private HttpStatus httpStatus;
	private boolean error;

	public CustomException(String message, HttpStatus httpStatus, boolean error){
		super(httpStatus, message);
		this.message = message;
		this.httpStatus = httpStatus;
		this.error = error;

	}
}