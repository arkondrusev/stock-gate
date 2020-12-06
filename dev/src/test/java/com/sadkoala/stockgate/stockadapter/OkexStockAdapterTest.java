package com.sadkoala.stockgate.stockadapter;

import org.junit.jupiter.api.Test;

public class OkexStockAdapterTest {

    private static OkexStockAdapter adapter = AbstractStockAdapter.getOkexStockAdapter();

    public static final String SYMBOL_LINK_USDT = "LINK-USDT";

    @Test
    public void testGetSymbolTicker() throws Exception {
        System.out.println("last price: " + adapter.getSymbolTicker(SYMBOL_LINK_USDT).lastPrice.toPlainString());
    }

}
