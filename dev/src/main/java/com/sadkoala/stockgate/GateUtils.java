package com.sadkoala.stockgate;

import java.util.Collection;
import java.util.Objects;

public class GateUtils {

    public static boolean isParamEmpty(String param) {
        return Objects.isNull(param) || param.isBlank();
    }

    public static void checkParamNotEmpty(String param, String paramName) {
        if (isParamEmpty(param)) {
            throwExceptionParamIsEmpty(paramName);
        }
    }

    public static void checkParamNotEmpty(byte[] param, String paramName) {
        checkParamNotNull(param, paramName);
        if (param.length == 0) {
            throwExceptionParamIsEmpty(paramName);
        }
    }

    public static void checkParamNotNull(Object param, String paramName) {
        if (param == null) {
            throw new NullPointerException("Parameter \"" + paramName + "\" is null");
        }
    }

    public static void checkParamNotEmpty(Collection param, String paramName) {
        if (Objects.isNull(param) || param.isEmpty()) {
            throwExceptionParamIsEmpty(paramName);
        }
    }

    public static void throwExceptionParamIsEmpty(String paramName) throws IllegalArgumentException {
        throw new IllegalArgumentException("Parameter \"" + paramName + "\" is empty");
    }

    public static void checkOneParamNotEmpty(String paramOne, String paramOneName, String paramTwo, String paramTwoName) {
        // если оба параметра пустые или оба непустые выдаем ошибку
        boolean paramOneEmpty = isParamEmpty(paramOne);
        boolean paramTwoEmpty = isParamEmpty(paramTwo);
        if (paramOneEmpty && paramTwoEmpty) {
            throw new IllegalStateException("Both params \"" + paramOneName + "\" and \"" + paramTwoName + "\" are empty");
        }
        if (!(paramOneEmpty || paramTwoEmpty)) {
            throw new IllegalStateException("Both params \"" + paramOneName + "\" and \"" + paramTwoName + "\" are not empty");
        }
    }

}
