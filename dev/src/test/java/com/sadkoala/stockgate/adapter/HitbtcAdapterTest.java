package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class HitbtcAdapterTest {

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> orders = HitbtcAdapter.getOpenOrders("BTCUSD");
        System.out.println("open orders count = " + orders.size());
    }

    @Test
    public void testGetCurrencyBalanceAvailable() throws Exception {
        BigDecimal balance = HitbtcAdapter.getCurrencyBalanceAvailaible("USD");
        System.out.println("balance = " + balance.toPlainString());
    }

}
