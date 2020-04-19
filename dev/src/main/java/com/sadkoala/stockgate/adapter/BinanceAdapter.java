package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.communicator.BinanceCommunicator;
import com.sadkoala.stockgate.parser.BinanceParser;
import com.sadkoala.stockgate.parser.model.*;

import java.math.BigDecimal;
import java.util.List;

public class BinanceAdapter extends AbstractStockAdapter {

    public static BigDecimal getBtcPrice() throws Exception {
        return new BigDecimal(BinanceParser.parseBtcPrice(BinanceCommunicator.requestLatestSymbolPrice("BTCUSDT")));
    }

    public static List<Order> getOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return BinanceParser.parseOpenOrders(BinanceCommunicator.requestOpenOrders(symbol));
    }

    public static BigDecimal getAssetBalanceFree(String asset) throws Exception {
        GateUtils.checkParamNotEmpty(asset, "asset");

        return BinanceParser.parseAssetBalanceFree(BinanceCommunicator.requestAccountInfo(), asset);
    }

    public static Orderbook getOrderbook(String symbol, Integer limit) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        String jsonOrderbook = BinanceCommunicator.requestOrderbook(symbol);
        return new Orderbook(BinanceParser.parseOrderbookAsk(jsonOrderbook, limit),
                BinanceParser.parseOrderbookBid(jsonOrderbook, limit));
    }

    public static Order placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotNull(qty, "qty");

        return BinanceParser.parseCreateOrderResponse(BinanceCommunicator.requestNewOrder(symbol, side, "MARKET", qty, null));
    }

    public static Order placeLimitOrder(String symbol, String side, BigDecimal qty, BigDecimal price) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(side, "side");
        GateUtils.checkParamNotNull(qty, "qty");
        GateUtils.checkParamNotNull(price, "price");

        return BinanceParser.parseCreateOrderResponse(BinanceCommunicator.requestNewOrder(symbol, side, "LIMIT", qty, price));
    }

    public static String checkOrderStatus(String symbol, String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return BinanceParser.parseCheckOrderStatusResponse(BinanceCommunicator.requestCheckOrderStatus(symbol, orderId));
    }

    public static String cancelOrder(String symbol, String orderId) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        GateUtils.checkParamNotEmpty(orderId, "orderId");

        return BinanceParser.parseCancelOrderResponse(BinanceCommunicator.requestCancelOrder(symbol, orderId));
    }

}