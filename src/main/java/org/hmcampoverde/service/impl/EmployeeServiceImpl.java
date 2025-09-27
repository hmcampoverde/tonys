package org.hmcampoverde.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.mapper.EmployeeMapper;
import org.hmcampoverde.model.Employee;
import org.hmcampoverde.model.User;
import org.hmcampoverde.repository.EmployeeRepository;
import org.hmcampoverde.response.MessageHandler;
import org.hmcampoverde.response.PaginatedRequest;
import org.hmcampoverde.service.EmployeeService;
import org.hmcampoverde.service.UserService;
import org.hmcampoverde.specification.CustomSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeMapper mapper;
	private final MessageHandler messageHandler;
	private final EmployeeRepository repository;
	private final UserService userService;

	@Override
	public List<EmployeeDto> findAll() {
		return repository.findAll().stream().map(mapper::map).toList();
	}

	@Override
	public Page<EmployeeDto> findAllWithFilter(PaginatedRequest request) {
		Sort sort = Sort.by("lastname").ascending();
		if (request.getSort().getField() != null) {
			sort = request.getSort().getDirection().equalsIgnoreCase("desc")
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
			.orElseThrow(() ->
				new CustomException(messageHandler.getDetail("employee.id.notfound", id), HttpStatus.NOT_FOUND)
			);
	}

	@Override
	public EmployeeDto create(EmployeeDto employeeDto) {
		Boolean exists;
		String identification = employeeDto.getIdentification();

		if (!employeeDto.getInstitution().isActived()) {
			throw new CustomException(
				messageHandler.getDetail("institution.inactive", employeeDto.getInstitution().getName()),
				HttpStatus.BAD_REQUEST
			);
		}

		exists = repository.existsByIdentification(0L, identification);
		if (exists != null && exists) {
			throw new CustomException(messageHandler.getDetail("employee.identification.duplicate"), HttpStatus.BAD_REQUEST);
		}

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
		Boolean exists;

		if (!employeeDto.getInstitution().isActived()) {
			throw new CustomException(
				messageHandler.getDetail("institution.inactive", employeeDto.getInstitution().getName()),
				HttpStatus.BAD_REQUEST
			);
		}

		exists = repository.existsByIdentification(id, employeeDto.getIdentification());
		if (exists != null && exists) {
			throw new CustomException(messageHandler.getDetail("employee.identification.duplicate"), HttpStatus.BAD_REQUEST);
		}

		return repository
			.findById(id)
			.map(employee -> mapper.map(employeeDto, employee))
			.map(repository::save)
			.map(mapper::map)
			.orElseThrow();
	}

	@Override
	public void delete(Long id) {
		repository
			.findById(id)
			.ifPresentOrElse(
				employee -> employee.setDeleted(Boolean.TRUE),
				() -> {
					throw new CustomException("", HttpStatus.BAD_REQUEST);
				}
			);
	}
}
