package com.sadkoala.stockgate.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractStockParser {

    protected static ObjectMapper mapper = new ObjectMapper();

    /*
    public static IStockParser createStockParser(String stockShortName) {
        Preconditions.checkNotNull(stockShortName);
        if (STOCK_BINANCE_MARKER.equals(stockShortName)) {
            return new BinanceParser();
        } else {
            throw new NotImplementedException(String.format("Нет имплементации парсера для биржи [%s]", stockShortName));
        }
    }
    */

}
