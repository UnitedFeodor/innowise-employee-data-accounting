package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.dto.AddEditEmployeeDTO;
import com.innowise.task3.dto.EmployeeDTO;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AddEmployeeExecutor implements Command {

    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddEditEmployeeDTO addEditEmployeeDTO = objectMapper.readValue(request.getReader(), AddEditEmployeeDTO.class);
        EmployeeDTO employeeDTO = employeeService.addEmployee(addEditEmployeeDTO);

        response.setStatus(HttpServletResponse.SC_CREATED);
        String employeesJsonString = objectMapper.writeValueAsString(employeeDTO);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(employeesJsonString);
        out.flush();

    }
}
