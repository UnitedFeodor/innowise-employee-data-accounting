package com.innowise.task3.dao;

import com.innowise.task3.dto.*;
import com.innowise.task3.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmployees();

    Employee getEmployeeWithId(int id);

    void editEmployee(Employee employee);

    void addEmployee(Employee employee);

    void deleteEmployee(int id);

    boolean login(String login, String password);
    boolean register (Employee employee);
}
