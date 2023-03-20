package com.innowise.task3.controller;

public enum CommandName {
    GET_EMPLOYEES(URI.EMPLOYEES, HttpMethod.GET),
    GET_EMPLOYEE_WITH_ID(URI.EMPLOYEE, HttpMethod.GET),
    ADD_EMPLOYEE(URI.EMPLOYEES, HttpMethod.POST),
    DELETE_EMPLOYEE_WITH_ID(URI.EMPLOYEE, HttpMethod.DELETE),
    EDIT_EMPLOYEE_WITH_ID(URI.EMPLOYEE, HttpMethod.PUT),

    INVALID_REQUEST(URI.ERROR, HttpMethod.GET),
    LOGIN(URI.SESSION, HttpMethod.POST),
    LOGOUT(URI.SESSION, HttpMethod.DELETE);

    private String uri;
    private String httpMethod;

    public String getUri() {
        return uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }


    CommandName(String uri, String httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;

    }

    public static class URI {
        public static final String ERROR = "/error";
        public static final String EMPLOYEES = "/employees";
        public static final String EMPLOYEE = "/employee/";
        public static final String SESSION = "/session";

    }
    public static class HttpMethod {

        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";

    }

}
