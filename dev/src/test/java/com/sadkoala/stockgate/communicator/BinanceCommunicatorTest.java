package com.sadkoala.stockgate.communicator;

import com.sadkoala.stockgate.adapter.BinanceAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BinanceCommunicatorTest {

    private static final String SYMBOL_BTC_USDT = "BTCUSDT";
    private static final String PAIR_LINK_USDT = "LINKUSDT";
    public static final String TRADE_SIDE_BUY = "BUY";
    public static final String ORDER_TYPE_LIMIT = "LIMIT";

    @Test
    public void testRequestLatestSymbolPrice() throws Exception {
        System.out.println(BinanceCommunicator.requestLatestSymbolPrice(SYMBOL_BTC_USDT));
    }

    @Test
    public void testRequestOpenOrders() throws Exception {
        System.out.println(BinanceCommunicator.requestOpenOrders(SYMBOL_BTC_USDT));
    }

    @Test
    public void testRequestAccountInfo() throws Exception {
        System.out.println(BinanceCommunicator.requestAccountInfo());
    }

    @Test
    public void testRequestOrderBook() throws Exception {
        System.out.println(BinanceCommunicator.requestOrderbook(SYMBOL_BTC_USDT));
    }

    @Test
    public void testRequestNewOrder_CheckOrder_CancelOrder() throws Exception {
        BigDecimal btcPrice = BinanceAdapter.getBtcPrice();

        String resp = BinanceCommunicator.requestNewOrder(SYMBOL_BTC_USDT, TRADE_SIDE_BUY, ORDER_TYPE_LIMIT, new BigDecimal("0.002"), btcPrice.subtract(new BigDecimal("1000")));
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"status\":\"NEW\""));
        String orderId = resp.substring(resp.indexOf("\"clientOrderId\":\"")+"\"clientOrderId\":\"".length(),resp.indexOf("\",\"transactTime\""));

        resp = BinanceCommunicator.requestOrder(SYMBOL_BTC_USDT, orderId);
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"status\":\"NEW\""));


        resp = BinanceCommunicator.requestCancelOrder(SYMBOL_BTC_USDT, orderId);
        System.out.println(resp);
        Assertions.assertTrue(resp.contains("\"status\":\"CANCELED\""));
    }

    @Test
    public void testRequestTradeList() throws Exception {
        //BinanceCommunicator.requestTradeList();
    }

    @Test
    public void testRequestCreateOCO() throws Exception {
        BigDecimal linkPrice = BinanceAdapter.getOrderbook(PAIR_LINK_USDT, 1).getBid().getList().get(0).getPrice();
        BigDecimal buyPrice = linkPrice.multiply(new BigDecimal("0.5"));
        BigDecimal sellPrice = linkPrice.multiply(new BigDecimal("2"));

        String resp = BinanceCommunicator.requestCreateOCO(PAIR_LINK_USDT, TRADE_SIDE_BUY, new BigDecimal("0.001"), buyPrice, sellPrice);
        System.out.println(resp);
    }

    /*
    @Test
    public void test() throws Exception {
        Order order = BinanceAdapter.placeLimitOrder(SYMBOL_BTC_USDT, "BUY", new BigDecimal("0.002"), new BigDecimal("14000"));
        String resp = BinanceCommunicator.requestOrder(SYMBOL_BTC_USDT, order.getOrderId());
        System.out.println(resp);
    }

    @Test
    public void test1() throws Exception {
        String resp = BinanceCommunicator.requestOrder(SYMBOL_BTC_USDT, "HUrheXcURrxxlWRgcxIkwT");
        System.out.println(resp);
    }
    */

}
