package com.innowise.task3.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperProvider {

    private final static ObjectMapperProvider INSTANCE = new ObjectMapperProvider();

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private ObjectMapperProvider(){

    }

    public static ObjectMapperProvider getInstance() {
        return INSTANCE;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
