package com.sadkoala.stockgate.stockadapter;

import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Ticker;

import java.math.BigDecimal;
import java.util.List;

public interface IStockAdapter {

    String getStockName();

    List<Order> getOpenOrders(String symbol) throws Exception;

    void placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception;

    String cancelOrder(String symbol, String orderId) throws Exception;

    Ticker getSymbolTicker(String symbol) throws Exception;

}
