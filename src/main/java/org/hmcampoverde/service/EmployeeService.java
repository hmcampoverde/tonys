package org.hmcampoverde.service;

import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.dto.request.PaginatedRequest;
import org.hmcampoverde.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService extends Service<Employee, EmployeeDto> {
	public Page<EmployeeDto> findAllWithFilter(PaginatedRequest request);
}
