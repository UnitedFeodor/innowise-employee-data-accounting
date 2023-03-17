package com.innowise.task3.controller;

public enum CommandName {
    GET_EMPLOYEES(URI.URI_EMPLOYEES, HttpMethod.GET),
    GET_EMPLOYEE_WITH_ID(URI.URI_EMPLOYEE, HttpMethod.GET),
    ADD_EMPLOYEE(URI.URI_EMPLOYEES, HttpMethod.POST),
    DELETE_EMPLOYEE_WITH_ID(URI.URI_EMPLOYEE, HttpMethod.DELETE),
    EDIT_EMPLOYEE_WITH_ID(URI.URI_EMPLOYEE, HttpMethod.PUT),
    INVALID_REQUEST(URI.URI_ERROR, HttpMethod.GET),
    LOGIN(URI.URI_LOGIN, HttpMethod.GET);
    // TODO change or something REGISTER;


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
        public static final String URI_ERROR = "/error";
        public static final String URI_EMPLOYEES = "/employees";
        public static final String URI_EMPLOYEE = "/employee/";
        public static final String URI_LOGIN = "/login";

    }
    public static class HttpMethod {

        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";

    }

}
