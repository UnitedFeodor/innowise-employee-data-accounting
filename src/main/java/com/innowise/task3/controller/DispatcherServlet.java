package com.innowise.task3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(
        urlPatterns = "/",
        name = "DispatcherServlet"
)
public class DispatcherServlet extends HttpServlet {
    private final CommandProvider provider = CommandProvider.getInstance();

    private static final String REQ_PARAM_COMMAND = "command";

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parseAndExecuteCommand(req,resp);
    }


    private void parseAndExecuteCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getServletPath();
        String httpMethod = request.getMethod();

        CommandExecutor commandExecutor = provider.getCommandExecutor(commandName, httpMethod);
        commandExecutor.execute(request, response);
    }
}
