package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class BinanceAdapterTest {

    private static final String BTC_USDT_SYMBOL = "BTCUSDT";

    @Test
    public void testGetBtcPrice() throws Exception {
        BigDecimal btcUsd = BinanceAdapter.getBtcPrice();
        System.out.println("btcUsd = " + btcUsd.toPlainString());
    }

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> orders = BinanceAdapter.getOpenOrders(BTC_USDT_SYMBOL);
        System.out.println("open orders count = " + orders.size());
    }

    @Test
    public void testGetAssetBalance() throws Exception {
        BigDecimal balance = BinanceAdapter.getAssetBalanceFree("USDT");
        System.out.println("balance = " + balance.toPlainString());
    }

    @Test
    public void testGetOrderbook() throws Exception {
        Orderbook orderbook = BinanceAdapter.getOrderbook(BTC_USDT_SYMBOL, 1);
        System.out.println("orderbook size: ask - " + orderbook.getAsk().getList().size()
                + " bid - " + orderbook.getBid().getList().size());
    }

    @Test
    public void testPlaceOrder_GetOrder_CancelOrder() throws Exception {
        BigDecimal btcPrice = BinanceAdapter.getBtcPrice();

        Order order = BinanceAdapter.placeLimitOrder(BTC_USDT_SYMBOL, "BUY", new BigDecimal("0.002"), btcPrice.subtract(new BigDecimal("1000")));
        Assertions.assertTrue("NEW".equals(order.getStatus()));

        order = BinanceAdapter.getOrder(order.getSymbol(), order.getOrderId());
        Assertions.assertTrue("NEW".equals(order.getStatus()));

        String status = BinanceAdapter.cancelOrder(order.getSymbol(), order.getOrderId());
        Assertions.assertTrue("CANCELED".equals(status));
    }

}
