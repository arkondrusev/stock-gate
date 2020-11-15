package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.GateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HitbtcCommunicator extends AbstractStockCommunicator {

    private static final String PRIVATE_KEY_VALUE = "38ae01062504c26ce0e3a295f25eb628";
    private static final String SECRET_KEY_VALUE = "be45b2caa23f600bfb0764ac5bd05fa0";

    private static final String AUTH_HEADER_VALUE = "Basic "
            + toString(encodeBase64(toByteArr(PRIVATE_KEY_VALUE + ":" + SECRET_KEY_VALUE)));

    private static final String HOST = "api.hitbtc.com";

    private static final String ENDPOINT_ORDER = "/api/2/order";
    private static final String ENDPOINT_TICKER = "/api/2/public/ticker";

    public static String requestOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return requestWithAuthorization("/api/2/order", "symbol=" + symbol);
    }

    public static String requestTradingBalance() throws Exception {
        return requestWithAuthorization("/api/2/trading/balance");
    }

    public static String requestOrderbook(List<String> symbols, int limit) throws Exception {
        GateUtils.checkParamNotEmpty(symbols, "symbols");
        if (limit < 0) {
            throw new IllegalArgumentException("Limit < 0");
        }

        URLParamsBuilder urlBuilder = new URLParamsBuilder();
        urlBuilder.addParamIfNotEmpty("symbols", GateUtils.listToCommaSeparatedString(symbols));
        urlBuilder.addParamIfNotEmpty("limit", limit);
        return HttpsCommunicator.executeHttpsRequest(buildRequestUrl(HOST, "/api/2/public/orderbook", urlBuilder.build()));
    }

    public static String requestOrderbook(String symbol, int limit) throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add(symbol);
        return requestOrderbook(symbols, limit);
    }

    public static String requestNewOrder(String symbol, String side, String type, BigDecimal qty, BigDecimal price) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotEmpty(type, "type");
        // validate type market or limit
        GateUtils.checkParamNotNull(qty, "qty");

        URLParamsBuilder paramsBuilder = URLParamsBuilder.newBuilder();
        paramsBuilder.addParamIfNotEmpty("symbol", symbol)
                .addParamIfNotEmpty("side", side)
                .addParamIfNotEmpty("type", type)
                .addParamIfNotEmpty("quantity", qty.toPlainString());
        if ("limit".equals(type)) {
            GateUtils.checkParamNotNull(price, "price");
            paramsBuilder.addParamIfNotEmpty("price", price.toPlainString());
            paramsBuilder.addParamIfNotEmpty("timeInForce", "GTC");
        }

        return requestPostWithAuthorization(ENDPOINT_ORDER, paramsBuilder.build());
    }

    public static String requestCheckOrderStatus(String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return requestWithAuthorization(ENDPOINT_ORDER + "/" + orderId);
    }

    public static String requestCancelOrder(String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        String urlString = HOST + ENDPOINT_ORDER + "/" + orderId;
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", AUTH_HEADER_VALUE);
        return HttpsCommunicator.executeHttpsRequest(urlString, headers, "DELETE", null);
    }

    public static String requestSymbolsTickers(List<String> symbols) throws Exception {
        if (!GateUtils.isParamEmpty(symbols)) {
            URLParamsBuilder urlBuilder = new URLParamsBuilder();
            urlBuilder.addParamIfNotEmpty("symbols", GateUtils.listToCommaSeparatedString(symbols));
            return HttpsCommunicator.executeGetRequest(buildRequestUrl(HOST, ENDPOINT_TICKER, urlBuilder.build()));
        } else {
            return HttpsCommunicator.executeGetRequest(buildRequestUrl(HOST, ENDPOINT_TICKER, null));
        }
    }

    private static String requestWithAuthorization(final String endpoint, final String requestParams) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");
        GateUtils.checkParamNotNull(requestParams, "requestParams");

        StringBuilder urlString = new StringBuilder(HOST);
        urlString.append(endpoint);
        if (!requestParams.isEmpty()) {
            urlString.append("?").append(requestParams);
        }
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", AUTH_HEADER_VALUE);
        return HttpsCommunicator.executeGetRequest(urlString.toString(), headers);
    }

    private static String requestWithAuthorization(final String endpoint) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");

        return requestWithAuthorization(endpoint, EMPTY_STRING);
    }

    private static String requestPostWithAuthorization(final String endpoint, final String requestParams) throws Exception {
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", AUTH_HEADER_VALUE);
        return HttpsCommunicator.executePostRequest(HOST + endpoint, requestParams, headers);
    }

}
