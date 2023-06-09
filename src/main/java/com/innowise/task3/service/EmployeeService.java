package com.innowise.task3.service;

import com.innowise.task3.entity.dto.AddEmployeeDTO;
import com.innowise.task3.entity.dto.EditEmployeeDTO;
import com.innowise.task3.entity.dto.EmployeeDTO;
import com.innowise.task3.entity.dto.LoginDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees(int companyId) throws ServiceException;

    EmployeeDTO getEmployeeWithId(int id) throws ServiceException;

    EmployeeDTO editEmployee(EditEmployeeDTO editEmployeeDTO) throws ServiceException;

    EmployeeDTO addEmployee(AddEmployeeDTO addEmployeeDTO) throws ServiceException;

    void deleteEmployee(int id) throws ServiceException;

    EmployeeDTO login(LoginDTO loginDTO) throws ServiceException;

}
