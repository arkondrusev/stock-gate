package com.sadkoala.stockgate.parser;

import java.io.IOException;

public class BinanceParser extends AbstractStockParser {

    /**
     * пример инпута {"symbol":"BTCUSDT","price":"5343.93000000"}
     */
    public String parseBtcPrice(final String jsonString) throws IOException {
        return mapper.readTree(jsonString).get("price").asText();
    }

}
