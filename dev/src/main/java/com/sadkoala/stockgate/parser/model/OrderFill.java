package com.sadkoala.stockgate.parser.model;

import java.math.BigDecimal;

public class OrderFill {

    private BigDecimal price;
    private BigDecimal qty;

    public OrderFill(BigDecimal price, BigDecimal qty) {
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
