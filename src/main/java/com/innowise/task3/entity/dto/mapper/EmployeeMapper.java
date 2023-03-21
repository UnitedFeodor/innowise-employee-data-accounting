package com.innowise.task3.entity.dto.mapper;

import com.innowise.task3.entity.Company;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.entity.Role;
import com.innowise.task3.entity.dto.AddEmployeeDTO;
import com.innowise.task3.entity.dto.EditEmployeeDTO;
import com.innowise.task3.entity.dto.EmployeeDTO;
import com.innowise.task3.entity.dto.LoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    String COMPANY_TO_ID = "companyToId";
    String ROLE_TO_ID = "roleToId";
    String ID_TO_ROLE = "idToRole";
    String ID_TO_COMPANY = "idToCompany";

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "employee.company", target = "company",qualifiedByName = COMPANY_TO_ID)
    @Mapping(source = "employee.role", target = "role",qualifiedByName = ROLE_TO_ID)
    EmployeeDTO employeeToEmployeeDTO(Employee employee);


    @Mapping(source = "employeeDTO.company", target = "company",qualifiedByName = ID_TO_COMPANY)
    @Mapping(source = "employeeDTO.role", target = "role",qualifiedByName = ID_TO_ROLE)
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    @Mapping(source = "employee.company", target = "company",qualifiedByName = COMPANY_TO_ID)
    @Mapping(source = "employee.role", target = "role",qualifiedByName = ROLE_TO_ID)
    List<EmployeeDTO> employeeListToEmployeeDTOList(List<Employee> employee);

    @Mapping(source = "addEmployeeDTO.company", target = "company",qualifiedByName = ID_TO_COMPANY)
    @Mapping(source = "addEmployeeDTO.role", target = "role",qualifiedByName = ID_TO_ROLE)
    Employee addEmployeeDTOToEmployee(AddEmployeeDTO addEmployeeDTO);

    @Mapping(source = "editEmployeeDTO.company", target = "company",qualifiedByName = ID_TO_COMPANY)
    @Mapping(source = "editEmployeeDTO.role", target = "role",qualifiedByName = ID_TO_ROLE)
    Employee editEmployeeDTOToEmployee(EditEmployeeDTO editEmployeeDTO);

    Employee loginDTOToEmployee(LoginDTO editEmployeeDTO);

    @Named(COMPANY_TO_ID)
    static Integer companyToId(Company company) {
        return company != null ? company.getId() : null;
    }

    @Named(ROLE_TO_ID)
    static Integer roleToId(Role role) {
        return role != null ? role.getId() : null;
    }

    @Named(ID_TO_COMPANY)
    static Company idToCompany(Integer companyId) {
        if (companyId != null) {

            Company company = new Company();
            company.setId(companyId);
            return company;
        } else {
            return null;
        }

    }

    @Named(ID_TO_ROLE)
    static Role idToRole(Integer roleId) {
        if (roleId != null) {
            Role role = new Role();
            role.setId(roleId);
            return role;
        } else {
            return null;
        }
    }



}
