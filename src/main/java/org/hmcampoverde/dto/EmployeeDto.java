package org.hmcampoverde.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

	private Long id;

	@Size(max = 75, message = "{employee.firstname.size}")
	@NotBlank(message = "{employee.firstname.required}")
	private String firstname;

	@Size(max = 75, message = "{employee.lastname.size}")
	@NotBlank(message = "{employee.lastname.required}")
	private String lastname;

	@Size(max = 10, message = "{employee.identification.size}")
	@NotBlank(message = "{employee.identification.required}")
	private String identification;

	@Size(max = 75, message = "{employee.emailPersonal.size}")
	@NotBlank(message = "{employee.emailPersonal.required}")
	@Email(message = "{employee.emailPersonal.invalid}")
	private String emailPersonal;

	@Size(max = 75, message = "{employee.emailInstitutional.size}")
	@NotBlank(message = "{employee.emailInstitutional.required}")
	@Email(message = "{employee.emailInstitutional.invalid}")
	private String emailInstitutional;

	@Size(min = 16, max = 16, message = "{employee.phone.size}")
	@NotBlank(message = "{employee.phone.required}")
	private String phone;

	@Size(min = 17, max = 17, message = "{employee.mobile.size}")
	@NotBlank(message = "{employee.mobile.required}")
	private String mobile;

	@NotBlank(message = "{employee.address.required}")
	private String address;

	@NotNull(message = "{employee.institution.required}")
	private InstitutionDto institution;

	@NotNull(message = "{institution.active.required}")
	private boolean actived;

	public String getFullname() {
		return firstname + " " + lastname;
	}
}
