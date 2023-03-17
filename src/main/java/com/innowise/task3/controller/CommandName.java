package com.innowise.task3.controller;

public enum CommandName {
    GET_EMPLOYEES("/employees", "GET"),
    GET_EMPLOYEE_WITH_ID("/employee/","GET"),
    ADD_EMPLOYEE("/employees", "POST"),
    DELETE_EMPLOYEE_WITH_ID("/employee/","DELETE"),
    EDIT_EMPLOYEE_WITH_ID("/employee/","PUT"),
    INVALID_REQUEST("/error","GET"),
    LOGIN("/login","GET");
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
}
