package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.ParameterUtils;
import com.sadkoala.stockgate.communicator.HitbtcCommunicator;
import com.sadkoala.stockgate.parser.HitbtcParser;
import com.sadkoala.stockgate.parser.model.Order;

import java.util.List;

public class HitbtcAdapter {

    public static List<Order> getOpenOrders(String symbol) throws Exception {
        ParameterUtils.checkParamEmpty(symbol, "symbol");
        return HitbtcParser.parseOpenOrders(HitbtcCommunicator.requestOpenOrders(symbol));
    }

}
