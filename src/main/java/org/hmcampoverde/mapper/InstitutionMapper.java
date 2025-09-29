package org.hmcampoverde.mapper;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.entity.Institution;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InstitutionMapper {

	public Institution map(InstitutionDto institutionDto) {
		return map(institutionDto, Institution.builder().build());
	}

	public Institution map(InstitutionDto institutionDto, Institution institution) {
		institution.setName(institutionDto.getName());
		institution.setAmie(institutionDto.getAmie());
		institution.setAddress(institutionDto.getAddress());
		institution.setPhone(institutionDto.getPhone());
		institution.setEmail(institutionDto.getEmail());
		institution.setActived(institutionDto.isActived());
		return institution;
	}

	public InstitutionDto map(Institution institution) {
		return InstitutionDto.builder()
			.id(institution.getId())
			.name(institution.getName())
			.amie(institution.getAmie())
			.address(institution.getAddress())
			.phone(institution.getPhone())
			.email(institution.getEmail())
			.actived(institution.isActived())
			.build();
	}
}
