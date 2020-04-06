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
        GateUtils.checkParamEmpty(symbol, "symbol");
        String requestPath = "/api/spot/v3/orders_pending?instrument_id=" + symbol;
        String timestamp = getUnixTime();
        String urlString = "www.okex.com" + requestPath;

        Map<String,String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json; charset=UTF-8");
//        headers.put("Accept", "application/json");
        headers.put("OK-ACCESS-KEY", API_KEY_VALUE);
        headers.put("OK-ACCESS-SIGN", makeSign(timestamp + "GET" + requestPath));
        headers.put("OK-ACCESS-TIMESTAMP", timestamp);
        headers.put("OK-ACCESS-PASSPHRASE", PASSPHRASE_VALUE);
        return HttpsCommunicator.executeHttpsRequest(urlString, headers);
    }

    private static String makeSign(String textToSign) throws InvalidKeyException, NoSuchAlgorithmException {
        return toString(encodeBase64(encodeHmac256(toByteArr(textToSign), toByteArr(SECRET_KEY_VALUE))));
    }

    /**
     * UNIX timestamp ISO 8601 rule eg: 2018-02-03T05:34:14.110Z
     */
    private static String getUnixTime() {
        StringBuilder nowStr = new StringBuilder(Instant.now().toString());
        // Instant.toString в windows и linux могут давать разные результаты, поэтому приводим к нужному нам текстовому формату принудительно
        return new StringBuilder().append(nowStr.substring(0,nowStr.lastIndexOf("."))).append(nowStr.substring(nowStr.lastIndexOf(".")).substring(0,4)).append(nowStr.substring(nowStr.length()-1)).toString();
    }

}
