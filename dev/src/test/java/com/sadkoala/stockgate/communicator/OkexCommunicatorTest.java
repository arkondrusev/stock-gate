package com.sadkoala.stockgate.communicator;

import com.sadkoala.stockgate.adapter.OkexAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OkexCommunicatorTest {

    private static final String BTC_USDT_SYMBOL = "btc-usdt";

    @Test
    public void testRequestOpenOrders() throws Exception {
        String orders = OkexCommunicator.requestOpenOrders(BTC_USDT_SYMBOL);
        System.out.println(orders);
    }

    @Test
    public void testRequestBalances() throws Exception {
        String balances = OkexCommunicator.requestBalances();
        System.out.println(balances);
    }

    @Test
    public void testRequestOrderbook() throws Exception {
        String response = OkexCommunicator.requestOrderbook(BTC_USDT_SYMBOL, 3, null);
        System.out.println(response);
    }

    @Test
    public void testRequestNewOrder_CheckOrder_CancelOrder() throws Exception {
        BigDecimal btcPrice = OkexAdapter.getOrderbook(BTC_USDT_SYMBOL, 1).getBid().getList().get(0).getPrice();

        String resp = OkexCommunicator.requestNewOrder(BTC_USDT_SYMBOL, "buy", "limit", new BigDecimal("0.002"), btcPrice.subtract(new BigDecimal("1000")));
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"result\":true"));
        String orderId = resp.substring(resp.indexOf("\"order_id\":\"")+"\"order_id\":\"".length(),resp.indexOf("\",\"result\""));

        resp = OkexCommunicator.requestCheckOrderStatus(BTC_USDT_SYMBOL, orderId);
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"status\":\"open\""));

        resp = OkexCommunicator.requestCancelOrder(BTC_USDT_SYMBOL, orderId);
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"result\":true"));
    }

    @Test
    public void testRequestAlgoOrders() throws Exception {
        String orders = OkexCommunicator.requestAlgoOrders(BTC_USDT_SYMBOL, "trigger order", "Pending", null);
        System.out.println(orders);
    }

    @Test
    public void testRequestSymbolTicker() throws Exception {
        String resp = OkexCommunicator.requestSymbolTicker(BTC_USDT_SYMBOL);
        System.out.println("resp=" + resp);
    }

}
