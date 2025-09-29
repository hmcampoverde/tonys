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
@Table(name = "tbl_institution")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Institution extends EntityBase {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "institution_id", unique = true, nullable = false, length = 4)
	private Long id;

	@Column(name = "institution_name", nullable = false, length = 75)
	private String name;

	@Column(name = "institution_amie", nullable = false, length = 8)
	private String amie;

	@Column(name = "institution_email", nullable = false, length = 75)
	private String email;

	@Column(name = "institution_phone", nullable = false, length = 17)
	private String phone;

	@Column(name = "institution_addres", nullable = false, columnDefinition = "TEXT")
	private String address;
}
