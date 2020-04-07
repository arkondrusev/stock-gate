package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.communicator.HitbtcCommunicator;
import com.sadkoala.stockgate.parser.HitbtcParser;
import com.sadkoala.stockgate.parser.model.Order;

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

}
