package org.hmcampoverde.repository;

import java.util.List;
import java.util.Optional;
import org.hmcampoverde.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface RoleRepository extends JpaRepository<Role, Long> {
	@Query("select role from Role role where role.deleted = false order by role.name")
	public @NonNull List<Role> findAll();

	@Query("select role from Role role where role.code = :code and role.deleted = false order by role.name")
	public Optional<Role> findByCode(@Param("code") String code);
}
