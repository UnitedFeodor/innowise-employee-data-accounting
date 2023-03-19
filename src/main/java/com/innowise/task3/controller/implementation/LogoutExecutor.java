package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
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

public class LogoutExecutor implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        } else {
            request.setAttribute(LoginExecutor.ERROR_MESSAGE, "Can't log out: not logged in");
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
