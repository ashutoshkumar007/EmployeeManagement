package com.employeemanagement.utils;

import com.google.gson.Gson;

public class TransformUtil {

    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        try {
            if (object != null) {
                return gson.toJson(object);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
