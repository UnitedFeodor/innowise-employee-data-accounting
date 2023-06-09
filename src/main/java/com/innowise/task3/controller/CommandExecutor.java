package com.innowise.task3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface CommandExecutor {
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
