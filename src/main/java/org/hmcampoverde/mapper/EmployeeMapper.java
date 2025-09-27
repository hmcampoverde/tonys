package org.hmcampoverde.mapper;

import lombok.AllArgsConstructor;
import org.hmcampoverde.dto.EmployeeDto;
import org.hmcampoverde.model.Employee;
import org.hmcampoverde.repository.InstitutionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmployeeMapper {

	private final InstitutionRepository institutionRepository;

	private final ModelMapper modelMapper;

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
		return modelMapper.map(employee, EmployeeDto.class);
	}
}
