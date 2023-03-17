package com.innowise.task3.service.implementation;

import com.innowise.task3.dao.DAOProvider;
import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.dto.*;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.mapper.EmployeeMapper;
import com.innowise.task3.service.EmployeeService;

import java.util.List;

public class EmployeeServiceImplementation implements EmployeeService {

    EmployeeDAO employeeDAO = DAOProvider.getInstance().getEmployeeDAO();
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return EmployeeMapper.INSTANCE.employeeListToEmployeeDTOList(employeeDAO.getAllEmployees());
    }

    @Override
    public EmployeeDTO getEmployeeWithId(int id) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeDTO(employeeDAO.getEmployeeWithId(id));
    }

    @Override
    public void editEmployee(AddEditEmployeeDTO addEditEmployeeDTO) {

    }

    @Override
    public void addEmployee(AddEditEmployeeDTO addEditEmployeeDTO) {

    }

    @Override
    public void deleteEmployee(int id) {

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
