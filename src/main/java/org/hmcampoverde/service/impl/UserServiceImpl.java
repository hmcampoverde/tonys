package org.hmcampoverde.service.impl;

import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.TokenDto;
import org.hmcampoverde.entity.User;
import org.hmcampoverde.security.SecurityProviderTokenImpl;
import org.hmcampoverde.service.RoleService;
import org.hmcampoverde.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final AuthenticationManager authenticationManager;
	private final SecurityProviderTokenImpl securityProvider;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public TokenDto login(String username, String password) {
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return TokenDto.builder().token(securityProvider.generate(authentication)).build();
	}

	@Override
	public TokenDto refresh(String token) throws ParseException {
		return TokenDto.builder().token(securityProvider.refresh(token)).build();
	}

	@Override
	public User buildUser(String username, String password) {
		return User.builder()
			.username(username)
			.password(passwordEncoder.encode(password))
			.role(roleService.findByCode("user"))
			.actived(Boolean.TRUE)
			.build();
	}
}
