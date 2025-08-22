package org.hmcampoverde.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

	private Long id;
	private String name;
	private String url;
	private String icon;
	private MenuDto parent;
}
