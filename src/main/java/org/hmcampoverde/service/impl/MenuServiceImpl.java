package org.hmcampoverde.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.MenuDto;
import org.hmcampoverde.repository.MenuRepository;
import org.hmcampoverde.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final ModelMapper modelMapper;

	@Override
	@Transactional(readOnly = true)
	public List<MenuDto> findByRole(String role) {
		return menuRepository
			.findByRole(role.substring(5).toLowerCase())
			.stream()
			.map(menu -> modelMapper.map(menu, MenuDto.class))
			.toList();
	}
}
