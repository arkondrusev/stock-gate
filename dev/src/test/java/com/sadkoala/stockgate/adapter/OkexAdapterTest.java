package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;
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

    @Test
    public void testGetOrderbook() throws Exception {
        Orderbook btcusd = OkexAdapter.getOrderbook("BTC-USDT", 1);
        System.out.println("orderbook : ask list - " + btcusd.getAsk().getList().size()
                + " bid list - " + btcusd.getBid().getList().size());
    }

}
