package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.communicator.HitbtcCommunicator;
import com.sadkoala.stockgate.parser.HitbtcParser;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Orderbook;

import java.math.BigDecimal;
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

}
