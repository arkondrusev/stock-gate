package com.sadkoala.stockgate.communicator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.GateUtils;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class OkexCommunicator extends AbstractStockCommunicator {

    private static final String SECRET_KEY_VALUE = "DFA18FF5FE4F9EF31A3C906E24DD43B3";
    private static final String API_KEY_VALUE = "c6626a6c-2379-4578-a106-7a16486600b4";
    private static final String PASSPHRASE_VALUE = "HoggyLezzy";

    private static final String HOST = "www.okex.com";

    private static final String ENDPOINT_ORDERS = "/api/spot/v3/orders";

    private static ObjectMapper mapper = new ObjectMapper();

    public static String requestOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return requestWithAuthorization("/api/spot/v3/orders_pending", "instrument_id=" + symbol);
    }

    public static String requestBalances() throws Exception {
        return requestWithAuthorization("/api/spot/v3/accounts");
    }

    public static String requestOrderbook(final String symbol, final Integer limit, final Float depth) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return HttpsCommunicator.executeHttpsRequest(
                buildRequestUrl(HOST,
                        "/api/spot/v3/instruments/{symbol}/book".replace("{symbol}", symbol),
                        URLParamsBuilder.newBuilder()
                                .addParamIfNotEmpty("size", limit)
                                .addParamIfNotEmpty("depth", depth)
                                .build()));
    }

    public static String requestNewOrder(final String symbol, final String side, final String type, final BigDecimal qty, final BigDecimal price) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotEmpty(type, "type");
        GateUtils.checkParamNotNull(qty, "qty");

        return requestPostWithAuthorization(ENDPOINT_ORDERS,
                makeNewOrderRequestContent(symbol, side, type, qty.toPlainString(), price.toPlainString()));
    }

    public static String requestCheckOrderStatus(final String symbol, final String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return requestWithAuthorization(ENDPOINT_ORDERS + "/" + orderId, "instrument_id=" + symbol);
    }

    public static String requestCancelOrder(final String symbol, final String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return requestPostWithAuthorization("/api/spot/v3/cancel_orders" + "/" + orderId, makeCancelOrderRequestContent(symbol));
    }

    private static String makeCancelOrderRequestContent(final String symbol) throws JsonProcessingException {
        ObjectNode root = mapper.createObjectNode();
        root.put("instrument_id", symbol);

        return mapper.writeValueAsString(root);
    }

    private static String makeNewOrderRequestContent(final String symbol, final String side, final String type, final String qty, final String price) throws JsonProcessingException {
        ObjectNode root = mapper.createObjectNode();
        root.put("instrument_id", symbol);
        root.put("side", side);
        root.put("type", type);
        if ("limit".equals(type)) {
            root.put("size", qty);
            root.put("price", price);
        } else {
            if ("buy".equals(side)) {
                root.put("notional", qty);
            } else {
                root.put("size", qty);
            }
        }

        return mapper.writeValueAsString(root);
    }

    private static String requestWithAuthorization(final String endpoint, final String params) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");
        GateUtils.checkParamNotNull(params, "params");

        String requestPath = endpoint;
        if (!params.isBlank()) {
            requestPath = requestPath + "?" + params;
        }
        return HttpsCommunicator.executeGetRequest(HOST + requestPath,
                makeAuthHeaders("GET", requestPath, EMPTY_STRING));
    }

    private static String requestWithAuthorization(final String endpoint) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");

        return requestWithAuthorization(endpoint, EMPTY_STRING);
    }

    private static String requestPostWithAuthorization(final String endpoint, final String requestContent) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");
        GateUtils.checkParamNotNull(requestContent, "requestContent");

        Map<String,String> headers = makeAuthHeaders("POST", endpoint, requestContent == null ? EMPTY_STRING : requestContent);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Accept", "application/json");
        return HttpsCommunicator.executePostRequest(HOST + endpoint, requestContent, headers);
    }

    private static Map<String, String> makeAuthHeaders(String httpMethod, String endpoint, String requestContent) throws NoSuchAlgorithmException, InvalidKeyException {
        String timestamp = getUnixTime();
        Map<String,String> headers = new HashMap<>();
        headers.put("OK-ACCESS-KEY", API_KEY_VALUE);
        headers.put("OK-ACCESS-SIGN", makeSign(timestamp + httpMethod + endpoint + requestContent));
        headers.put("OK-ACCESS-TIMESTAMP", timestamp);
        headers.put("OK-ACCESS-PASSPHRASE", PASSPHRASE_VALUE);

        return headers;
    }

    private static String makeSign(String textToSign) throws InvalidKeyException, NoSuchAlgorithmException {
        return toString(encodeBase64(encodeHmac256(toByteArr(textToSign), toByteArr(SECRET_KEY_VALUE))));
    }

    /**
     * UNIX timestamp ISO 8601 rule eg: 2018-02-03T05:34:14.110Z
     */
    private static String getUnixTime() {
        String now = Instant.now().toString();
        // Instant.now().toString() в windows и linux могут давать разные результаты, поэтому приводим к нужному нам текстовому формату принудительно
        int dotIndex = now.lastIndexOf(".");
        return now.substring(0, dotIndex)
                + now.substring(dotIndex).substring(0,4)
                + now.substring(now.length()-1);
    }

}
