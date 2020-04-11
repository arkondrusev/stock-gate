package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.GateUtils;

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

    /**
     * Open Orders
     * This retrieves the list of your current open orders. Pagination is supported and the response is sorted with most recent first in reverse chronological order.
     *
     * Rate limit: 20 requests per 2 seconds
     * HTTP Request
     * GET/api/spot/v3/orders_pending
     *
     * Example Request
     * 2018-09-12T07:51:51.138ZGET/api/spot/v3/orders_pending?limit=2&instrument_id=btc-usdt&after=2500723297223680
     *
     * Parameters
     * Parameter	Type	Required	Description
     * instrument_id	String	Yes	Trading pair symbol
     * after	String	No	Pagination of data to return records earlier than the requested order_id.
     * before	String	No	Pagination of data to return records newer than the requested order_id.
     * limit	String	No	Number of results per request. The maximum is 100; the default is 100
     */
    public static String requestOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return requestWithAuthorization("/api/spot/v3/orders_pending", "instrument_id=" + symbol);
    }

    /**
     * Account Information
     * This retrieves the list of assets, (with non-zero balance), remaining balance, and amount available in the spot trading account.
     *
     * Rate limit: 20 requests per 2 seconds
     * HTTP Requests
     * GET/api/spot/v3/accounts
     *
     * Example Request
     * GET/api/spot/v3/accounts
     *
     * Return Parameters
     * Parameters	Parameters Types	Description
     * currency	String	Token symbol
     * balance	String	Remaining balance
     * id	String	Account ID
     * hold	String	Amount on hold (not available)
     * available	String	Available amount
     * Notes
     * After placing an order, the order amount will be put on hold. After you placed an order, the amount of the order will be put on hold. You will not be able to transfer or use in other orders until the order is completed or cancelled.
     */
    public static String requestBalances() throws Exception {
        return requestWithAuthorization("/api/spot/v3/accounts");
    }

    /**
     * Public - Order Book
     * Retrieve a trading pair's order book. Pagination is not supported here; the entire orderbook will be returned per request. This is publicly accessible without account authentication. WebSocket is recommended here.
     *
     * Rate limit: 20 requests per 2 seconds
     * HTTP Request
     * GET/api/spot/v3/instruments/<instrument_id>/book
     *
     * Example Request
     * GET/api/spot/v3/instruments/BTC-USDT/book?size=5&depth=0.2
     *
     * Parameters
     * Parameter	Type	Required	Description
     * size	String	No	Number of results per request. Maximum 200
     * depth	String	No	Aggregation of the order book. e.g . 0.1, 0.001
     * instrument_id	String	No	Trading pair symbol
     * Response
     * Parameter	Type	Description
     * asks	List<String>	Sell side depth
     * bids	List<String>	Buy side depth
     * timestamp	String	timestamp
     * Notes
     * Aggregation of the order book means that orders within a certain price range is combined and considered as one order cluster.
     *
     * When size is not passed in the parameters, one entry is returned; when size is 0, no entry is returned. The maximum size is 200. When requesting more than 200 entries, at most 200 entries are returned.
     */
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

    private static String requestWithAuthorization(final String endpoint, final String params) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");
        GateUtils.checkParamNotNull(params, "params");

        String requestPath = endpoint;
        if (!params.isBlank()) {
            requestPath = requestPath + "?" + params;
        }
        String timestamp = getUnixTime();
        Map<String,String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json; charset=UTF-8");
//        headers.put("Accept", "application/json");
        headers.put("OK-ACCESS-KEY", API_KEY_VALUE);
        headers.put("OK-ACCESS-SIGN", makeSign(timestamp + "GET" + requestPath));
        headers.put("OK-ACCESS-TIMESTAMP", timestamp);
        headers.put("OK-ACCESS-PASSPHRASE", PASSPHRASE_VALUE);
        return HttpsCommunicator.executeHttpsRequest(HOST + requestPath, headers);
    }

    private static String requestWithAuthorization(final String endpoint) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");

        return requestWithAuthorization(endpoint, EMPTY_STRING);
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
