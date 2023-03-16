package com.innowise.task3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class DispatcherServlet extends HttpServlet {
    private final CommandProvider provider = new CommandProvider();

    private static final String REQ_PARAM_COMMAND = "command";
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(REQ_PARAM_COMMAND);
        Command command = provider.getCommand(commandName);
        command.execute(request, response);
    }


}
