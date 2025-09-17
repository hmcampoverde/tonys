package org.hmcampoverde.mapper;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.model.Institution;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InstitutionMapper {

	private final ModelMapper modelMapper;

	public Institution map(InstitutionDto institutionDto) {
		return modelMapper.map(institutionDto, Institution.class);
	}

	public Institution map(InstitutionDto institutionDto, Institution institution) {
		modelMapper
			.typeMap(InstitutionDto.class, Institution.class)
			.addMappings(mapper -> mapper.skip(Institution::setId))
			.map(institutionDto, institution);

		return institution;
	}

	public InstitutionDto map(Institution institution) {
		return modelMapper.map(institution, InstitutionDto.class);
	}
}
