package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.controller.utils.ControllerUtils;
import com.innowise.task3.dto.EmployeeDTO;
import com.innowise.task3.dto.LoginDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginExecutor implements Command {

    public static final String ID_TOKEN = "idToken";
    public static final String ROLE_TOKEN = "accessToken";
    private static final String INVALID_ACCOUNT_DATA = "Invalid account data";
    private static final String UNABLE_TO_LOGIN = "Unable to login";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginDTO loginDTO = objectMapper.readValue(request.getReader(), LoginDTO.class);

            EmployeeDTO employee = employeeService.login(loginDTO);
            if (employee != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute(ID_TOKEN, employee.getId());
                session.setAttribute(ROLE_TOKEN, employee.getRole());

                String employeeJsonString = objectMapper.writeValueAsString(employee);

                ControllerUtils.writeJSONResponse(response,employeeJsonString, HttpServletResponse.SC_OK);

            } else {
                request.setAttribute(InvalidRequestExecutor.ERROR_MESSAGE, INVALID_ACCOUNT_DATA);
                request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request, response);

            }
        } catch (ServiceException e) {
            request.setAttribute(InvalidRequestExecutor.ERROR_MESSAGE, UNABLE_TO_LOGIN);
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);
        } 
    }
}
