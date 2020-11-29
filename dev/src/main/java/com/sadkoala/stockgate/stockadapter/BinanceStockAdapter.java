package com.sadkoala.stockgate.stockadapter;

import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.adapter.BinanceAdapter;
import com.sadkoala.stockgate.communicator.BinanceCommunicator;
import com.sadkoala.stockgate.parser.BinanceParser;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.Ticker;

import java.math.BigDecimal;
import java.util.List;

public class BinanceStockAdapter implements IStockAdapter {

    @Override
    public String getStockName() {
        return "Binance";
    }

    @Override
    public List<Order> getOpenOrders(String symbol) throws Exception {
        return BinanceAdapter.getOpenOrders(symbol);
    }

    @Override
    public void placeMarketOrder(String symbol, String side, BigDecimal qty) throws Exception {
        BinanceAdapter.placeMarketOrder(symbol, side, qty);
    }

    @Override
    public String cancelOrder(String symbol, String orderId) throws Exception {
        return BinanceAdapter.cancelOrder(symbol, orderId);
    }

    @Override
    public Ticker getSymbolTicker(String symbol) throws Exception {
        GateUtils.checkParamNotEmpty(symbol, "symbol");

        return new Ticker(symbol,
                BinanceParser.parseSymbolPrice(BinanceCommunicator.requestLatestSymbolPrice(symbol)));
    }

}
