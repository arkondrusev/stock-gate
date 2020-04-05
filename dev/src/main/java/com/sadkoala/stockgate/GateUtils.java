package com.sadkoala.stockgate;

public class GateUtils {

    public static void checkParamEmpty(String param, String paramName) {
        if (param == null || param.isBlank()) {
            throw new IllegalArgumentException("Parameter \"" + paramName + "\" is empty");
        }
    }

    public static void checkParamEmpty(byte[] param, String paramName) {
        if (param == null || param.length == 0) {
            throw new IllegalArgumentException("Parameter \"" + paramName + "\" is empty");
        }
    }

}
