package com.innowise.task3.listener;

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
