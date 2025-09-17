package org.hmcampoverde.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.response.MessageHandler;
import org.hmcampoverde.response.MessageResponse;
import org.hmcampoverde.response.PaginatedRequest;
import org.hmcampoverde.response.PaginatedResponse;
import org.hmcampoverde.service.InstitutionService;
import org.springframework.data.domain.Page;
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
@RequestMapping("/institution")
@RequiredArgsConstructor
@Validated
public class InstitutionController {

	private final InstitutionService institutionService;

	private final MessageHandler messageHandler;

	@GetMapping("/findAll")
	public ResponseEntity<List<InstitutionDto>> findAll() {
		return ResponseEntity.ok(institutionService.findAll());
	}

	@PostMapping("/findAllWithFilter")
	public ResponseEntity<PaginatedResponse<InstitutionDto>> findAllWithFilter(@RequestBody PaginatedRequest request) {
		Page<InstitutionDto> page = institutionService.findAllWithFilter(request);

		PaginatedResponse<InstitutionDto> response = PaginatedResponse.<InstitutionDto>builder()
			.currentPage(page.getNumber())
			.totalPages(page.getTotalPages())
			.totalItems(page.getTotalElements())
			.pageSize(page.getSize())
			.hasNext(page.hasNext())
			.hasPrevious(page.hasPrevious())
			.data(page.getContent())
			.build();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<InstitutionDto> findById(@PathVariable("id") @Min(1) Long id) {
		return ResponseEntity.ok(institutionService.findById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<MessageResponse<InstitutionDto>> create(@Valid @RequestBody InstitutionDto institutionDto) {
		institutionDto = institutionService.create(institutionDto);
		return ResponseEntity.ok(messageHandler.buildCreationMessage("institution.create", institutionDto));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<MessageResponse<?>> update(
		@PathVariable @Min(1) Long id,
		@Valid @RequestBody InstitutionDto institutionDto
	) {
		institutionDto = institutionService.update(id, institutionDto);
		return ResponseEntity.ok(messageHandler.buildUpdateMessage("institution.update", institutionDto));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<MessageResponse<Void>> delete(@PathVariable("id") Long id) {
		institutionService.delete(id);
		return ResponseEntity.ok().body(messageHandler.buildDeleteMessage("institution.deleted", null));
	}
}
