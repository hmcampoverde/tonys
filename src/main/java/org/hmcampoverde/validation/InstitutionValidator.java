package org.hmcampoverde.validation;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.exception.ValidationException;
import org.hmcampoverde.message.MessageResolver;
import org.hmcampoverde.repository.InstitutionRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InstitutionValidator implements Validator<InstitutionDto> {

	private final InstitutionRepository institutionRepository;

	private final MessageResolver messageResolver;

	@Override
	public void validate(InstitutionDto institutionDto) throws ValidationException {
		Boolean exists = institutionRepository.existsByAmie(institutionDto.getId(), institutionDto.getAmie());

		if (exists != null && exists) {
			throw new ValidationException(messageResolver.resolve("institution.amie.exists"));
		}
	}
}
