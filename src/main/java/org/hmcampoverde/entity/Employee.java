package org.hmcampoverde.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_employee")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Employee extends EntityBase {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", unique = true, nullable = false, length = 4)
	private Long id;

	@Column(name = "employee_firstname", nullable = false, length = 75)
	private String firstname;

	@Column(name = "employee_lastname", nullable = false, length = 75)
	private String lastname;

	@Column(name = "employee_identification", unique = true, nullable = false, length = 10)
	private String identification;

	@Column(name = "employee_email_personal", nullable = false, length = 75)
	private String emailPersonal;

	@Column(name = "employee_email_institutional", nullable = false, length = 75)
	private String emailInstitutional;

	@Column(name = "employee_phone", nullable = false, length = 17)
	private String phone;

	@Column(name = "employee_mobile", nullable = false, length = 18)
	private String mobile;

	@Column(name = "employee_address", nullable = false, columnDefinition = "TEXT")
	private String address;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PERSON_USER"))
	private User user;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "institution_id", foreignKey = @ForeignKey(name = "FK_EMPLOYEE_INSTITUTION"))
	private Institution institution;
}
