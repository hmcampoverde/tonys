package org.hmcampoverde.service;

import java.text.ParseException;
import org.hmcampoverde.dto.TokenDto;

public interface UserService {
	public TokenDto login(String username, String password);

	public TokenDto refresh(String token) throws ParseException;
}
