package com.innowise.task3.controller.listener;

import com.innowise.task3.dao.DAOProvider;

public class SessionStorageProvider {

    private static final SessionStorageProvider INSTANCE = new SessionStorageProvider();

    private final SessionStorage sessionStorage = new SessionStorage();
    private SessionStorageProvider(){

    }
    public static SessionStorageProvider getInstance() {
        return INSTANCE;
    }

    public SessionStorage getSessionStorage() {
        return sessionStorage;
    }
}
