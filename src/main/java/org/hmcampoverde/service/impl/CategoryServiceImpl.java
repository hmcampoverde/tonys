package org.hmcampoverde.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.CategoryDto;
import org.hmcampoverde.exception.ResourceNotFoundException;
import org.hmcampoverde.mapper.CategoryMapper;
import org.hmcampoverde.message.MessageResolver;
import org.hmcampoverde.repository.CategoryRepository;
import org.hmcampoverde.service.CategoryService;
import org.hmcampoverde.validation.CategoryValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final MessageResolver messageResolver;

	private final CategoryRepository repository;

	private final CategoryValidator categoryValidator;

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
			.orElseThrow(() -> new ResourceNotFoundException(messageResolver.resolve("category.id.notfound", id)));
	}

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		categoryValidator.validate(categoryDto);

		return Optional.of(categoryDto).map(mapper::map).map(repository::save).map(mapper::map).orElseThrow();
	}

	@Override
	public CategoryDto update(Long id, CategoryDto categoryDto) {
		categoryValidator.validate(categoryDto);

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
			.map(category -> {
				category.setDeleted(Boolean.TRUE);
				return repository.save(category);
			})
			.orElseThrow(() -> new ResourceNotFoundException(messageResolver.resolve("category.id.notfound", id)));
	}
}
