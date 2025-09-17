package org.hmcampoverde.service;

import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.response.PaginatedRequest;
import org.springframework.data.domain.Page;

public interface InstitutionService extends Service<InstitutionDto> {
	public Page<InstitutionDto> findAllWithFilter(PaginatedRequest request);
}
