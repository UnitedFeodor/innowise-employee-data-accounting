package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.CommandExecutor;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.controller.utils.ControllerUtils;
import com.innowise.task3.entity.dto.EmployeeDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GetEmployeeWithIdExecutor implements CommandExecutor {
    private static final String UNABLE_TO_GET_EMPLOYEE_DATA = "Unable to get employee data";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int employeeId = ControllerUtils.getIdFromLastQuerySegment(request.getServletPath());

            EmployeeDTO employee = employeeService.getEmployeeWithId(employeeId);

            String employeeJsonString = objectMapper.writeValueAsString(employee);

            ControllerUtils.writeJSONResponse(response,employeeJsonString, HttpServletResponse.SC_OK);

        } catch (ServiceException e) {
            request.setAttribute(InvalidRequestExecutor.ERROR_MESSAGE, UNABLE_TO_GET_EMPLOYEE_DATA);
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);
        }

    }
}
