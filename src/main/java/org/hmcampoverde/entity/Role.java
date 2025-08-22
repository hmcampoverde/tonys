package org.hmcampoverde.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_role")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Role extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false, length = 2)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "role_code", unique = true, nullable = false, length = 10)
	private String code;

	@Column(name = "role_name", unique = true, nullable = false, length = 75)
	private String name;

	@Column(name = "role_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT 'TRUE'")
	private boolean active;
}
