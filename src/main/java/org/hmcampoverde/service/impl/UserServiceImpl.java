package org.hmcampoverde.service.impl;

import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.TokenDto;
import org.hmcampoverde.security.SecurityProvider;
import org.hmcampoverde.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final AuthenticationManager authenticationManager;
	private final SecurityProvider securityProvider;

	@Override
	public TokenDto login(String username, String password) {
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return TokenDto.builder().token(securityProvider.buildToken(authentication)).build();
	}

	@Override
	public TokenDto refresh(String token) throws ParseException {
		return TokenDto.builder().token(securityProvider.refreshToken(token)).build();
	}
}
