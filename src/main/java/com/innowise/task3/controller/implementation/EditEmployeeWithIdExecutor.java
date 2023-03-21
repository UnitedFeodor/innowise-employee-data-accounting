package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.CommandExecutor;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.controller.listener.SessionStorage;
import com.innowise.task3.controller.listener.SessionStorageProvider;
import com.innowise.task3.controller.utils.ControllerUtils;
import com.innowise.task3.entity.dto.EditEmployeeDTO;
import com.innowise.task3.entity.dto.EmployeeDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class EditEmployeeWithIdExecutor implements CommandExecutor {

    private static final String UNABLE_TO_EDIT_THE_EMPLOYEE = "Unable to edit the employee";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    private final SessionStorage sessionStorage = SessionStorageProvider.getInstance().getSessionStorage();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            EditEmployeeDTO employeeDTO = objectMapper.readValue(request.getReader(), EditEmployeeDTO.class);

            int employeeId = ControllerUtils.getIdFromLastQuerySegment(request.getServletPath());
            employeeDTO.setId(employeeId);

            EmployeeDTO employee = employeeService.editEmployee(employeeDTO);

            List<HttpSession> employeeSessions = sessionStorage.getSessionsForUserId(employeeId);

            Integer oldRole = (Integer) employeeSessions.stream()
                    .findFirst()
                    .map(e -> e.getAttribute(LoginExecutor.ROLE_TOKEN))
                    .orElse(null);

            // change role token in employee sessions because role data is changed
            if (!Objects.equals(employee.getRole(), oldRole)) {
                employeeSessions.forEach(e -> e.setAttribute(LoginExecutor.ROLE_TOKEN,employee.getRole()));
            }

            String employeeJsonString = objectMapper.writeValueAsString(employee);
            ControllerUtils.writeJSONResponse(response,employeeJsonString, HttpServletResponse.SC_OK);

        } catch (ServiceException e) {
            request.setAttribute(InvalidRequestExecutor.ERROR_MESSAGE, UNABLE_TO_EDIT_THE_EMPLOYEE);
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);
        }


    }
}
