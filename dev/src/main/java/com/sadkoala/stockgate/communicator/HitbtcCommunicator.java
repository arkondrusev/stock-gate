package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import com.sadkoala.stockgate.GateUtils;

import java.util.HashMap;
import java.util.Map;

public class HitbtcCommunicator extends AbstractStockCommunicator {

    private static final String PRIVATE_KEY_VALUE = "38ae01062504c26ce0e3a295f25eb628";
    private static final String SECRET_KEY_VALUE = "be45b2caa23f600bfb0764ac5bd05fa0";

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
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", AUTH_HEADER_VALUE);
        return HttpsCommunicator.executeHttpsRequest("api.hitbtc.com/api/2/order?symbol=" + symbol, headers);
    }

}
