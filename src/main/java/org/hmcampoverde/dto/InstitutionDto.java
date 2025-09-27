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
public class InstitutionDto {

	private Long id;

	@Size(max = 75, message = "{institution.name.size}")
	@NotBlank(message = "{institution.name.required}")
	private String name;

	@Size(max = 8, message = "{institution.amie.size}")
	@NotBlank(message = "{institution.amie.required}")
	private String amie;

	@Size(max = 75, message = "{institution.email.size}")
	@NotBlank(message = "{institution.email.required}")
	@Email(message = "{institution.email.invalid}")
	private String email;

	@Size(max = 17, message = "{institution.phone.size}")
	@NotBlank(message = "{institution.phone.required}")
	private String phone;

	@NotBlank(message = "{institution.address.required}")
	private String address;

	@NotNull(message = "{institution.active.required}")
	private boolean actived;
}
