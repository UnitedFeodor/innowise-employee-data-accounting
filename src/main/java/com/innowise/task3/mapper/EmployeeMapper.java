package com.innowise.task3.mapper;

import com.innowise.task3.dto.*;
import com.innowise.task3.entity.Company;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface EmployeeMapper {

    String COMPANY_TO_ID = "companyToId";
    String ROLE_TO_ID = "roleToId";
    String ID_TO_ROLE = "idToRole";
    String ID_TO_COMPANY = "idToCompany";

    @Mapping(source = "employee.company", target = "company",qualifiedByName = COMPANY_TO_ID)
    @Mapping(source = "employee.role", target = "role",qualifiedByName = ROLE_TO_ID)
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    @Mapping(source = "employeeDTO.company", target = "company",qualifiedByName = ID_TO_COMPANY)
    @Mapping(source = "employeeDTO.role", target = "role",qualifiedByName = ID_TO_ROLE)
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    @Mapping(source = "addEditEmployeeDTO.company", target = "company",qualifiedByName = ID_TO_COMPANY)
    @Mapping(source = "addEditEmployeeDTO.role", target = "role",qualifiedByName = ID_TO_ROLE)
    Employee addEmployeeDTOToEmployee(AddEditEmployeeDTO addEditEmployeeDTO);

    @Mapping(source = "addEditEmployeeDTO.company", target = "company",qualifiedByName = ID_TO_COMPANY)
    @Mapping(source = "addEditEmployeeDTO.role", target = "role",qualifiedByName = ID_TO_ROLE)
    Employee editEmployeeDTOToEmployee(AddEditEmployeeDTO addEditEmployeeDTO);

    Employee loginDTOToEmployee(LoginDTO editEmployeeDTO);

    @Named(COMPANY_TO_ID)
    static int companyToId(Company company) {
        return company.getId();
    }

    @Named(ROLE_TO_ID)
    static int roleToId(Role role) {
        return role.getId();
    }

    @Named(ID_TO_COMPANY)
    static Company idToCompany(int companyId) {
        Company company = new Company();
        company.setId(companyId);
        return company;
    }

    @Named(ID_TO_ROLE)
    static Role idToRole(int roleId) {
        Role role = new Role();
        role.setId(roleId);
        return role;
    }



}
