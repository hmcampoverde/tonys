package org.hmcampoverde.repository;

import java.util.List;
import org.hmcampoverde.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface InstitutionRepository extends JpaRepository<Institution, Long>, JpaSpecificationExecutor<Institution> {
	@Query("select ie from Institution ie where ie.deleted = false order by ie.amie")
	public @NonNull List<Institution> findAll();

	@Query(
		"select case when exists (select 1 from Institution i1_0 where i1_0.id != :id and i1_0.amie = :amie and i1_0.deleted = false ) then true else false end from Institution"
	)
	public Boolean existsByName(@Param("id") Long id, @Param("amie") String amie);
}
