package com.sadkoala.stockgate;

public class ParameterUtils {

    public static void checkParamEmpty(String param, String paramName) {
        if (param == null || param.isBlank()) {
            throw new IllegalArgumentException("Parameter \"" + paramName + "\" is empty");
        }
    }

}
