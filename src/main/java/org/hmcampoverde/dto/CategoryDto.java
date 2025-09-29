package org.hmcampoverde.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryDto {

	private Long id;

	@Size(max = 75, message = "{category.name.size}")
	@NotBlank(message = "{category.name.required}")
	private String name;

	@Size(max = 75, message = "{category.icon.size}")
	@NotBlank(message = "{category.icon.required}")
	private String icon;

	private boolean actived;

	private boolean visible;

	private Long idParent;
}
