package org.hmcampoverde.mapper;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.CategoryDto;
import org.hmcampoverde.entity.Category;
import org.hmcampoverde.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {

	private final CategoryRepository categoryRepository;

	public Category map(CategoryDto categoryDto) {
		return map(categoryDto, Category.builder().build());
	}

	public Category map(CategoryDto categoryDto, Category category) {
		category.setName(categoryDto.getName());
		category.setIcon(categoryDto.getIcon());
		category.setVisible(categoryDto.isVisible());
		category.setActived(categoryDto.isActived());

		if (categoryDto.getIdParent() != null && categoryDto.getIdParent() != 0) {
			category.setParent(categoryRepository.getReferenceById(categoryDto.getIdParent()));
		} else {
			category.setParent(null);
		}

		return category;
	}

	public CategoryDto map(Category category) {
		return CategoryDto.builder()
			.id(category.getId())
			.name(category.getName())
			.icon(category.getIcon())
			.visible(category.isVisible())
			.actived(category.isActived())
			.idParent(category.getParent() != null ? category.getParent().getId() : null)
			.build();
	}
}
