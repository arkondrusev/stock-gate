package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class HitbtcAdapterTest {

    private static final String BTC_USD_SYMBOL = "BTCUSD";

    @Test
    public void testGetOpenOrders() throws Exception {
        List<Order> orders = HitbtcAdapter.getOpenOrders(BTC_USD_SYMBOL);
        System.out.println("open orders count = " + orders.size());
    }

    @Test
    public void testGetCurrencyBalanceAvailable() throws Exception {
        BigDecimal balance = HitbtcAdapter.getCurrencyBalanceAvailaible("USD");
        System.out.println("balance = " + balance.toPlainString());
    }

    @Test
    public void testGetOrderbook() throws Exception {
        Orderbook btcusd = HitbtcAdapter.getOrderbook(BTC_USD_SYMBOL, 1);
        System.out.println("orderbook : ask list - " + btcusd.getAsk().getList().size()
                + " bid list - " + btcusd.getBid().getList().size());
    }

    @Test
    public void testPlaceOrder_CheckOrderStatus_CancelOrder() throws Exception {
        BigDecimal btcPrice = HitbtcAdapter.getOrderbook(BTC_USD_SYMBOL, 1).getBid().getList().get(0).getPrice();

        Order order = HitbtcAdapter.placeLimitOrder(BTC_USD_SYMBOL, "buy", new BigDecimal("0.002"), btcPrice.subtract(new BigDecimal("1000")));
        Assertions.assertTrue("new".equals(order.getStatus()));

        String status = HitbtcAdapter.checkOrderStatus(order.getOrderId());
        Assertions.assertTrue("new".equals(status));

        status = HitbtcAdapter.cancelOrder(order.getOrderId());
        Assertions.assertTrue("canceled".equals(status));
    }

    @Test
    public void testGetSymbolPrice() throws Exception {
        BigDecimal price = HitbtcAdapter.getSymbolPrice(BTC_USD_SYMBOL);
        System.out.println("btcusd price - " + price.toPlainString());
    }

}
