package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.GateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HitbtcCommunicator extends AbstractStockCommunicator {

    private static final String PRIVATE_KEY_VALUE = "38ae01062504c26ce0e3a295f25eb628";
    private static final String SECRET_KEY_VALUE = "be45b2caa23f600bfb0764ac5bd05fa0";

    private static final String HOST = "api.hitbtc.com";

    private static final String AUTH_HEADER_VALUE = "Basic "
            + toString(encodeBase64(toByteArr(PRIVATE_KEY_VALUE + ":" + SECRET_KEY_VALUE)));

    /**
     * #### Get Active orders
     *
     * `GET /api/2/order`
     *
     * Return array of active orders.
     *
     * Parameters:
     *
     * | Name | Type | Description |
     * |:---|:---:|:---|
     * | symbol | String | Optional parameter to filter active orders by symbol |
     *
     * Response: Array of orders
     *
     * Example response:
     * ```json
     * [
     *   {
     *     "id": 840450210,
     *     "clientOrderId": "c1837634ef81472a9cd13c81e7b91401",
     *     "symbol": "ETHBTC",
     *     "side": "buy",
     *     "status": "partiallyFilled",
     *     "type": "limit",
     *     "timeInForce": "GTC",
     *     "quantity": "0.020",
     *     "price": "0.046001",
     *     "cumQuantity": "0.005",
     *     "createdAt": "2017-05-12T17:17:57.437Z",
     *     "updatedAt": "2017-05-12T17:18:08.610Z"
     *   }
     * ]
     * ```
     */
    public static String requestOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return requestWithAuthorization("/api/2/order", "symbol=" + symbol);
    }

    /**
     * ### Trading Balance
     *
     * `GET /api/2/trading/balance`
     *
     * Responses:
     *
     * | Name | Type | Description |
     * |:---|:---:|:---|
     * | currency | String |  |
     * | available | Number | Amount available for trading or transfer to main account |
     * | reserved | Number | Amount reserved for active orders or incomplete transfers to main account |
     *
     * Example response:
     * ```json
     *     [
     *       {
     *         "currency": "ETH",
     *         "available": "10.000000000",
     *         "reserved": "0.560000000"
     *       },
     *       {
     *         "currency": "BTC",
     *         "available": "0.010205869",
     *         "reserved": "0"
     *       }
     *     ]
     * ```
     */
    public static String requestTradingBalance() throws Exception {
        return requestWithAuthorization("/api/2/trading/balance");
    }

    /**
     * #### Orderbook
     *
     * `GET /api/2/public/orderbook`
     *
     * An Order Book is an electronic list of buy and sell orders for a specific symbol, structured by price level.
     *
     * You can optionally use comma-separated list of symbols. If it is not provided, null or empty, the request returns an Order Book for all symbols.
     *
     * Requires no API key Access Rights.
     *
     * Parameters:
     *
     * Name	Type	Description
     * limit	Number	Limit of Order Book levels
     * Default value: 100
     * Set 0 to view full list of Order Book levels.
     * symbols	String	Comma-separated list of symbol codes. Optional parameter
     * Responses:
     *
     * Name	Type	Description
     * ask	Array	Ask side array of levels
     * bid	Array	Bid side array of levels
     * size	Number	Total volume of orders with the specified price
     * price	Number	Price level
     *
     * ```
     */
    public static String requestOrderbook(List<String> symbols, int limit) throws Exception {
        StringBuilder urlString = new StringBuilder(HOST);
        urlString.append("/api/2/public/orderbook");
        StringBuilder symbolsList = new StringBuilder();
        int i = 0;
        for (String symbol : symbols) {
            symbolsList.append(symbol);
            if (symbols.size()-1 > i) { // if current symbol not last
                symbolsList.append(",");
            }
        }
        String symbolsParam = symbolsList.toString();
        if (!symbolsParam.isBlank()) {
            urlString.append("?").append("symbols=").append(symbolsList);
        }
        if (limit >=0) {
            urlString.append("&").append("limit=").append(limit);
        }
        return HttpsCommunicator.executeHttpsRequest(urlString.toString());
    }

    public static String requestOrderbook(String symbol, int limit) throws Exception {
        List<String> symbols = new ArrayList<>();
        symbols.add(symbol);
        return requestOrderbook(symbols, limit);
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
        return HttpsCommunicator.executeHttpsRequest(urlString.toString(), headers);
    }

    private static String requestWithAuthorization(final String endpoint) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");

        return requestWithAuthorization(endpoint, EMPTY_STRING);
    }

}
