package com.sadkoala.stockgate;

public class GateUtils {

    public static void checkParamNotEmpty(String param, String paramName) {
        if (param == null || param.isBlank()) {
            throw new IllegalArgumentException("Parameter \"" + paramName + "\" is empty");
        }
    }

    public static void checkParamNotEmpty(byte[] param, String paramName) {
        if (param == null || param.length == 0) {
            throw new IllegalArgumentException("Parameter \"" + paramName + "\" is empty");
        }
    }

    public static void checkParamNotNull(Object param, String paramName) {
        if (param == null) {
            throw new NullPointerException("Parameter \"" + paramName + "\" is null");
        }
    }

}
