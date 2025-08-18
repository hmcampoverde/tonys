package org.hmcampoverde.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatusCode;

@Getter
@Builder
@AllArgsConstructor
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatusCode status;

	public CustomException(@NonNull String message, @NonNull HttpStatusCode status) {
		super(message);
		this.status = status;
	}
}
