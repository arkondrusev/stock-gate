package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.ParameterUtils;
import com.sadkoala.stockgate.communicator.BinanceCommunicator;
import com.sadkoala.stockgate.parser.BinanceParser;
import com.sadkoala.stockgate.parser.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class BinanceAdapter extends AbstractStockAdapter implements IStockAdapter {

    public static BigDecimal getBtcPrice() throws Exception {
        return new BigDecimal(BinanceParser.parseBtcPrice(BinanceCommunicator.requestLatestSymbolPrice("BTCUSDT")));
    }

    public static List<Order> getOpenOrders(String symbol) throws Exception {
        ParameterUtils.checkParamEmpty(symbol, "symbol");
        return BinanceParser.parseOpenOrders(BinanceCommunicator.requestOpenOrders(symbol));
    }

}