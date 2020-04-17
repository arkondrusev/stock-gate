package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.GateUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class BinanceCommunicator extends AbstractStockCommunicator {

    private static final String SECRET_KEY_VALUE = "5Pffrmzjxjalryu4WenfVXiaCSGCiDGSgtknvCrPVb6ew0UeIsh7oH5bYicU3sx1";
    private static final String API_KEY_VALUE = "0Wm7F0NYk38njHwhmPzcxT14A5cbGs5Ri5d7TnvKJbizulrp5EIDHoA0M6FYunuk";

    private static final String HOST = "api.binance.com";
    public static final String HEADER_MBX_APIKEY = "X-MBX-APIKEY";

    /**
     * ### Symbol price ticker
     * ```
     * GET /api/v3/ticker/price
     * ```
     * Latest price for a symbol or symbols.
     *
     * **Weight:**
     * 1 for a single symbol; **2** when the symbol parameter is omitted
     *
     * **Parameters:**
     *
     * Name | Type | Mandatory | Description
     * ------------ | ------------ | ------------ | ------------
     * symbol | STRING | NO |
     *
     * * If the symbol is not sent, prices for all symbols will be returned in an array.
     *
     * **Response:**
     * ```javascript
     * {
     *   "symbol": "LTCBTC",
     *   "price": "4.00000200"
     * }
     * ```
     * OR
     * ```javascript
     * [
     *   {
     *     "symbol": "LTCBTC",
     *     "price": "4.00000200"
     *   },
     *   {
     *     "symbol": "ETHBTC",
     *     "price": "0.07946600"
     *   }
     * ]
     * ```
     *
     * #symbol - example BTCUSD
     *
     */
    public static String requestLatestSymbolPrice(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return HttpsCommunicator.executeHttpsRequest(HOST + "/api/v3/ticker/price?symbol=" + symbol);
    }

    /**
     * ### Current open orders (USER_DATA)
     * ```
     * GET /api/v3/openOrders  (HMAC SHA256)
     * ```
     * Get all open orders on a symbol. **Careful** when accessing this with no symbol.
     *
     * **Weight:**
     * 1 for a single symbol; **40** when the symbol parameter is omitted
     *
     * **Parameters:**
     *
     * Name | Type | Mandatory | Description
     * ------------ | ------------ | ------------ | ------------
     * symbol | STRING | NO |
     * recvWindow | LONG | NO | The value cannot be greater than ```60000```
     * timestamp | LONG | YES |
     *
     * * If the symbol is not sent, orders for all symbols will be returned in an array.
     *
     * **Response:**
     * ```javascript
     * [
     *   {
     *     "symbol": "LTCBTC",
     *     "orderId": 1,
     *     "orderListId": -1, //Unless OCO, the value will always be -1
     *     "clientOrderId": "myOrder1",
     *     "price": "0.1",
     *     "origQty": "1.0",
     *     "executedQty": "0.0",
     *     "cummulativeQuoteQty": "0.0",
     *     "status": "NEW",
     *     "timeInForce": "GTC",
     *     "type": "LIMIT",
     *     "side": "BUY",
     *     "stopPrice": "0.0",
     *     "icebergQty": "0.0",
     *     "time": 1499827319559,
     *     "updateTime": 1499827319559,
     *     "isWorking": true,
     *     "origQuoteOrderQty": "0.000000"
     *   }
     * ]
     * ```
     */
    public static String requestOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return requestWithAuthorization("/api/v3/openOrders", "symbol=" + symbol + "&" + prepareCommonParams());
    }

    /**
     * ### Account information (USER_DATA)
     * ```
     * GET /api/v3/account (HMAC SHA256)
     * ```
     * Get current account information.
     *
     * **Weight:**
     * 5
     *
     * **Parameters:**
     *
     * Name | Type | Mandatory | Description
     * ------------ | ------------ | ------------ | ------------
     * recvWindow | LONG | NO | The value cannot be greater than ```60000```
     * timestamp | LONG | YES |
     *
     * **Response:**
     * ```javascript
     * {
     *   "makerCommission": 15,
     *   "takerCommission": 15,
     *   "buyerCommission": 0,
     *   "sellerCommission": 0,
     *   "canTrade": true,
     *   "canWithdraw": true,
     *   "canDeposit": true,
     *   "updateTime": 123456789,
     *   "accountType": "SPOT",
     *   "balances": [
     *     {
     *       "asset": "BTC",
     *       "free": "4723846.89208129",
     *       "locked": "0.00000000"
     *     },
     *     {
     *       "asset": "LTC",
     *       "free": "4763368.68006011",
     *       "locked": "0.00000000"
     *     }
     *   ]
     * }
     * ```
     */
    public static String requestAccountInfo() throws Exception {
        return requestWithAuthorization("/api/v3/account", prepareCommonParams());
    }

    /**
     * ## Market Data endpoints
     * ### Order book
     * ```
     * GET /api/v3/depth
     * ```
     *
     * **Weight:**
     * Adjusted based on the limit:
     *
     *
     * Limit | Weight
     * ------------ | ------------
     * 5, 10, 20, 50, 100 | 1
     * 500 | 5
     * 1000 | 10
     * 5000| 50
     *
     *
     * **Parameters:**
     *
     * Name | Type | Mandatory | Description
     * ------------ | ------------ | ------------ | ------------
     * symbol | STRING | YES |
     * limit | INT | NO | Default 100; max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     *
     * **Response:**
     * ```javascript
     * {
     *   "lastUpdateId": 1027024,
     *   "bids": [
     *     [
     *       "4.00000000",     // PRICE
     *       "431.00000000"    // QTY
     *     ]
     *   ],
     *   "asks": [
     *     [
     *       "4.00000200",
     *       "12.00000000"
     *     ]
     *   ]
     * }
     * ```
     */
    public static String requestOrderbook(final String symbol, final int limit) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        if (limit != 0) {
            checkParamLimitValid(limit);
        }

        String urlString = HOST + "/api/v3/depth" + "?" + "symbol=" + symbol;
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

    private static String prepareCommonParams() {
        return "recvWindow=60000&timestamp=" + getTimestamp();
    }

    /**
     * endpoint - url part after host name and before "?"
     * requestParams - url params after "?" (without signature)
     */
    private static String requestWithAuthorization(String endpoint, String requestParams) throws Exception {
        String urlString = HOST + endpoint + "?" + requestParams + "&signature=" + makeSignature(requestParams);
        Map<String,String> headers = new HashMap<>();
        headers.put(HEADER_MBX_APIKEY, API_KEY_VALUE);
        return HttpsCommunicator.executeGetRequest(urlString, headers);
    }

    private static String requestWithAuthorization(String endpoint) throws Exception {
        GateUtils.checkParamNotEmpty(endpoint, "endpoint");

        return requestWithAuthorization(endpoint, EMPTY_STRING);
    }

    private static long getTimestamp() {
        return System.currentTimeMillis();
    }

    private static String makeSignature(String text) throws InvalidKeyException, NoSuchAlgorithmException {
        return new String(bytesToHex(encodeHmac256(toByteArr(text), toByteArr(SECRET_KEY_VALUE))));
    }

}
