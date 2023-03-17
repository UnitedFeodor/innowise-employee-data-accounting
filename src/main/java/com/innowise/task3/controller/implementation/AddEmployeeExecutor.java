package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.dto.EmployeeDTO;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AddEmployeeExecutor implements Command {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
