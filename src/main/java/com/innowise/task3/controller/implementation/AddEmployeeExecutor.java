package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.controller.utils.ControllerUtils;
import com.innowise.task3.dto.AddEmployeeDTO;
import com.innowise.task3.dto.EmployeeDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddEmployeeExecutor implements Command {

    private static final String UNABLE_TO_ADD_THE_EMPLOYEE = "Unable to add the employee";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            AddEmployeeDTO addEmployeeDTO = objectMapper.readValue(request.getReader(), AddEmployeeDTO.class);
            EmployeeDTO employeeDTO = employeeService.addEmployee(addEmployeeDTO);

            response.setStatus(HttpServletResponse.SC_CREATED);
            String employeeJsonString = objectMapper.writeValueAsString(employeeDTO);

            ControllerUtils.writeJSONResponse(response,employeeJsonString, HttpServletResponse.SC_CREATED);

        } catch (ServiceException e) {
            request.setAttribute(InvalidRequestExecutor.ERROR_MESSAGE, UNABLE_TO_ADD_THE_EMPLOYEE);
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);
        }



    }
}
