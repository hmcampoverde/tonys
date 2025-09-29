package org.hmcampoverde.message;

public interface MessageResolver {
	String resolve(String key, Object... args);
}
