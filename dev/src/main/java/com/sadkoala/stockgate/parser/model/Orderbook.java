package com.sadkoala.stockgate.parser.model;

import java.util.List;

public class Orderbook {

    private OrderbookHalf ask;
    private OrderbookHalf bid;

    public Orderbook(OrderbookHalf ask, OrderbookHalf bid) {
        this.ask = ask;
        this.bid = bid;
    }

    public Orderbook(List<OrderbookEntry> askList, List<OrderbookEntry> bidList) {
        ask = new OrderbookHalf(askList, BookType.ASK);
        bid = new OrderbookHalf(bidList, BookType.BID);
    }

    public OrderbookHalf getAsk() {
        return ask;
    }

    public OrderbookHalf getBid() {
        return bid;
    }

}
