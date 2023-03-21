package com.innowise.task3.controller.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ControllerUtils {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String UTF_8 = "UTF-8";

    public static int getIdFromLastQuerySegment(String queryString) throws NumberFormatException {
        String[] segments = queryString.split("/");
        return Integer.parseInt(segments[segments.length-1]);
    }

    public static void writeJSONResponse(HttpServletResponse response, String formedJSON, int status) throws IOException {
        response.setStatus(status);
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(UTF_8);
        out.print(formedJSON);
        out.flush();

    }
}
