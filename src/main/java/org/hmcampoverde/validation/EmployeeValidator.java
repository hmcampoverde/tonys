package org.hmcampoverde.validation;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.dto.InstitutionDto;
import org.hmcampoverde.exception.ValidationException;
import org.hmcampoverde.message.MessageResolver;
import org.hmcampoverde.repository.EmployeeRepository;
import org.hmcampoverde.service.InstitutionService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmployeeValidator implements Validator<EmployeeDto> {

	private final EmployeeRepository employeeRepository;

	private final InstitutionService institutionService;

	private final MessageResolver messageResolver;

	@Override
	public void validate(EmployeeDto employeeDto) throws ValidationException {
		Boolean exists = employeeRepository.existsByIdentification(employeeDto.getId(), employeeDto.getIdentification());
		if (exists != null && exists) {
			throw new ValidationException(messageResolver.resolve("employee.identification.exists"));
		}

		InstitutionDto institutionDto = institutionService.findById(employeeDto.getInstitution().getId());
		if (!institutionDto.isActived()) {
			throw new ValidationException(messageResolver.resolve("institution.inactive"));
		}
	}
}
