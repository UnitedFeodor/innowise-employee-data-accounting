package com.innowise.task3.service;

import com.innowise.task3.dto.*;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeWithId(int id);

    void editEmployee(EditEmployeeDTO editEmployeeDTO);

    void addEmployee(AddEmployeeDTO addEmployeeDTO);

    void deleteEmployee(int id);

    boolean login(LoginDTO loginDTO);
    boolean register (RegisterDTO registerDTO);

}
