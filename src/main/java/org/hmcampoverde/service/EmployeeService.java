package org.hmcampoverde.service;

import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.response.PaginatedRequest;
import org.springframework.data.domain.Page;

public interface EmployeeService extends Service<EmployeeDto> {
	public Page<EmployeeDto> findAllWithFilter(PaginatedRequest request);
}
