package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.communicator.OkexCommunicator;
import com.sadkoala.stockgate.parser.OkexParser;
import com.sadkoala.stockgate.parser.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class OkexAdapter {

    public static List<Order> getOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return OkexParser.parseOpenOrders(OkexCommunicator.requestOpenOrders(symbol));
    }

    public static BigDecimal getBalanceAvailable(final String currency) throws Exception {
        GateUtils.checkParamNotEmpty(currency, "currency");

        return OkexParser.parseBalanceAvailable(OkexCommunicator.requestBalances(), currency);
    }

}
