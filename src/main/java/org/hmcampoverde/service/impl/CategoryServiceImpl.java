package org.hmcampoverde.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.CategoryDto;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.mapper.CategoryMapper;
import org.hmcampoverde.repository.CategoryRepository;
import org.hmcampoverde.response.MessageHandler;
import org.hmcampoverde.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final MessageHandler messageHandler;

	private final CategoryRepository repository;

	private final CategoryMapper mapper;

	@Override
	public List<CategoryDto> findAll() {
		return repository.findAll().stream().map(mapper::map).toList();
	}

	@Override
	public CategoryDto findById(Long id) {
		return repository
			.findById(id)
			.map(mapper::map)
			.orElseThrow(() ->
				new CustomException(messageHandler.getDetail("category.id.notfound", id), HttpStatus.NOT_FOUND)
			);
	}

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		Boolean exists = repository.existsByName(0L, categoryDto.getName());
		if (exists != null && exists) {
			throw new CustomException(messageHandler.getDetail("category.name.duplicate"), HttpStatus.BAD_REQUEST);
		}

		return Optional.of(categoryDto).map(mapper::map).map(repository::save).map(mapper::map).orElseThrow();
	}

	@Override
	public CategoryDto update(Long id, CategoryDto categoryDto) {
		if (repository.existsByName(id, categoryDto.getName())) {
			throw new CustomException(messageHandler.getDetail("category.name.duplicate"), HttpStatus.BAD_REQUEST);
		}

		return repository
			.findById(id)
			.map(category -> mapper.map(categoryDto, category))
			.map(repository::save)
			.map(mapper::map)
			.orElseThrow();
	}

	@Override
	public void delete(Long id) {
		repository
			.findById(id)
			.ifPresentOrElse(
				category -> category.setDeleted(Boolean.TRUE),
				() -> {
					throw new CustomException("", HttpStatus.BAD_REQUEST);
				}
			);
	}
}
