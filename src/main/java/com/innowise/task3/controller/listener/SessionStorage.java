package com.innowise.task3.controller.listener;

import com.innowise.task3.controller.implementation.LoginExecutor;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebListener
public class SessionStorage implements HttpSessionListener {

    private final static List<HttpSession> sessionList = new ArrayList<>();

    public List<HttpSession> getSessionsForUserId(int userId) {
        return sessionList.stream()
                .filter(e -> userId == (int) e.getAttribute(LoginExecutor.ID_TOKEN))
                .collect(Collectors.toList());
    }


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        sessionList.add(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionList.remove(se.getSession());
    }


}
