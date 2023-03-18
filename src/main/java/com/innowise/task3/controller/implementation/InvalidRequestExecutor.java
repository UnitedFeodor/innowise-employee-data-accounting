package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.innowise.task3.controller.Command;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class InvalidRequestExecutor implements Command {

    private static final String ERROR = "error";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String errorMessage = (String) request.getAttribute(LoginExecutor.ERROR_MESSAGE);

        ObjectNode errorJSON = objectMapper.createObjectNode();
        errorJSON.put(ERROR,errorMessage);

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(objectMapper.writeValueAsString(errorJSON));
        out.flush();
    }
}
