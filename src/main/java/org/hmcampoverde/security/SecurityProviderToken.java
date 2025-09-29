package org.hmcampoverde.security;

import java.text.ParseException;
import org.springframework.security.core.Authentication;

public interface SecurityProviderToken {
	public String generate(Authentication authentication);

	public String refresh(String token) throws ParseException;

	public boolean validate(String token);

	public String getUsername(String token);
}
