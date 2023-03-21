package com.innowise.task3.dao;

import com.innowise.task3.dao.implementation.EmployeeDAOImplementation;

public class DAOProvider {
    private static final DAOProvider INSTANCE = new DAOProvider();

    private final EmployeeDAO employeeDAO = new EmployeeDAOImplementation();

    private DAOProvider() {
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    public static DAOProvider getInstance() {
        return INSTANCE;
    }
}
