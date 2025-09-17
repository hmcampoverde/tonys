package org.hmcampoverde.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDto {

	private Long id;

	@Size(min = 30, max = 75, message = "{institution.name.size}")
	@NotBlank(message = "{institution.name.required}")
	private String name;

	@Size(min = 5, max = 8, message = "{institution.amie.size}")
	@NotBlank(message = "{institution.amie.required}")
	private String amie;

	@Size(min = 15, max = 75, message = "{institution.email.size}")
	@NotBlank(message = "{institution.email.required}")
	@Email(message = "{institution.email.invalid}")
	private String email;

	@Size(min = 17, max = 17, message = "{institution.phone.size}")
	@NotBlank(message = "{institution.phone.required}")
	private String phone;

	@NotBlank(message = "{institution.address.required}")
	private String address;

	@NotNull(message = "{institution.active.required}")
	private boolean actived;
}
