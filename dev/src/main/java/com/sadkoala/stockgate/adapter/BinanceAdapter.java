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

}