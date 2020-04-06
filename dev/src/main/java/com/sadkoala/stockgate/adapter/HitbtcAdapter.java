package com.sadkoala.stockgate.adapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.communicator.HitbtcCommunicator;
import com.sadkoala.stockgate.parser.HitbtcParser;
import com.sadkoala.stockgate.parser.model.Order;

import java.util.List;

public class HitbtcAdapter extends AbstractStockAdapter {

    public static List<Order> getOpenOrders(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");
        return HitbtcParser.parseOpenOrders(HitbtcCommunicator.requestOpenOrders(symbol));
    }

}
