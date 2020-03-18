package com.sadkoala.stockgate.communicator;

import com.sadkoala.httpscommunicator.HttpsCommunicator;
import org.apache.commons.lang3.StringUtils;

public class BinanceCommunicator extends AbstractStockCommunicator {

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
    public String requestLatestSymbolPrice(String symbol) throws Exception {
        String urlString = "api.binance.com/api/v3/ticker/price";
        if (StringUtils.isNotBlank(symbol)) {
            urlString = urlString + "?symbol=" + symbol;
        }
        return HttpsCommunicator.executeHttpsRequest(urlString);
    }

}
