package org.hmcampoverde.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ValidationException extends RuntimeException {

	private final HttpStatusCode status;

	public ValidationException(String message) {
		super(message);
		status = HttpStatus.BAD_REQUEST;
	}

	public ValidationException(String message, HttpStatusCode status) {
		super(message);
		this.status = status;
	}
}
