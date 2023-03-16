package com.innowise.task3.dao.implementation;

import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.entity.Employee;

import java.util.List;

public class EmployeeDAOImplementation implements EmployeeDAO {
    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public Employee getEmployeeWithId(int id) {
        return null;
    }

    @Override
    public void editEmployee(Employee employee) {

    }

    @Override
    public void addEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(int id) {

    }

    @Override
    public boolean login(String login, String password) {
        return false;
    }

    @Override
    public boolean register(Employee employee) {
        return false;
    }
}
