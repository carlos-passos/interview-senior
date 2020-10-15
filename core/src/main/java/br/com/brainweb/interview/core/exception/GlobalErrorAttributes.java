package br.com.brainweb.interview.core.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.google.gson.annotations.Expose;

public class GlobalErrorAttributes {
	
	@Expose private int code;
	@Expose private String error;
	@Expose private String message;
	@Expose private List<GlobalFieldError> errorFields;
	
	public GlobalErrorAttributes(HttpStatus httpStatus, String message) {
		super();
		this.code = httpStatus.value();
		this.error = httpStatus.getReasonPhrase();
		this.message = message;
	}
	
	public GlobalErrorAttributes(HttpStatus httpStatus, String message,
			List<GlobalFieldError> errorFields) {
		super();
		this.code = httpStatus.value();
		this.error = httpStatus.getReasonPhrase();
		this.message = message;
		this.errorFields = errorFields;
	}

	public int getCode() {
		return code;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public List<GlobalFieldError> getErrorFields() {
		return errorFields;
	}

	
}
