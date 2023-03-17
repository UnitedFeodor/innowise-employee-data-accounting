package com.innowise.task3.controller.utils;

public class Utils {

    public static int getIdFromLastQuerySegment(String queryString) throws NumberFormatException {
        String[] segments = queryString.split("/");
        return Integer.parseInt(segments[segments.length-1]);
    }
}
