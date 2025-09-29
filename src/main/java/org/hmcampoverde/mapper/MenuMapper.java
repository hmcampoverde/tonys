package org.hmcampoverde.mapper;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.MenuDto;
import org.hmcampoverde.entity.Menu;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuMapper {

	public MenuDto map(Menu menu) {
		return MenuDto.builder()
			.id(menu.getId())
			.name(menu.getName())
			.url(menu.getUrl())
			.icon(menu.getIcon())
			.idParent(menu.getParent() != null ? menu.getParent().getId() : null)
			.build();
	}
}
