package com.innowise.task3.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.innowise.task3.controller.CommandExecutor;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.controller.utils.ControllerUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class InvalidRequestExecutor implements CommandExecutor {

    public static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR = "error";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String errorMessage = (String) request.getAttribute(ERROR_MESSAGE);

        ObjectNode errorJsonNode = objectMapper.createObjectNode();
        errorJsonNode.put(ERROR,errorMessage);
        String errorJsonString = objectMapper.writeValueAsString(errorJsonNode);

        ControllerUtils.writeJSONResponse(response,errorJsonString, HttpServletResponse.SC_BAD_REQUEST);

    }
}
