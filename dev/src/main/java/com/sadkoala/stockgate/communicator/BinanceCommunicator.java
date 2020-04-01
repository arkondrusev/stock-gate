package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.ParameterUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class BinanceCommunicator extends AbstractStockCommunicator {

    private static final String SECRET_KEY_VALUE = "5Pffrmzjxjalryu4WenfVXiaCSGCiDGSgtknvCrPVb6ew0UeIsh7oH5bYicU3sx1";
    private static final String API_KEY_VALUE = "0Wm7F0NYk38njHwhmPzcxT14A5cbGs5Ri5d7TnvKJbizulrp5EIDHoA0M6FYunuk";

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
        ParameterUtils.checkParamEmpty(symbol, "symbol");
        String urlString = "api.binance.com/api/v3/ticker/price" + "?symbol=" + symbol;
        return HttpsCommunicator.executeHttpsRequest(urlString);
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
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Parameter symbol is mandatory");
        }
        String urlString = "symbol=" + symbol + "&recvWindow=60000" + "&timestamp=" + getTimestamp();
        urlString = "api.binance.com/api/v3/openOrders?" + urlString + "&signature=" + getSignature(urlString);
        Map<String,String> headers = new HashMap<>();
        headers.put("X-MBX-APIKEY", API_KEY_VALUE);
        return HttpsCommunicator.executeHttpsRequest(urlString, headers);
    }

    private static String getSignature(String message) throws NoSuchAlgorithmException, InvalidKeyException {
        //log.debug("getSignature. Start...");

        String hmacSha256 = "HmacSHA256";
        Mac sha256_HMAC = Mac.getInstance(hmacSha256);
        SecretKeySpec secret_key = new SecretKeySpec(SECRET_KEY_VALUE.getBytes(StandardCharsets.UTF_8), hmacSha256);
        sha256_HMAC.init(secret_key);
        String hash = bytesToHex(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));

        //log.debug("returning value. result=" + hash);
        //log.debug("getSignature. Finish...");
        return hash;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static long getTimestamp() {
        return System.currentTimeMillis();
    }

}
