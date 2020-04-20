package com.sadkoala.stockgate.communicator;

import com.sadkoala.stockgate.adapter.HitbtcAdapter;
import com.sadkoala.stockgate.parser.model.Orderbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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

    @Test
    public void testRequestNewOrder_requestCheckOrderStatus_requestCancelOrder() throws Exception {
        Orderbook orderbook = HitbtcAdapter.getOrderbook(BTC_USD_SYMBOL, 1);

        String resp = HitbtcCommunicator.requestNewOrder(BTC_USD_SYMBOL, "buy", "limit", new BigDecimal("0.002"),
                orderbook.getBid().getList().get(0).getPrice().subtract(new BigDecimal("1000")));
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"status\":\"new\""));
        String orderId = resp.substring(resp.indexOf("\"clientOrderId\":\"")+"\"clientOrderId\":\"".length(), resp.indexOf("\","));

        resp = HitbtcCommunicator.requestCheckOrderStatus(orderId);
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"status\":\"new\""));

        resp = HitbtcCommunicator.requestCancelOrder(orderId);
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"status\":\"canceled\""));
    }

}
