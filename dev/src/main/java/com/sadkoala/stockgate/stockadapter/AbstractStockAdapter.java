package com.sadkoala.stockgate.stockadapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStockAdapter {

    public static List<IStockAdapter> getAdapters() {
        List<IStockAdapter> adapters = new ArrayList<>();
        adapters.add(new BinanceStockAdapter());
        adapters.add(new HitbtcStockAdapter());
        adapters.add(new OkexStockAdapter());
        return adapters;
    }

}
