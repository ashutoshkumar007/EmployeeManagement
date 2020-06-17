package com.employeemanagement.utils;

import java.util.UUID;

public class LogUtil {
    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}
