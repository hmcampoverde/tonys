package org.hmcampoverde.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.dto.request.PaginatedRequest;
import org.hmcampoverde.entity.Employee;
import org.hmcampoverde.entity.User;
import org.hmcampoverde.exception.ResourceNotFoundException;
import org.hmcampoverde.mapper.EmployeeMapper;
import org.hmcampoverde.message.MessageResolver;
import org.hmcampoverde.repository.EmployeeRepository;
import org.hmcampoverde.service.EmployeeService;
import org.hmcampoverde.service.UserService;
import org.hmcampoverde.specification.CustomSpecification;
import org.hmcampoverde.validation.EmployeeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeMapper mapper;

	private final EmployeeValidator employeeValidator;

	private final EmployeeRepository repository;

	private final UserService userService;

	private final MessageResolver messageResolver;

	@Override
	public List<EmployeeDto> findAll() {
		return repository.findAll().stream().map(mapper::map).toList();
	}

	@Override
	public Page<EmployeeDto> findAllWithFilter(PaginatedRequest request) {
		Sort sort = Sort.by("lastname").ascending();

		if (request.getSort().getField() != null) {
			sort = request.getSort().getOrder().equalsIgnoreCase("desc")
				? Sort.by(request.getSort().getField()).descending()
				: Sort.by(request.getSort().getField()).ascending();
		}

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

		CustomSpecification<Employee> spec = new CustomSpecification<Employee>(
			request.getFilters(),
			List.of("institution")
		);

		Page<Employee> page = repository.findAll(spec, pageable);

		return page.map(mapper::map);
	}

	@Override
	public EmployeeDto findById(Long id) {
		return repository
			.findById(id)
			.map(mapper::map)
			.orElseThrow(() -> new ResourceNotFoundException(messageResolver.resolve("employee.id.notfound", id)));
	}

	@Override
	public EmployeeDto create(EmployeeDto employeeDto) {
		employeeValidator.validate(employeeDto);

		String identification = employeeDto.getIdentification();
		User user = userService.buildUser(identification, identification);
		Employee employee = Employee.builder().user(user).build();

		return Optional.of(employeeDto)
			.map(e -> mapper.map(employeeDto, employee))
			.map(repository::save)
			.map(mapper::map)
			.orElseThrow();
	}

	@Override
	public EmployeeDto update(Long id, EmployeeDto employeeDto) {
		employeeValidator.validate(employeeDto);

		return repository
			.findById(id)
			.map(employee -> mapper.map(employeeDto, employee))
			.map(repository::save)
			.map(mapper::map)
			.orElseThrow(() -> new ResourceNotFoundException(messageResolver.resolve("employee.id.notfound", id)));
	}

	@Override
	public void delete(Long id) {
		repository
			.findById(id)
			.map(employee -> {
				employee.setDeleted(Boolean.TRUE);
				return employee;
			})
			.orElseThrow(() -> new ResourceNotFoundException(messageResolver.resolve("employee.id.notfound", id)));
	}
}
