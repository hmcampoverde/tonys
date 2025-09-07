package org.hmcampoverde.repository;

import java.util.List;
import org.hmcampoverde.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query("select c from Category c left join fetch c.parent where c.deleted = false order by c.name")
	public @NonNull List<Category> findAll();

	@Query(
		"select case when exists (select 1 from Category c1_0 where c1_0.id != :id and c1_0.name = :name and c1_0.deleted = false ) then true else false end from Category"
	)
	public Boolean existsByName(@Param("id") Long id, @Param("name") String name);
}
