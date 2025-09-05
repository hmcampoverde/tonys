package org.hmcampoverde.repository;

import java.util.Optional;
import org.hmcampoverde.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u left join fetch u.role where u.username = :username and u.deleted = false")
	public Optional<User> findByUsername(@Param("username") String username);
}
