package com.sadkoala.stockgate.stockadapter;

import com.sadkoala.stockgate.parser.model.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BinanceStockAdapterTest {

    private static BinanceStockAdapter adapter = AbstractStockAdapter.getBinanceStockAdapter();

    public static final String SYMBOL_LINK_USDT = "LINKUSDT";

    @Test
    public void testGetSymbolPrice() throws Exception {
        System.out.println("last price: " + adapter.getSymbolTicker(SYMBOL_LINK_USDT).lastPrice.toPlainString());
    }

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> openOrders = adapter.getOpenOrders(SYMBOL_LINK_USDT);
        System.out.println(openOrders.size() + " open orders for pair " + SYMBOL_LINK_USDT);
    }

}
