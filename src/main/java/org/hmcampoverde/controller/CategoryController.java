package org.hmcampoverde.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.CategoryDto;
import org.hmcampoverde.message.Message;
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

	@GetMapping("/findAll")
	public ResponseEntity<List<CategoryDto>> findAll() {
		return ResponseEntity.ok(categoryService.findAll());
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable("id") @Min(1) Long id) {
		return ResponseEntity.ok(categoryService.findById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<Message> create(@Valid @RequestBody CategoryDto categoryDto) {
		return ResponseEntity.ok(categoryService.create(categoryDto));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Message> update(
		@PathVariable("id") @Min(1) Long id,
		@Valid @RequestBody CategoryDto categoryDto
	) {
		return ResponseEntity.ok(this.categoryService.update(id, categoryDto));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Message> delete(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(this.categoryService.delete(id));
	}
}
