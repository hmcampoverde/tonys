package org.hmcampoverde.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.CategoryDto;
import org.hmcampoverde.entity.Category;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.mapper.CategoryMapper;
import org.hmcampoverde.message.Message;
import org.hmcampoverde.message.MessageHandler;
import org.hmcampoverde.repository.CategoryRepository;
import org.hmcampoverde.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;

	private final CategoryMapper mapper;

	private final MessageHandler messageHandler;

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
				new CustomException(messageHandler.getDetail("category.id.notfound", new Object[] { id }), HttpStatus.NOT_FOUND)
			);
	}

	@Override
	public Message create(CategoryDto categoryDto) {
		Boolean exists = repository.existsByName(0L, categoryDto.getName());
		if (exists != null && exists) {
			throw new CustomException(messageHandler.getDetail("category.name.duplicate"), HttpStatus.BAD_REQUEST);
		}

		return Optional.of(categoryDto)
			.map(mapper::map)
			.map(repository::save)
			.map(Category::getId)
			.map(id ->
				Message.builder()
					.severity("info")
					.summary(messageHandler.getDetail("title.information"))
					.detail(messageHandler.getDetail("category.saved"))
					.data(Map.of("id", id))
					.build()
			)
			.orElseThrow();
	}

	@Override
	public Message update(Long id, CategoryDto categoryDto) {
		if (repository.existsByName(id, categoryDto.getName())) {
			throw new CustomException(messageHandler.getDetail("category.name.duplicate"), HttpStatus.BAD_REQUEST);
		}

		return repository
			.findById(id)
			.map(c -> mapper.map(categoryDto, c))
			.map(c ->
				Message.builder()
					.severity("info")
					.summary(messageHandler.getDetail("title.information"))
					.detail(messageHandler.getDetail("category.saved"))
					.build()
			)
			.orElseThrow(() ->
				new CustomException(messageHandler.getDetail("category.id.notfound", id), HttpStatus.BAD_REQUEST)
			);
	}

	@Override
	public Message delete(Long id) {
		return repository
			.findById(id)
			.map(c -> {
				c.setDeleted(Boolean.TRUE);
				return Message.builder()
					.severity("error")
					.summary(messageHandler.getDetail("title.information"))
					.detail(messageHandler.getDetail("category.deleted"))
					.build();
			})
			.orElseThrow(() ->
				new CustomException(messageHandler.getDetail("category.id.notfound", id), HttpStatus.BAD_REQUEST)
			);
	}
}
