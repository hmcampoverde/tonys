package org.hmcampoverde.mapper;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.MenuDto;
import org.hmcampoverde.entity.Menu;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuMapper {

	private final ModelMapper modelMapper;

	public MenuDto map(Menu menu) {
		return modelMapper.map(menu, MenuDto.class);
	}
}
