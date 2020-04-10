package com.sadkoala.stockgate.parser.model;

import java.util.List;

public class OrderbookHalf {

    private List<OrderbookEntry> list;

    private BookType type;

    public OrderbookHalf(List<OrderbookEntry> list, BookType type) {
        this.list = list;
        this.type = type;
    }

    public List<OrderbookEntry> getList() {
        return list;
    }

    public BookType getType() {
        return type;
    }

}
