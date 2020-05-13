package com.sadkoala.stockgate.stockadapter;

import com.sadkoala.stockgate.adapter.BinanceAdapter;
import com.sadkoala.stockgate.parser.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class BinanceStockAdapter implements IStockAdapter {

    @Override
    public List<Order> getOpenOrders(String symbol) throws Exception {
        return BinanceAdapter.getOpenOrders(symbol);
    }

    @Override
    public void placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception {
        BinanceAdapter.placeMarketOrder(symbol, side, qty);
    }

}
