package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.GateUtils;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class BinanceCommunicator extends AbstractStockCommunicator {

    private static final String SECRET_KEY_VALUE = "5Pffrmzjxjalryu4WenfVXiaCSGCiDGSgtknvCrPVb6ew0UeIsh7oH5bYicU3sx1";
    private static final String API_KEY_VALUE = "0Wm7F0NYk38njHwhmPzcxT14A5cbGs5Ri5d7TnvKJbizulrp5EIDHoA0M6FYunuk";

    private static final String HOST = "api.binance.com";

    private static final String HEADER_MBX_APIKEY = "X-MBX-APIKEY";

    private static final String ENDPOINT_ORDER = "/api/v3/order";

    private static final String REQUEST_PARAM_SYMBOL = "symbol";
    public static final String REQUEST_PARAM_CLIENT_ORDER_ID = "origClientOrderId";

    public static String requestLatestSymbolPrice(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return HttpsCommunicator.executeHttpsRequest(HOST + "/api/v3/ticker/price?" + REQUEST_PARAM_SYMBOL + "=" + symbol);
    }

    public static String requestOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return requestGetWithAuthorization("/api/v3/openOrders", REQUEST_PARAM_SYMBOL + "=" + symbol + "&" + prepareCommonParams());
    }

    public static String requestAccountInfo() throws Exception {
        return requestGetWithAuthorization("/api/v3/account", prepareCommonParams());
    }

    public static String requestOrderbook(final String symbol, final int limit) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        if (limit != 0) {
            checkParamLimitValid(limit);
        }

        String urlString = HOST + "/api/v3/depth" + "?" + REQUEST_PARAM_SYMBOL + "=" + symbol;
        if (limit != 0) {
            urlString = urlString + "&limit=" + limit;
        }
        return HttpsCommunicator.executeHttpsRequest(urlString);
    }

    private static void checkParamLimitValid(int limit) {
        return;
    }

    public static String requestOrderbook(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return requestOrderbook(symbol, 0);
    }

    public static String requestNewOrder(String symbol, String side, String type, BigDecimal qty, BigDecimal price) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotEmpty(type, "type");
        // validate type market or limit
        GateUtils.checkParamNotNull(qty, "qty");

        URLParamsBuilder paramsBuilder = URLParamsBuilder.newBuilder();
        paramsBuilder.addParamIfNotEmpty(REQUEST_PARAM_SYMBOL, symbol)
                .addParamIfNotEmpty("side", side)
                .addParamIfNotEmpty("type", type)
                .addParamIfNotEmpty("quantity", qty.toPlainString())
                .addParamIfNotEmpty("newOrderRespType", "FULL");
        if ("LIMIT".equals(type)) {
            GateUtils.checkParamNotNull(price, "price");
            paramsBuilder.addParamIfNotEmpty("price", price.toPlainString());
            paramsBuilder.addParamIfNotEmpty("timeInForce", "GTC");
        }
        addCommonParams(paramsBuilder);

        return requestPostWithAuthorization(ENDPOINT_ORDER, paramsBuilder.build());
    }

    public static String requestCheckOrderStatus(String symbol, String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        URLParamsBuilder paramsBuilder = URLParamsBuilder.newBuilder();
        paramsBuilder.addParamIfNotEmpty(REQUEST_PARAM_SYMBOL, symbol)
                .addParamIfNotEmpty(REQUEST_PARAM_CLIENT_ORDER_ID, orderId);
        addCommonParams(paramsBuilder);

        return requestGetWithAuthorization(ENDPOINT_ORDER, paramsBuilder.build());
    }

    public static String requestCancelOrder(String symbol, String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        URLParamsBuilder paramsBuilder = URLParamsBuilder.newBuilder();
        paramsBuilder.addParamIfNotEmpty(REQUEST_PARAM_SYMBOL, symbol);
        paramsBuilder.addParamIfNotEmpty(REQUEST_PARAM_CLIENT_ORDER_ID, orderId);
        addCommonParams(paramsBuilder);
        String urlString = HOST + ENDPOINT_ORDER + "?" + signParams(paramsBuilder.build());
        Map<String,String> headers = new HashMap<>();
        headers.put(HEADER_MBX_APIKEY, API_KEY_VALUE);
        return HttpsCommunicator.executeHttpsRequest(urlString, headers, "DELETE", EMPTY_STRING);
    }

    private static String prepareCommonParams() {
        URLParamsBuilder paramsBuilder = URLParamsBuilder.newBuilder();
        addCommonParams(paramsBuilder);
        return paramsBuilder.build();
    }

    private static void addCommonParams(URLParamsBuilder paramsBuilder) {
        paramsBuilder.addParamIfNotEmpty("recvWindow", 60000)
                .addParamIfNotEmpty("timestamp", getTimestamp());
    }

    /**
     * endpoint - url part after host name and before "?"
     * requestParams - url params after "?" (without signature)
     */
    private static String requestGetWithAuthorization(String endpoint, String requestParams) throws Exception {
        Map<String,String> headers = new HashMap<>();
        headers.put(HEADER_MBX_APIKEY, API_KEY_VALUE);
        return HttpsCommunicator.executeGetRequest(HOST + endpoint + "?" + signParams(requestParams), headers);
    }

    private static String requestGetWithAuthorization(String endpoint) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");

        return requestGetWithAuthorization(endpoint, EMPTY_STRING);
    }

    private static String requestPostWithAuthorization(final String endpoint, final String requestParams) throws Exception {
        Map<String,String> headers = new HashMap<>();
        headers.put(HEADER_MBX_APIKEY, API_KEY_VALUE);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return HttpsCommunicator.executePostRequest(HOST + endpoint, signParams(requestParams), headers);
    }

    private static String signParams(final String requestParams) throws NoSuchAlgorithmException, InvalidKeyException {
        return requestParams + "&signature=" + makeSignature(requestParams);
    }

    private static long getTimestamp() {
        return System.currentTimeMillis();
    }

    private static String makeSignature(String text) throws InvalidKeyException, NoSuchAlgorithmException {
        return new String(bytesToHex(encodeHmac256(toByteArr(text), toByteArr(SECRET_KEY_VALUE))));
    }

}
