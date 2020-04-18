package com.sadkoala.stockgate.communicator;

import org.junit.jupiter.api.Test;

public class BinanceCommunicatorTest {

    private static final String BTC_USDT_SYMBOL = "BTCUSDT";

    @Test
    public void testRequestLatestSymbolPrice() throws Exception {
        System.out.println(BinanceCommunicator.requestLatestSymbolPrice(BTC_USDT_SYMBOL));
    }

    @Test
    public void testRequestOpenOrders() throws Exception {
        System.out.println(BinanceCommunicator.requestOpenOrders(BTC_USDT_SYMBOL));
    }

    @Test
    public void testRequestAccountInfo() throws Exception {
        System.out.println(BinanceCommunicator.requestAccountInfo());
    }

    @Test
    public void testRequestOrderBook() throws Exception {
        System.out.println(BinanceCommunicator.requestOrderbook(BTC_USDT_SYMBOL));
    }

    @Test
    public void testRequestNewOrder() throws Exception {
//        System.out.println(BinanceCommunicator.requestNewOrder(BTC_USDT_SYMBOL, "BUY", "LIMIT", new BigDecimal("0.002"), new BigDecimal("6000")));
    }

}
