package com.sadkoala.stockgate.parser.model;

import java.math.BigDecimal;

public class Order {

    private String stock;
    private String symbol;
    private String orderId;
    private BigDecimal price;
    private BigDecimal qty;
    private String status;
    private Long createTime;
    private String side;
    private String type;

    public Order(String stock, String symbol, String orderId, BigDecimal price, BigDecimal qty, String status, Long createTime, String side, String type) {
        this.stock = stock;
        this.symbol = symbol;
        this.orderId = orderId;
        this.price = price;
        this.qty = qty;
        this.status = status;
        this.createTime = createTime;
        this.side = side;
        this.type = type;
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

    public Long getCreateTime() {
        return createTime;
    }

    public String getSide() {
        return side;
    }

    public String getType() {
        return type;
    }

}
