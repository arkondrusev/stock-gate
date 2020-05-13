package com.sadkoala.stockgate.stockadapter;

import com.sadkoala.stockgate.adapter.HitbtcAdapter;
import com.sadkoala.stockgate.parser.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class HitbtcStockAdapter implements IStockAdapter {

    @Override
    public List<Order> getOpenOrders(String symbol) throws Exception {
        return HitbtcAdapter.getOpenOrders(symbol);
    }

    @Override
    public void placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception {
        HitbtcAdapter.placeMarketOrder(symbol, side, qty);
    }

}