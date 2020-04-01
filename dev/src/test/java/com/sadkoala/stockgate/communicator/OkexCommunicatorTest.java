package com.sadkoala.stockgate.communicator;

import org.junit.jupiter.api.Test;

public class OkexCommunicatorTest {

    @Test
    public void testRequestOpenOrders() throws Exception {
        String orders = OkexCommunicator.requestOpenOrders("btc-usdt");
        System.out.println(orders);
    }

}
