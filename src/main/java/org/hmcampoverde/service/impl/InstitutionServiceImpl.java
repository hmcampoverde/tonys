package org.hmcampoverde.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.exception.CustomException;
import org.hmcampoverde.mapper.InstitutionMapper;
import org.hmcampoverde.model.Institution;
import org.hmcampoverde.repository.InstitutionRepository;
import org.hmcampoverde.response.MessageHandler;
import org.hmcampoverde.response.PaginatedRequest;
import org.hmcampoverde.service.InstitutionService;
import org.hmcampoverde.specification.CustomSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

	private final InstitutionRepository repository;

	private final InstitutionMapper mapper;

	private final MessageHandler messageHandler;

	@Override
	public List<InstitutionDto> findAll() {
		return repository.findAll().stream().map(mapper::map).toList();
	}

	@Override
	public InstitutionDto findById(Long id) {
		return repository
			.findById(id)
			.map(mapper::map)
			.orElseThrow(() ->
				new CustomException(messageHandler.getDetail("institution.id.notfound", id), HttpStatus.NOT_FOUND)
			);
	}

	@Override
	public InstitutionDto create(InstitutionDto institutionDto) {
		Boolean exists = repository.existsByName(0L, institutionDto.getAmie());
		if (exists != null && exists) {
			throw new CustomException(messageHandler.getDetail("institution.amie.duplicate"), HttpStatus.BAD_REQUEST);
		}

		return Optional.of(institutionDto).map(mapper::map).map(repository::save).map(mapper::map).orElseThrow();
	}

	@Override
	public InstitutionDto update(Long id, InstitutionDto institutionDto) {
		if (repository.existsByName(id, institutionDto.getAmie())) {
			throw new CustomException(messageHandler.getDetail("institution.amie.duplicate"), HttpStatus.BAD_REQUEST);
		}

		return repository
			.findById(id)
			.map(institution -> mapper.map(institutionDto, institution))
			.map(repository::save)
			.map(mapper::map)
			.orElseThrow();
	}

	@Override
	public void delete(Long id) {
		repository
			.findById(id)
			.ifPresentOrElse(
				category -> category.setDeleted(Boolean.TRUE),
				() -> {
					throw new CustomException("", HttpStatus.BAD_REQUEST);
				}
			);
	}

	@Override
	public Page<InstitutionDto> findAllWithFilter(PaginatedRequest request) {
		Sort sort = Sort.by("amie").ascending();

		if (request.getSort().getField() != null) {
			sort = request.getSort().getDirection().equalsIgnoreCase("desc")
				? Sort.by(request.getSort().getField()).descending()
				: Sort.by(request.getSort().getField()).ascending();
		}

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
		CustomSpecification<Institution> spec = new CustomSpecification<Institution>(request.getFilters());

		return repository.findAll(spec, pageable).map(mapper::map);
	}
}
