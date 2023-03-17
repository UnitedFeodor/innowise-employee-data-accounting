package com.innowise.task3.service.implementation;

import com.innowise.task3.dao.DAOProvider;
import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.dto.*;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.mapper.EmployeeMapper;
import com.innowise.task3.service.EmployeeService;

import java.util.List;

public class EmployeeServiceImplementation implements EmployeeService {
    // TODO add exceptions and validation

    EmployeeDAO employeeDAO = DAOProvider.getInstance().getEmployeeDAO();

    EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.employeeListToEmployeeDTOList(employeeDAO.getAllEmployees());
    }

    @Override
    public EmployeeDTO getEmployeeWithId(int id) {
        return employeeMapper.employeeToEmployeeDTO(employeeDAO.getEmployeeWithId(id));
    }

    @Override
    public EmployeeDTO editEmployee(AddEditEmployeeDTO addEditEmployeeDTO) {
        return null;
    }

    @Override
    public EmployeeDTO addEmployee(AddEditEmployeeDTO addEditEmployeeDTO) {
        Employee employee = employeeDAO.addEmployee(employeeMapper.addEditEmployeeDTOToEmployee(addEditEmployeeDTO));
        return employeeMapper.employeeToEmployeeDTO(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeDAO.deleteEmployee(id);
    }

    @Override
    public boolean login(LoginDTO loginDTO) {
        return false;
    }

    @Override
    public boolean register(RegisterDTO registerDTO) {
        return false;
    }
}
