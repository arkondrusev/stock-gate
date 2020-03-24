package com.sadkoala.stockgate.communicator;

import org.junit.jupiter.api.Test;

public class BinanceCommunicatorTest {

    @Test
    public void testRequestOpenOrders() throws Exception {
        System.out.println(BinanceCommunicator.requestOpenOrders("BTCUSDT"));
    }

}
