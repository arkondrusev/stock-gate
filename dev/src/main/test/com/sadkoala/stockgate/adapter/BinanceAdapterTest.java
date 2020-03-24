package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class BinanceAdapterTest {

    @Test
    public void testGetBtcPrice() throws Exception {
        BigDecimal btcUsd = BinanceAdapter.getBtcPrice();
        System.out.println("btcUsd = " + btcUsd.toPlainString());
    }

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> orders = BinanceAdapter.getOpenOrders("BTCUSDT");
        System.out.println("open orders count = " + orders.size());
    }

}
