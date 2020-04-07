package com.sadkoala.stockgate.communicator;

import org.junit.jupiter.api.Test;

public class HitbtcCommunicatorTest {

    @Test
    public void testRequestOpenOrders() throws Exception {
        String response = HitbtcCommunicator.requestOpenOrders("BTCUSD");
        System.out.println(response);
    }

    @Test
    public void testRequestTradingBalance() throws Exception {
        String response = HitbtcCommunicator.requestTradingBalance();
        System.out.println(response);
    }

}
