package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.communicator.BinanceCommunicator;
import com.sadkoala.stockgate.parser.BinanceParser;

import java.math.BigDecimal;

public class BinanceAdapter extends AbstractStockAdapter implements IStockAdapter {

    public static BigDecimal getBtcPrice() throws Exception {
        return new BigDecimal(BinanceParser.parseBtcPrice(BinanceCommunicator.requestLatestSymbolPrice("BTCUSDT")));
    }

}