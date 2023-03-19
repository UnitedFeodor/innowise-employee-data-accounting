package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.controller.utils.ControllerUtils;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteEmployeeWithIdExecutor implements Command {
    private static final String UNABLE_TO_DELETE_THE_EMPLOYEE = "Unable to delete the employee";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int employeeId = ControllerUtils.getIdFromLastQuerySegment(request.getServletPath());
            employeeService.deleteEmployee(employeeId);

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (ServiceException e) {
            request.setAttribute(LoginExecutor.ERROR_MESSAGE, UNABLE_TO_DELETE_THE_EMPLOYEE);
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);
        }



    }
}
