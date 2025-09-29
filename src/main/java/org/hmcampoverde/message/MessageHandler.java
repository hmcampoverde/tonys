package org.hmcampoverde.message;

import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.response.MessageResponse;
import org.hmcampoverde.dto.response.Severity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandler {

	private final MessageResolver messageResolver;

	public <T> MessageResponse<T> generateCreationMessage(String key, T data) {
		return generateMessage(Severity.success, key, data);
	}

	public <T> MessageResponse<T> generateUpdateMessage(String key, T data) {
		return generateMessage(Severity.info, key, data);
	}

	public <T> MessageResponse<T> generateDeleteMessage(String key, T data) {
		return generateMessage(Severity.error, key, data);
	}

	private <T> MessageResponse<T> generateMessage(Severity type, String key, T data) {
		return new MessageResponse<T>(type, messageResolver.resolve("summary.success"), messageResolver.resolve(key), data);
	}
}
