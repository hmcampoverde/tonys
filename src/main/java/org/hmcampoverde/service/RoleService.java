package org.hmcampoverde.service;

import org.hmcampoverde.entity.Role;
import org.springframework.data.repository.query.Param;

public interface RoleService {
	public Role findByCode(@Param("code") String code);
}
