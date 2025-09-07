package org.hmcampoverde.response;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandler {

	private final MessageSource messageSource;

	public <T> MessageResponse<T> buildCreationMessage(String key, T data) {
		return new MessageResponse<T>(Severity.success, getDetail("summary.success"), getDetail(key), data);
	}

	public <T> MessageResponse<T> buildUpdateMessage(String key, T data) {
		return new MessageResponse<T>(Severity.info, getDetail("summary.success"), getDetail(key), data);
	}

	public <T> MessageResponse<T> buildDeleteMessage(String key, T data) {
		return new MessageResponse<T>(Severity.error, getDetail("summary.success"), getDetail(key), data);
	}

	public String getDetail(String key, Object... arguments) {
		return messageSource.getMessage(key, arguments, "key does not exist: ".concat(key), Locale.of("es"));
	}
}
