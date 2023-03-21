package com.innowise.task3.dao;

import com.innowise.task3.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmployees() throws DAOException;

    Employee getEmployeeWithId(int id) throws DAOException;

    Employee editEmployee(Employee employee) throws DAOException;

    Employee addEmployee(Employee employee) throws DAOException;

    void deleteEmployee(int id) throws DAOException;

    Employee login(String email, String password) throws DAOException;
}
