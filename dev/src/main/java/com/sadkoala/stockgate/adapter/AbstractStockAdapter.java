package com.sadkoala.stockgate.adapter;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.NotImplementedException;

import static com.sadkoala.stockgate.StockGateConstants.STOCK_BINANCE_MARKER;

public abstract class AbstractStockAdapter {

    public static IStockAdapter createStockAdapter(String stockShortName) {
        Preconditions.checkNotNull(stockShortName);
        if (STOCK_BINANCE_MARKER.equals(stockShortName)) {
            return new BinanceAdapter();
        } else {
            throw new NotImplementedException(String.format("Нет имплементации адаптера для биржи [%s]", stockShortName));
        }
    }

}
