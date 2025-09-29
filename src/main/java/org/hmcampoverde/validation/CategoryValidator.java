package org.hmcampoverde.validation;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.CategoryDto;
import org.hmcampoverde.exception.ResourceNotFoundException;
import org.hmcampoverde.exception.ValidationException;
import org.hmcampoverde.message.MessageResolver;
import org.hmcampoverde.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryValidator implements Validator<CategoryDto> {

	private final CategoryRepository categoryRepository;

	private final MessageResolver messageResolver;

	@Override
	public void validate(CategoryDto categoryDto) throws ValidationException {
		Boolean exists = categoryRepository.existsByName(categoryDto.getId(), categoryDto.getName());

		if (exists != null && exists) {
			throw new ResourceNotFoundException(messageResolver.resolve("category.name.exists"));
		}
	}
}
