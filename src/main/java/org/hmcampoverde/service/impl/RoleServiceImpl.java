package org.hmcampoverde.service.impl;

import lombok.RequiredArgsConstructor;
import org.hmcampoverde.entity.Role;
import org.hmcampoverde.exception.ResourceNotFoundException;
import org.hmcampoverde.message.MessageResolver;
import org.hmcampoverde.repository.RoleRepository;
import org.hmcampoverde.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final MessageResolver messageResolver;

	private final RoleRepository repository;

	@Override
	public Role findByCode(String code) {
		return repository
			.findByCode(code)
			.orElseThrow(() -> new ResourceNotFoundException(messageResolver.resolve("role.code.notfound", code)));
	}
}
