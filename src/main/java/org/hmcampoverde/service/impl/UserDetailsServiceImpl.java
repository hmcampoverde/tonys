package org.hmcampoverde.service.impl;

import io.jsonwebtoken.lang.Collections;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.model.Role;
import org.hmcampoverde.model.User;
import org.hmcampoverde.repository.UserRepository;
import org.hmcampoverde.security.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository
			.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return new UserPrincipal(user, getRoles(user.getRole()));
	}

	private Collection<? extends GrantedAuthority> getRoles(Role role) {
		return Collections.of(new SimpleGrantedAuthority("ROLE_".concat(role.getCode().toUpperCase())));
	}
}
