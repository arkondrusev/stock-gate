package com.sadkoala.stockgate.parser.model;

import java.math.BigDecimal;

public class Ticker {

    public String symbol;
    public BigDecimal lastPrice;

    public Ticker(String symbol, BigDecimal lastPrice) {
        this.symbol = symbol;
        this.lastPrice = lastPrice;
    }

}
