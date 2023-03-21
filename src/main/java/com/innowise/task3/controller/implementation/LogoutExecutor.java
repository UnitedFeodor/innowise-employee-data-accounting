package com.innowise.task3.controller.implementation;

import com.innowise.task3.controller.CommandExecutor;
import com.innowise.task3.controller.CommandName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutExecutor implements CommandExecutor {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        } else {
            request.setAttribute(InvalidRequestExecutor.ERROR_MESSAGE, "Can't log out: not logged in");
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
