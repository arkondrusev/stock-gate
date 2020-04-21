package com.sadkoala.stockgate.communicator;

import com.sadkoala.stockgate.adapter.OkexAdapter;
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
//        Assertions.assertTrue(resp.contains("\"status\":\"NEW\""));
//        String orderId = resp.substring(resp.indexOf("\"clientOrderId\":\"")+"\"clientOrderId\":\"".length(),resp.indexOf("\",\"transactTime\""));

//        resp = BinanceCommunicator.requestCheckOrderStatus(BTC_USDT_SYMBOL, orderId);
//        System.out.println(resp);
//        Assertions.assertTrue(resp.contains("\"status\":\"NEW\""));


//        resp = BinanceCommunicator.requestCancelOrder(BTC_USDT_SYMBOL, orderId);
//        System.out.println(resp);
//        Assertions.assertTrue(resp.contains("\"status\":\"CANCELED\""));
    }

//    @Test
//    public void testMakeContent() throws JsonProcessingException {
//        System.out.println(OkexCommunicator.makeNewOrderRequestContent(BTC_USDT_SYMBOL, "buy", "limit", "0.002", "7000"));
//    }

}
