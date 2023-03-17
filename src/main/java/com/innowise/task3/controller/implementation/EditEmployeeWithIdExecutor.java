package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.controller.utils.Utils;
import com.innowise.task3.dto.AddEmployeeDTO;
import com.innowise.task3.dto.EditEmployeeDTO;
import com.innowise.task3.dto.EmployeeDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class EditEmployeeWithIdExecutor implements Command {

    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EditEmployeeDTO employeeDTO = objectMapper.readValue(request.getReader(), EditEmployeeDTO.class);

        int employeeId = Utils.getIdFromLastQuerySegment(request.getServletPath());
        employeeDTO.setId(employeeId);
        EmployeeDTO employee = employeeService.editEmployee(employeeDTO);
        String employeeJsonString = objectMapper.writeValueAsString(employee);

        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(employeeJsonString);
        out.flush();
    }
}
