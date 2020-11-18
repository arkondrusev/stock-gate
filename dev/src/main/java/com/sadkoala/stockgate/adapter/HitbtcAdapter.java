package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.communicator.HitbtcCommunicator;
import com.sadkoala.stockgate.parser.HitbtcParser;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HitbtcAdapter extends AbstractStockAdapter {

    public static List<Order> getOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return HitbtcParser.parseOpenOrders(HitbtcCommunicator.requestOpenOrders(symbol));
    }

    public static BigDecimal getCurrencyBalanceAvailaible(String currency) throws Exception {
        GateUtils.checkParamNotEmpty(currency, "currency");

        return HitbtcParser.parseTradingBalanceAvailaible(HitbtcCommunicator.requestTradingBalance(), currency);
    }

    public static Orderbook getOrderbook(String symbol, Integer limit) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        String jsonOrderbook = HitbtcCommunicator.requestOrderbook(symbol, limit);
        return new Orderbook(HitbtcParser.parseOrderbookAsk(jsonOrderbook, symbol, limit),
                HitbtcParser.parseOrderbookBid(jsonOrderbook, symbol, limit));
    }

    public static Order placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotNull(qty, "qty");

        return HitbtcParser.parseOrderResponse(HitbtcCommunicator.requestNewOrder(symbol, side, "market", qty, null));
    }

    public static Order placeLimitOrder(String symbol, String side, BigDecimal qty, BigDecimal price) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotNull(qty, "qty");
        GateUtils.checkParamNotNull(price, "price");

        return HitbtcParser.parseOrderResponse(HitbtcCommunicator.requestNewOrder(symbol, side, "limit", qty, price));
    }

    public static String checkOrderStatus(String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return HitbtcParser.parseCheckOrderStatusResponse(HitbtcCommunicator.requestCheckOrderStatus(orderId));
    }

    public static String cancelOrder(String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return HitbtcParser.parseCancelOrderResponse(HitbtcCommunicator.requestCancelOrder(orderId));
    }

    public static BigDecimal getSymbolPrice(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        List<String> symbols = new ArrayList<>();
        symbols.add(symbol);

        return HitbtcParser.parseTickers(HitbtcCommunicator.requestSymbolsTickers(symbols)).get(0).lastPrice;
    }

    public static Order getOrder(String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return HitbtcParser.parseOrderResponse(HitbtcCommunicator.requestCheckOrderStatus(orderId));
    }

}