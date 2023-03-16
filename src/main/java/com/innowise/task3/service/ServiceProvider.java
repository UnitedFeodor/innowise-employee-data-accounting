package com.innowise.task3.service;

import com.innowise.task3.service.implementation.EmployeeServiceImplementation;

public class ServiceProvider {

    private final static ServiceProvider instance = new ServiceProvider();

    private final EmployeeService employeeService = new EmployeeServiceImplementation();

    private ServiceProvider() {
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }
    public static ServiceProvider getInstance() {
        return instance;
    }
}
