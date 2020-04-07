package com.sadkoala.stockgate.parser.model;

import java.math.BigDecimal;

public class OrderBookEntry {

    private BigDecimal price;
    private BigDecimal qty;

    public OrderBookEntry(BigDecimal price, BigDecimal qty) {
        this.price = price;
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQty() {
        return qty;
    }
}
