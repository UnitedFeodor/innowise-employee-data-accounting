package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.controller.utils.Utils;
import com.innowise.task3.dto.EditEmployeeDTO;
import com.innowise.task3.dto.EmployeeDTO;
import com.innowise.task3.dto.LoginDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginExecutor implements Command {

    // TODO put constants elsewhere i guess
    public static final String ID_TOKEN = "idToken";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String ERROR_MESSAGE = "errorMessage";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginDTO loginDTO = objectMapper.readValue(request.getReader(), LoginDTO.class);

        EmployeeDTO employee = employeeService.login(loginDTO);
        if (employee != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute(ID_TOKEN,employee.getId());
            session.setAttribute(ACCESS_TOKEN,employee.getRole());

            String employeeJsonString = objectMapper.writeValueAsString(employee);

            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(employeeJsonString);
            out.flush();
        } else {
            request.setAttribute(ERROR_MESSAGE,"unable to login");
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);
            //response.sendRedirect(String.valueOf(CommandName.INVALID_REQUEST));

        }
    }
}
