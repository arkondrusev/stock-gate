package com.sadkoala.stockgate.communicator;

import com.sadkoala.stockgate.GateUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public abstract class AbstractStockCommunicator {

    protected static final String EMPTY_STRING = "";

    protected static class URLParamsBuilder {

        private StringBuilder params = new StringBuilder();

        public static URLParamsBuilder newBuilder() {
            return new URLParamsBuilder();
        }

        private void addParam(String paramName, String paramValue) {
            GateUtils.checkParamNotEmpty(paramName, "paramName");
            GateUtils.checkParamNotEmpty(paramValue, "paramValue");

            if (params.length() > 0) {
                params.append("&");
            }
            params.append(paramName).append("=").append(paramValue);
        }

        public URLParamsBuilder addParamIfNotEmpty(String paramName, String paramValue) {
            GateUtils.checkParamNotEmpty(paramName, "paramName");

            if (!GateUtils.isParamEmpty(paramValue)) {
                addParam(paramName, paramValue);
            }

            return this;
        }

        public URLParamsBuilder addParamIfNotEmpty(String paramName, Integer paramValue) {
            if (Objects.nonNull(paramValue)) {
                addParamIfNotEmpty(paramName, String.valueOf(paramValue));
            }

            return this;
        }

        public URLParamsBuilder addParamIfNotEmpty(String paramName, Float paramValue) {
            if (Objects.nonNull(paramValue)) {
                addParamIfNotEmpty(paramName, String.valueOf(paramValue));
            }

            return this;
        }

        public URLParamsBuilder addParamIfNotEmpty(String paramName, Short paramValue) {
            if (Objects.nonNull(paramValue)) {
                addParamIfNotEmpty(paramName, String.valueOf(paramValue));
            }

            return this;
        }

        public URLParamsBuilder addParamIfNotEmpty(String paramName, long paramValue) {
            addParamIfNotEmpty(paramName, String.valueOf(paramValue));

            return this;
        }

        public URLParamsBuilder addParamIfNotEmtpy(String paramName, String paramValue, String defaultValue) {
            GateUtils.checkParamNotEmpty(paramName, "paramName");

            addParamIfNotEmpty(paramName, Objects.requireNonNullElse(paramValue, defaultValue));

            return this;
        }

        public String build() {
            return params.toString();
        }

    }

    /*
    public static IStockCommunicator createCommunicator(String stockShortName) {
        if (STOCK_BINANCE_MARKER.equals(stockShortName)) {
            return new BinanceCommunicator();
        } else if (STOCK_HITBTC_MARKER.equals(stockShortName)) {
            return null;// new HitbtcCommunicator();
        } else if (STOCK_OKEX_MARKER.equals(stockShortName)) {
            return null;
        } else {
            throw new NotImplementedException(String.format("Нет имплементации коммуникатора для биржи [%s]", stockShortName));
        }
    }
    */

    protected static String buildRequestUrl(String host, String endpoint, String params) {
        GateUtils.checkParamNotEmpty(host, "host");
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");

        StringBuilder sb = new StringBuilder(host).append(endpoint);
        if (!GateUtils.isParamEmpty(params)) {
            sb.append("?").append(params);
        }

        return sb.toString();
    }

    protected static byte[] encodeHmac256(byte[] message, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        GateUtils.checkParamNotEmpty(message, "message");
        GateUtils.checkParamNotEmpty(key, "key");

        String hmacSha256_Algo = "HmacSHA256";
        Mac sha256_HMAC = Mac.getInstance(hmacSha256_Algo);
        sha256_HMAC.init(new SecretKeySpec(key, hmacSha256_Algo));
        return sha256_HMAC.doFinal(message);
    }

    protected static char[] bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return hexChars;
    }

    protected static byte[] encodeBase64(byte[] text) {
        return Base64.getEncoder().encode(text);
    }

    protected static String toString(byte[] input) {
        return new String(input, StandardCharsets.UTF_8);
    }

    protected static byte[] toByteArr(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

}
