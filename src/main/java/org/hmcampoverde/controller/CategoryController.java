package org.hmcampoverde.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.CategoryDto;
import org.hmcampoverde.dto.response.MessageResponse;
import org.hmcampoverde.message.MessageHandler;
import org.hmcampoverde.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {

	private final CategoryService categoryService;

	private final MessageHandler messageHandler;

	@GetMapping("/findAll")
	public ResponseEntity<List<CategoryDto>> findAll() {
		return ResponseEntity.ok(categoryService.findAll());
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable("id") @Min(1) Long id) {
		return ResponseEntity.ok(categoryService.findById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<MessageResponse<CategoryDto>> create(@Valid @RequestBody CategoryDto categoryDto) {
		categoryDto = categoryService.create(categoryDto);

		return ResponseEntity.ok(messageHandler.generateCreationMessage("category.create", categoryDto));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<MessageResponse<?>> update(
		@PathVariable @Min(1) Long id,
		@Valid @RequestBody CategoryDto categoryDto
	) {
		categoryDto = categoryService.update(id, categoryDto);

		return ResponseEntity.ok(messageHandler.generateUpdateMessage("category.update", categoryDto));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<MessageResponse<Void>> delete(@PathVariable("id") Long id) {
		categoryService.delete(id);

		return ResponseEntity.ok().body(messageHandler.generateDeleteMessage("category.deleted", null));
	}
}
