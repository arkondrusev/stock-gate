package com.sadkoala.stockgate.stockadapter;

import com.sadkoala.stockgate.parser.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface IStockAdapter {

    List<Order> getOpenOrders(String symbol) throws Exception;

    void placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception;

}
