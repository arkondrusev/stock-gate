package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.communicator.OkexCommunicator;
import com.sadkoala.stockgate.parser.OkexParser;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;

import java.math.BigDecimal;
import java.util.List;

public class OkexAdapter extends AbstractStockAdapter {

    public static List<Order> getOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return OkexParser.parseOpenOrders(OkexCommunicator.requestOpenOrders(symbol));
    }

    public static BigDecimal getBalanceAvailable(final String currency) throws Exception {
        GateUtils.checkParamNotEmpty(currency, "currency");

        return OkexParser.parseBalanceAvailable(OkexCommunicator.requestBalances(), currency);
    }

    public static Orderbook getOrderbook(String symbol, Integer limit) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        String jsonOrderbook = OkexCommunicator.requestOrderbook(symbol, limit, null);
        return new Orderbook(OkexParser.parseOrderbookAsk(jsonOrderbook, null),
                OkexParser.parseOrderbookBid(jsonOrderbook, null));
    }

    public static String placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotNull(qty, "qty");

        return OkexParser.parseCreateOrderResponse(OkexCommunicator.requestNewOrder(symbol, side, "market", qty, null));
    }

    public static String placeLimitOrder(String symbol, String side, BigDecimal qty, BigDecimal price) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotNull(qty, "qty");
        GateUtils.checkParamNotNull(price, "price");

        return OkexParser.parseCreateOrderResponse(OkexCommunicator.requestNewOrder(symbol, side, "limit", qty, price));
    }

    public static Order checkOrderStatus(String symbol, String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return OkexParser.parseCheckOrderStatusResponse(OkexCommunicator.requestCheckOrderStatus(symbol, orderId));
    }

    public static String cancelOrder(String symbol, String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return OkexParser.parseCancelOrderResponse(OkexCommunicator.requestCancelOrder(symbol, orderId));
    }

}
