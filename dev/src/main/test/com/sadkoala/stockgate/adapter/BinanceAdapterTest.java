package com.sadkoala.stockgate.adapter;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BinanceAdapterTest {

    @Test
    public void testGetBtcPrice() throws Exception {
        BigDecimal btcUsd = new BinanceAdapter().getBtcPrice();
        System.out.println("btcUsd = " + btcUsd.toPlainString());
    }

}
