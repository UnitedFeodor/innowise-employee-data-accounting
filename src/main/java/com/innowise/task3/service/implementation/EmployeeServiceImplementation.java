package com.innowise.task3.service.implementation;

import com.innowise.task3.dao.DAOProvider;
import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.dto.*;
import com.innowise.task3.service.EmployeeService;

import java.util.List;

public class EmployeeServiceImplementation implements EmployeeService {

    EmployeeDAO employeeDAO = DAOProvider.getInstance().getEmployeeDAO();
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return null;
    }

    @Override
    public EmployeeDTO getEmployeeWithId(int id) {
        return null;
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
