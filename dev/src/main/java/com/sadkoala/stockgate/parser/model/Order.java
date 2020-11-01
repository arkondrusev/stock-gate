package com.sadkoala.stockgate.parser.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Order {

    private String stock;
    private String symbol;
    private String orderId;
    private BigDecimal price; // price order was placed for
    private BigDecimal qty; //initial qty
    private String status;
    private Long createTime;
    private String side;
    private String type;
    private Set<OrderFill> orderFills = new HashSet<>();

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

    public void addOrderFill(BigDecimal price, BigDecimal qty) {
        orderFills.add(new OrderFill(price, qty));
    }

    public Set<OrderFill> getOrderFills() {
        return orderFills;
    }
}
