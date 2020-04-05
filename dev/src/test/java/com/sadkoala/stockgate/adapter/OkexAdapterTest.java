package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OkexAdapterTest {

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> orders = OkexAdapter.getOpenOrders("btc-usdt");
        System.out.println("open orders count = " + orders.size());
    }

}
