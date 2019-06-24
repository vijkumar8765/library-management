package com.bookkeeping.librarymanagement.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomErrorType extends RuntimeException {

	private String errorMessage;

	public CustomErrorType(String errorMessage){
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}