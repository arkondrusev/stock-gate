package com.sadkoala.stockgate.stockadapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.adapter.OkexAdapter;
import com.sadkoala.stockgate.communicator.OkexCommunicator;
import com.sadkoala.stockgate.parser.OkexParser;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Ticker;

import java.math.BigDecimal;
import java.util.List;

public class OkexStockAdapter implements IStockAdapter {

    @Override
    public String getStockName() {
        return "Okex";
    }

    @Override
    public List<Order> getOpenOrders(String symbol) throws Exception {
        return OkexAdapter.getOpenOrders(symbol);
    }

    @Override
    public void placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception {
        OkexAdapter.placeMarketOrder(symbol, side, qty);
    }

    @Override
    public String cancelOrder(String symbol, String orderId) throws Exception {
        return OkexAdapter.cancelOrder(symbol, orderId);
    }

    @Override
    public Ticker getSymbolTicker(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return OkexParser.parseTicker(OkexCommunicator.requestSymbolTicker(symbol));
    }

}
