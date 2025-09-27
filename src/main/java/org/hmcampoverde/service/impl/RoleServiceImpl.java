package org.hmcampoverde.service.impl;

import lombok.RequiredArgsConstructor;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.model.Role;
import org.hmcampoverde.repository.RoleRepository;
import org.hmcampoverde.response.MessageHandler;
import org.hmcampoverde.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final MessageHandler messageHandler;
	private final RoleRepository repository;

	@Override
	public Role findByCode(String code) {
		return repository
			.findByCode(code)
			.orElseThrow(() ->
				new CustomException(messageHandler.getDetail("role.code.notfound", code), HttpStatus.NOT_FOUND)
			);
	}
}
