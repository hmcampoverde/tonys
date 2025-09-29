package org.hmcampoverde.exception;

import jakarta.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.response.MessageResponse;
import org.hmcampoverde.dto.response.Severity;
import org.hmcampoverde.message.MessageResolver;
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

	private final MessageResolver messageResolver;

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<MessageResponse<String>> handleException(Exception exception) {
		return ResponseEntity.badRequest().body(
			new MessageResponse<String>(Severity.error, messageResolver.resolve("summary.error"), exception.getMessage())
		);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MessageResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
			new MessageResponse<String>(Severity.error, messageResolver.resolve("summary.error"), exception.getMessage())
		);
	}

	@ExceptionHandler(value = { NoResourceFoundException.class })
	public ResponseEntity<MessageResponse<String>> handleNoResourceFoundException(NoResourceFoundException exception) {
		return ResponseEntity.status(exception.getStatusCode()).body(
			new MessageResponse<String>(
				Severity.error,
				messageResolver.resolve("summary.error"),
				messageResolver.resolve("validation.notFound", exception.getResourcePath())
			)
		);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<MessageResponse<String>> handleCustomException(CustomException exception) {
		return ResponseEntity.status(exception.getStatus()).body(
			new MessageResponse<String>(Severity.error, messageResolver.resolve("summary.error"), exception.getMessage())
		);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<MessageResponse<String>> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception
	) {
		return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode())).body(
			new MessageResponse<String>(
				Severity.error,
				messageResolver.resolve("summary.error"),
				messageResolver.resolve("validation.invalidParameter", exception.getName())
			)
		);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<List<MessageResponse<String>>> handleConstraintViolationException(
		ConstraintViolationException exception
	) {
		List<MessageResponse<String>> errors = new ArrayList<>();

		exception
			.getConstraintViolations()
			.stream()
			.forEach(violation -> {
				errors.add(
					new MessageResponse<String>(
						Severity.error,
						messageResolver.resolve("summary.error"),
						messageResolver.resolve(
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
	public ResponseEntity<List<MessageResponse<String>>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception
	) {
		List<MessageResponse<String>> errors = new ArrayList<>();

		exception
			.getBindingResult()
			.getAllErrors()
			.forEach(violation ->
				errors.add(
					new MessageResponse<String>(
						Severity.error,
						messageResolver.resolve("summary.error"),
						violation.getDefaultMessage()
					)
				)
			);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<MessageResponse<String>> handleBadCredentialsException(BadCredentialsException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			new MessageResponse<String>(
				Severity.error,
				messageResolver.resolve("summary.error"),
				messageResolver.resolve("user.username.invalid")
			)
		);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<MessageResponse<String>> handleAccessDeniedException(AccessDeniedException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
			new MessageResponse<String>(
				Severity.error,
				messageResolver.resolve("summary.error"),
				messageResolver.resolve("user.username.unauthorized")
			)
		);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<MessageResponse<String>> handleDisabledException(DisabledException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			new MessageResponse<String>(
				Severity.error,
				messageResolver.resolve("summary.error"),
				messageResolver.resolve("user.username.disabled")
			)
		);
	}
}
