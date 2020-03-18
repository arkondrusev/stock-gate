package com.sadkoala.stockgate.communicator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractStockCommunicator {

    private final Logger log = LogManager.getLogger(AbstractStockCommunicator.class.getName());

    /*
    public static IStockCommunicator createCommunicator(String stockShortName) {
        if (STOCK_BINANCE_MARKER.equals(stockShortName)) {
            return new BinanceCommunicator();
        } else if (STOCK_HITBTC_MARKER.equals(stockShortName)) {
            return null;// new HitbtcCommunicator();
        } else if (STOCK_OKEX_MARKER.equals(stockShortName)) {
            return null;
        } else {
            throw new NotImplementedException(String.format("Нет имплементации коммуникатора для биржи [%s]", stockShortName));
        }
    }
    */

    public static String executeRequest() {
        return null;
    }

}
