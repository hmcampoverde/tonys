package org.hmcampoverde.message;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandler {

	private final MessageSource messageSource;

	public String getDetail(String key) {
		return getDetail(key, new Object[] {});
	}

	public String getDetail(String key, Object... arguments) {
		return messageSource.getMessage(key, arguments, "key does not exist: ".concat(key), Locale.of("es"));
	}
}
