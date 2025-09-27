package org.hmcampoverde.mapper;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.CategoryDto;
import org.hmcampoverde.model.Category;
import org.hmcampoverde.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {

	private final ModelMapper modelMapper;

	private final CategoryRepository categoryRepository;

	public Category map(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);

		if (categoryDto.getIdParent() != null) {
			category.setParent(categoryRepository.getReferenceById(categoryDto.getIdParent()));
		}

		return category;
	}

	public CategoryDto map(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}

	public Category map(CategoryDto categoryDto, Category category) {
		modelMapper
			.typeMap(CategoryDto.class, Category.class)
			.addMappings(mapper -> mapper.skip(Category::setId))
			.map(categoryDto, category);

		if (categoryDto.getIdParent() != null && categoryDto.getIdParent() != 0) {
			category.setParent(categoryRepository.getReferenceById(categoryDto.getIdParent()));
		} else {
			category.setParent(null);
		}

		return category;
	}
}
