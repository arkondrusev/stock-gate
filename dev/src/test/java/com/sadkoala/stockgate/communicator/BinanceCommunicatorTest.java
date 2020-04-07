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
        System.out.println(BinanceCommunicator.requestOrderBook(BTC_USDT_SYMBOL));
    }

}
