package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.GateUtils;

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
        String urlString = "instrument_id=" + symbol;
        urlString = "/orders_pending?" + urlString;
        String timestamp = GateUtils.millisToISO8601Timestamp(0L);
        String textToSign = timestamp + "GET" + urlString;
        urlString = "www.okex.com/api/spot/v3/orders_pending?" + urlString;
        Map<String,String> headers = new HashMap<>();
        headers.put("OK-ACCESS-KEY", API_KEY_VALUE);
        headers.put("OK-ACCESS-SIGN", encodeHmac256(textToSign, SECRET_KEY_VALUE));
        headers.put("OK-ACCESS-TIMESTAMP", timestamp);
        headers.put("OK-ACCESS-PASSPHRASE", PASSPHRASE_VALUE);
        return HttpsCommunicator.executeHttpsRequest(urlString, headers);
    }

}
