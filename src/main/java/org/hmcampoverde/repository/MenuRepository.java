package org.hmcampoverde.repository;

import java.util.List;
import org.hmcampoverde.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	@Query(
		"select menu from Menu menu left join fetch menu.parent left join fetch menu.roles roles where menu.deleted = false and roles.code = :role order by menu.id asc"
	)
	public List<Menu> findByRole(@Param("role") String role);
}
