package org.hmcampoverde.exception;

import jakarta.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.response.ErrorReponse;
import org.hmcampoverde.response.MessageHandler;
import org.hmcampoverde.response.Severity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

	private final MessageHandler messageHandler;

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorReponse> handleException(Exception exception) {
		return ResponseEntity.badRequest().body(
			new ErrorReponse(Severity.error, messageHandler.getDetail("summary.error"), exception.getMessage())
		);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorReponse> handleCustomException(CustomException exception) {
		return ResponseEntity.status(exception.getStatus()).body(
			new ErrorReponse(Severity.error, messageHandler.getDetail("summary.error"), exception.getMessage())
		);
	}

	@ExceptionHandler(value = { NoResourceFoundException.class })
	public ResponseEntity<ErrorReponse> handleNoResourceFoundException(NoResourceFoundException exception) {
		return ResponseEntity.badRequest().body(
			new ErrorReponse(
				Severity.error,
				messageHandler.getDetail("summary.error"),
				messageHandler.getDetail("validation.notFound", exception.getResourcePath())
			)
		);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorReponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception
	) {
		return ResponseEntity.badRequest().body(
			new ErrorReponse(
				Severity.error,
				messageHandler.getDetail("summary.error"),
				messageHandler.getDetail("validation.invalidParameter", exception.getName())
			)
		);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<List<ErrorReponse>> handleConstraintViolationException(ConstraintViolationException exception) {
		List<ErrorReponse> errors = new ArrayList<>();

		exception
			.getConstraintViolations()
			.stream()
			.forEach(violation -> {
				errors.add(
					new ErrorReponse(
						Severity.error,
						messageHandler.getDetail("summary.error"),
						messageHandler.getDetail(
							"validation.min",
							new Object[] {
								violation.getPropertyPath().toString().split("\\.")[1],
								violation.getConstraintDescriptor().getAttributes().get("value")
							}
						)
					)
				);
			});

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErrorReponse>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception
	) {
		List<ErrorReponse> errors = new ArrayList<>();

		exception
			.getBindingResult()
			.getAllErrors()
			.forEach(violation ->
				errors.add(
					new ErrorReponse(Severity.error, messageHandler.getDetail("summary.error"), violation.getDefaultMessage())
				)
			);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorReponse> handleBadCredentialsException(BadCredentialsException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			new ErrorReponse(
				Severity.error,
				messageHandler.getDetail("summary.error"),
				messageHandler.getDetail("user.username.invalid")
			)
		);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorReponse> handleAccessDeniedException(AccessDeniedException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
			new ErrorReponse(
				Severity.error,
				messageHandler.getDetail("summary.error"),
				messageHandler.getDetail("user.username.unauthorized")
			)
		);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ErrorReponse> handleDisabledException(DisabledException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			new ErrorReponse(
				Severity.error,
				messageHandler.getDetail("summary.error"),
				messageHandler.getDetail("user.username.disabled")
			)
		);
	}
}
