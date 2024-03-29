package com.sadkoala.stockgate.stockadapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.adapter.HitbtcAdapter;
import com.sadkoala.stockgate.communicator.HitbtcCommunicator;
import com.sadkoala.stockgate.parser.HitbtcParser;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Ticker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HitbtcStockAdapter implements IStockAdapter {

    @Override
    public String getStockName() {
        return "Hitbtc";
    }

    @Override
    public List<Order> getOpenOrders(String symbol) throws Exception {
        return HitbtcAdapter.getOpenOrders(symbol);
    }

    @Override
    public void placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception {
        HitbtcAdapter.placeMarketOrder(symbol, side, qty);
    }

    @Override
    public String cancelOrder(String symbol, String orderId) throws Exception {
        return HitbtcAdapter.cancelOrder(orderId);
    }

    @Override
    public Ticker getSymbolTicker(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        List<String> symbols = new ArrayList<>();
        symbols.add(symbol);
        return HitbtcParser.parseTickers(HitbtcCommunicator.requestSymbolsTickers(symbols)).get(0);
    }

}