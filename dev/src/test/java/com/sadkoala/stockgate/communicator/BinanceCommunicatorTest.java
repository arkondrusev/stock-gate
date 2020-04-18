package com.sadkoala.stockgate.communicator;

import com.sadkoala.stockgate.adapter.BinanceAdapter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
    public void testRequestNewOrderAndCancelOrder() throws Exception {
        BigDecimal btcPrice = BinanceAdapter.getBtcPrice();

        String resp = BinanceCommunicator.requestNewOrder(BTC_USDT_SYMBOL, "BUY", "LIMIT", new BigDecimal("0.002"), btcPrice.subtract(new BigDecimal("1000")));
        System.out.println(resp);
        Long orderId = Long.valueOf(resp.substring(resp.indexOf("\"orderId\":")+"\"orderId\":".length(),resp.indexOf(",\"orderListId\"")));

        System.out.println(BinanceCommunicator.requestCancelOrder(BTC_USDT_SYMBOL, orderId));
    }

}
