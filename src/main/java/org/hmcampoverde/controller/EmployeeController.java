package org.hmcampoverde.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.response.MessageHandler;
import org.hmcampoverde.response.MessageResponse;
import org.hmcampoverde.response.PaginatedRequest;
import org.hmcampoverde.response.PaginatedResponse;
import org.hmcampoverde.service.EmployeeService;
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
@RequestMapping("/employee")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

	private final EmployeeService employeeService;

	private final MessageHandler messageHandler;

	@GetMapping("/findAll")
	public ResponseEntity<List<EmployeeDto>> findAll() {
		return ResponseEntity.ok(employeeService.findAll());
	}

	@PostMapping("/findAllWithFilter")
	public ResponseEntity<PaginatedResponse<EmployeeDto>> findAllWithFilter(@RequestBody PaginatedRequest request) {
		Page<EmployeeDto> page = employeeService.findAllWithFilter(request);

		PaginatedResponse<EmployeeDto> response = PaginatedResponse.<EmployeeDto>builder()
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
	public ResponseEntity<EmployeeDto> findById(@PathVariable("id") @Min(1) Long id) {
		return ResponseEntity.ok(employeeService.findById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<MessageResponse<EmployeeDto>> create(@Valid @RequestBody EmployeeDto employeeDto) {
		employeeDto = employeeService.create(employeeDto);
		return ResponseEntity.ok(messageHandler.buildCreationMessage("employee.create", employeeDto));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<MessageResponse<?>> update(
		@PathVariable @Min(1) Long id,
		@Valid @RequestBody EmployeeDto employeeDto
	) {
		employeeDto = employeeService.update(id, employeeDto);
		return ResponseEntity.ok(messageHandler.buildUpdateMessage("employee.update", employeeDto));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<MessageResponse<Void>> delete(@PathVariable("id") Long id) {
		employeeService.delete(id);
		return ResponseEntity.ok().body(messageHandler.buildDeleteMessage("employee.deleted", null));
	}
}
