package com.sadkoala.stockgate.communicator;

import com.sadkoala.stockgate.adapter.HitbtcAdapter;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testSymbolsTickers() throws Exception {
        System.out.println(HitbtcCommunicator.requestSymbolsTickers(null));

        List<String> symbols = new ArrayList<>();
        symbols.add(BTC_USD_SYMBOL);
        System.out.println(HitbtcCommunicator.requestSymbolsTickers(symbols));

        symbols.add("ETHBTC");
        System.out.println(HitbtcCommunicator.requestSymbolsTickers(symbols));
    }

    @Test
    public void testRequestOrderHistory() throws Exception {
        BigDecimal btcPrice = HitbtcAdapter.getSymbolPrice(BTC_USD_SYMBOL);

        Order order = HitbtcAdapter.placeLimitOrder(BTC_USD_SYMBOL, "buy",
                new BigDecimal("0.002"),
                btcPrice.subtract(new BigDecimal("1000")));
        System.out.println(order.getOrderId());
        HitbtcAdapter.cancelOrder(order.getOrderId());

        Thread.sleep(5000);

        String resp = HitbtcCommunicator.requestOrderHistory(order.getOrderId());
        Assertions.assertTrue(resp.contains(order.getOrderId()));
    }

}
