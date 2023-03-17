package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.dto.AddEmployeeDTO;
import com.innowise.task3.dto.EmployeeDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class AddEmployeeExecutor implements Command {

    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddEmployeeDTO addEmployeeDTO = objectMapper.readValue(request.getReader(), AddEmployeeDTO.class);
        EmployeeDTO employeeDTO = employeeService.addEmployee(addEmployeeDTO);

        response.setStatus(HttpServletResponse.SC_CREATED);
        String employeesJsonString = objectMapper.writeValueAsString(employeeDTO);

        response.setStatus(HttpServletResponse.SC_CREATED);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(employeesJsonString);
        out.flush();

    }
}
