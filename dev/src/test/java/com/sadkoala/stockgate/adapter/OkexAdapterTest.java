package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class OkexAdapterTest {

    private static final String BTC_USDT_SYMBOL = "btc-usdt";

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> orders = OkexAdapter.getOpenOrders(BTC_USDT_SYMBOL);
        System.out.println("open orders count = " + orders.size());
    }

    @Test
    public void testGetBalanceAvailable() throws Exception {
        BigDecimal balance = OkexAdapter.getBalanceAvailable("USDT");
        System.out.println("balance = " + balance.toPlainString());
    }

    @Test
    public void testGetOrderbook() throws Exception {
        Orderbook btcusd = OkexAdapter.getOrderbook(BTC_USDT_SYMBOL, 1);
        System.out.println("orderbook : ask list - " + btcusd.getAsk().getList().size()
                + " bid list - " + btcusd.getBid().getList().size());
    }

    @Test
    public void testPlaceOrder_CheckOrderStatus_CancelOrder() throws Exception {
        BigDecimal btcPrice = OkexAdapter.getOrderbook(BTC_USDT_SYMBOL, 1).getBid().getList().get(0).getPrice();

        String orderId = OkexAdapter.placeLimitOrder(BTC_USDT_SYMBOL, "buy", new BigDecimal("0.002"), btcPrice.subtract(new BigDecimal("1000")));
        System.out.println("order id = " + orderId);

        Order order = OkexAdapter.checkOrderStatus(BTC_USDT_SYMBOL, orderId);
        System.out.println("order status = " + order.getStatus());
        Assertions.assertTrue("open".equals(order.getStatus()));

        String result = OkexAdapter.cancelOrder(BTC_USDT_SYMBOL, orderId);
        System.out.println("result = " + result);
        Assertions.assertTrue("true".equals(result));
    }

}
