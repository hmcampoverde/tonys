package org.hmcampoverde.mapper;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.entity.Employee;
import org.hmcampoverde.repository.InstitutionRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmployeeMapper {

	private final InstitutionRepository institutionRepository;

	private final InstitutionMapper institutionMapper;

	public Employee map(EmployeeDto employeeDto) {
		return map(employeeDto, Employee.builder().build());
	}

	public Employee map(EmployeeDto employeeDto, Employee employee) {
		employee.setIdentification(employeeDto.getIdentification());
		employee.setFirstname(employeeDto.getFirstname());
		employee.setLastname(employeeDto.getLastname());
		employee.setEmailInstitutional(employeeDto.getEmailInstitutional());
		employee.setEmailPersonal(employeeDto.getEmailPersonal());
		employee.setPhone(employeeDto.getPhone());
		employee.setMobile(employeeDto.getMobile());
		employee.setAddress(employeeDto.getAddress());
		employee.setActived(employeeDto.isActived());
		employee.setInstitution(institutionRepository.getReferenceById(employeeDto.getInstitution().getId()));

		return employee;
	}

	public EmployeeDto map(Employee employee) {
		return EmployeeDto.builder()
			.id(employee.getId())
			.identification(employee.getIdentification())
			.firstname(employee.getFirstname())
			.lastname(employee.getLastname())
			.emailInstitutional(employee.getEmailInstitutional())
			.emailPersonal(employee.getEmailPersonal())
			.phone(employee.getPhone())
			.mobile(employee.getMobile())
			.address(employee.getAddress())
			.actived(employee.isActived())
			.institution(institutionMapper.map(employee.getInstitution()))
			.build();
	}
}
