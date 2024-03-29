package com.sadkoala.stockgate.stockadapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStockAdapter {

    public static List<IStockAdapter> getAdapters() {
        List<IStockAdapter> adapters = new ArrayList<>();
        adapters.add(getBinanceStockAdapter());
        adapters.add(getHitbtcStockAdapter());
        adapters.add(getOkexStockAdapter());
        return adapters;
    }

    public static BinanceStockAdapter getBinanceStockAdapter() {
        return new BinanceStockAdapter();
    }

    public static HitbtcStockAdapter getHitbtcStockAdapter() {
        return new HitbtcStockAdapter();
    }

    public static OkexStockAdapter getOkexStockAdapter() {
        return new OkexStockAdapter();
    }

}
