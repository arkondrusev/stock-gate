package com.sadkoala.stockgate.communicator;

import org.junit.jupiter.api.Test;

public class HitbtcCommunicatorTest {

    private static final String BTC_USD_SYMBOL = "BTCUSD";

    @Test
    public void testRequestOpenOrders() throws Exception {
        String response = HitbtcCommunicator.requestOpenOrders(BTC_USD_SYMBOL);
        System.out.println(response);
    }

    @Test
    public void testRequestTradingBalance() throws Exception {
        String response = HitbtcCommunicator.requestTradingBalance();
        System.out.println(response);
    }

    @Test
    public void testRequestOrderbook() throws Exception {
        String response = HitbtcCommunicator.requestOrderbook(BTC_USD_SYMBOL, 5);
        System.out.println(response);
    }


}
