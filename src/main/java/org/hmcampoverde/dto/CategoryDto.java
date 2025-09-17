package org.hmcampoverde.dto;

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
public class CategoryDto {

	private Long id;

	@Size(min = 5, max = 75, message = "{category.name.size}")
	@NotBlank(message = "{category.name.required}")
	private String name;

	@Size(min = 2, max = 75, message = "{category.icon.size}")
	@NotBlank(message = "{category.icon.required}")
	private String icon;

	@NotNull(message = "{category.actived.required}")
	private boolean actived;

	@NotNull(message = "{category.visible.required}")
	private boolean visible;

	private Long idParent;
}
