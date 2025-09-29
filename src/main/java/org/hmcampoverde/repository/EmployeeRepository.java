package org.hmcampoverde.repository;

import java.util.List;
import java.util.Optional;
import org.hmcampoverde.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
	@Query("select e from Employee e where e.deleted = false order by e.lastname")
	public @NonNull List<Employee> findAll();

	@Query(
		"select case when exists (select 1 from Employee e1_0 where e1_0.id != :id and e1_0.identification = :identification and e1_0.deleted = false ) then true else false end from Employee"
	)
	public Boolean existsByIdentification(@Param("id") Long id, @Param("identification") String identification);

	@Query("select e from Employee e left join fetch e.institution where e.id = :id and e.deleted = false")
	public @NonNull Optional<Employee> findById(@NonNull Long id);
}
