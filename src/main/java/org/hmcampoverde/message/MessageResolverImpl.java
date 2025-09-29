package org.hmcampoverde.message;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageResolverImpl implements MessageResolver {

	private final MessageSource messageSource;

	@Override
	public String resolve(String key, Object... args) {
		return messageSource.getMessage(key, args, "key does not exist: ".concat(key), Locale.of("es"));
	}
}
