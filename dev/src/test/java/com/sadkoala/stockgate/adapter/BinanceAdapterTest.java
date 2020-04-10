package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class BinanceAdapterTest {

    private static final String BTC_USDT_SYMBOL = "BTCUSDT";

    @Test
    public void testGetBtcPrice() throws Exception {
        BigDecimal btcUsd = BinanceAdapter.getBtcPrice();
        System.out.println("btcUsd = " + btcUsd.toPlainString());
    }

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> orders = BinanceAdapter.getOpenOrders(BTC_USDT_SYMBOL);
        System.out.println("open orders count = " + orders.size());
    }

    @Test
    public void testGetAssetBalance() throws Exception {
        BigDecimal balance = BinanceAdapter.getAssetBalanceFree("USDT");
        System.out.println("balance = " + balance.toPlainString());
    }

    @Test
    public void testGetOrderbook() throws Exception {
        Orderbook orderbook = BinanceAdapter.getOrderbook(BTC_USDT_SYMBOL, 1);
        System.out.println("orderbook size: ask - " + orderbook.getAsk().getList().size()
                + " bid - " + orderbook.getBid().getList().size());
    }

}
