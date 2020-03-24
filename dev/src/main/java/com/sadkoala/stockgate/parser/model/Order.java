package com.sadkoala.stockgate.parser.model;

import java.math.BigDecimal;

public class Order {

    private String stock;
    private String symbol;
    private String orderId;
    private BigDecimal price;
    private BigDecimal qty;
    private String status;
    private Long time;

    public Order(String stock, String symbol, String orderId, BigDecimal price, BigDecimal qty, String status, Long time) {
        this.stock = stock;
        this.symbol = symbol;
        this.orderId = orderId;
        this.price = price;
        this.qty = qty;
        this.status = status;
        this.time = time;
    }

    public String getStock() {
        return stock;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public String getStatus() {
        return status;
    }

    public Long getTime() {
        return time;
    }

}
