package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.communicator.BinanceCommunicator;
import com.sadkoala.stockgate.parser.BinanceParser;

import java.math.BigDecimal;

public class BinanceAdapter extends AbstractStockAdapter implements IStockAdapter {

    private BinanceCommunicator communicator;
    private BinanceParser parser;

    public BinanceAdapter() {
        communicator = new BinanceCommunicator();
        parser = new BinanceParser();
    }

    public BigDecimal getBtcPrice() throws Exception {
        return new BigDecimal(parser.parseBtcPrice(communicator.requestLatestSymbolPrice("BTCUSDT")));
    }

}