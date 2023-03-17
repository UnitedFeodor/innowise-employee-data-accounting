package com.innowise.task3.dao;

import com.innowise.task3.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmployees();

    Employee getEmployeeWithId(int id);

    Employee editEmployee(Employee employee);

    Employee addEmployee(Employee employee);

    void deleteEmployee(int id);

    boolean signIn(String email, String password);
    boolean register (Employee employee);
}
