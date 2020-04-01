package com.sadkoala.stockgate;

public class GateUtils {

    public static void checkParamEmpty(String param, String paramName) {
        if (param == null || param.isBlank()) {
            throw new IllegalArgumentException("Parameter \"" + paramName + "\" is empty");
        }
    }

    public static String millisToISO8601Timestamp(Long timeMillis) {
        return null;
    }

    public static Long iso8601TimestampToMillis(String timestamp) {
        return null;
    }

}
