package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class OkexAdapterTest {

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> orders = OkexAdapter.getOpenOrders("btc-usdt");
        System.out.println("open orders count = " + orders.size());
    }

    @Test
    public void testGetBalanceAvailable() throws Exception {
        BigDecimal balance = OkexAdapter.getBalanceAvailable("USDT");
        System.out.println("balance = " + balance.toPlainString());
    }

}
