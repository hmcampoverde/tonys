package org.hmcampoverde.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.exception.ResourceNotFoundException;
import org.hmcampoverde.mapper.InstitutionMapper;
import org.hmcampoverde.message.MessageResolver;
import org.hmcampoverde.repository.InstitutionRepository;
import org.hmcampoverde.service.InstitutionService;
import org.hmcampoverde.validation.InstitutionValidator;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

	private final InstitutionRepository repository;

	private final InstitutionMapper mapper;

	private final InstitutionValidator institutionValidator;

	private final MessageResolver messageResolver;

	@Override
	public List<InstitutionDto> findAll() {
		return repository.findAll().stream().map(mapper::map).toList();
	}

	@Override
	public InstitutionDto findById(Long id) {
		return repository
			.findById(id)
			.map(mapper::map)
			.orElseThrow(() -> new ResourceNotFoundException(messageResolver.resolve("institution.id.notfound", id)));
	}

	@Override
	public InstitutionDto create(InstitutionDto institutionDto) {
		institutionValidator.validate(institutionDto);

		return Optional.of(institutionDto).map(mapper::map).map(repository::save).map(mapper::map).orElseThrow();
	}

	@Override
	public InstitutionDto update(Long id, InstitutionDto institutionDto) {
		institutionValidator.validate(institutionDto);

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
			.map(institution -> {
				institution.setDeleted(Boolean.TRUE);
				return repository.save(institution);
			})
			.orElseThrow(() -> new ResourceNotFoundException(messageResolver.resolve("institution.id.notfound", id)));
	}
}
