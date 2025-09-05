package org.hmcampoverde.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.message.MessageHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
	public ResponseEntity<Message> handleException(Exception exception) {
		Message message = Message.builder().severity("error").summary("title.fatal").detail(exception.getMessage()).build();

		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Message> handleCustomException(CustomException exception) {
		Message message = Message.builder()
			.severity("error")
			.summary(messageHandler.getDetail("title.fatal"))
			.detail(exception.getMessage())
			.build();

		return ResponseEntity.status(exception.getStatus()).body(message);
	}

	@ExceptionHandler(value = { NoResourceFoundException.class })
	public ResponseEntity<Message> handleNoResourceFoundException(NoResourceFoundException exception) {
		Message message = Message.builder()
			.severity("error")
			.summary(messageHandler.getDetail("title.fatal"))
			.detail(messageHandler.getDetail("validation.resource.notFound", new Object[] { exception.getResourcePath() }))
			.build();

		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<List<Message>> handleConstraintViolationException(ConstraintViolationException exception) {
		List<Message> errors = new ArrayList<>();

		exception
			.getConstraintViolations()
			.stream()
			.forEach(violation ->
				errors.add(
					Message.builder()
						.severity("error")
						.summary("title.fatal")
						.detail(
							messageHandler.getDetail(
								"validation.size.min.message",
								new Object[] {
									violation.getPropertyPath(),
									violation.getConstraintDescriptor().getAttributes().get("min")
								}
							)
						)
						.build()
				)
			);

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Message> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception
	) {
		Message message = Message.builder()
			.severity("error")
			.summary(messageHandler.getDetail("title.fatal"))
			.detail(messageHandler.getDetail("validation.argumentType.mismatch"))
			.build();

		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<Message>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception
	) {
		List<Message> errors = new ArrayList<>();

		exception
			.getBindingResult()
			.getAllErrors()
			.forEach(violation ->
				errors.add(
					Message.builder()
						.severity("error")
						.summary(messageHandler.getDetail("title.fatal"))
						.detail(violation.getDefaultMessage())
						.build()
				)
			);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Message> handleBadCredentialsException(BadCredentialsException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			Message.builder()
				.severity("error")
				.summary(messageHandler.getDetail("title.fatal"))
				.detail(messageHandler.getDetail("user.username.invalid"))
				.build()
		);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Message> handleAccessDeniedException(AccessDeniedException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
			Message.builder()
				.severity("error")
				.summary("title.fatal")
				.detail(messageHandler.getDetail("user.username.unauthorized"))
				.build()
		);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<Message> handleDisabledException(DisabledException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			Message.builder()
				.severity("error")
				.summary("title.fatal")
				.detail(messageHandler.getDetail("user.username.disabled"))
				.build()
		);
	}
}
