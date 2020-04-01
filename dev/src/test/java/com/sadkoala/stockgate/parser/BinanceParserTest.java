package com.sadkoala.stockgate.parser;

import com.sadkoala.stockgate.parser.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class BinanceParserTest {

    @Test
    public void testParseOpenOrders() throws IOException {
        String jsonString = "[{\"symbol\":\"BTCUSDT\",\"orderId\":1164435203,\"orderListId\":-1,\"clientOrderId\":\"and_059bea04ee574a76878c4de37dae7e7c\",\"price\":\"10510.00000000\",\"origQty\":\"0.00299700\",\"executedQty\":\"0.00000000\",\"cummulativeQuoteQty\":\"0.00000000\",\"status\":\"NEW\",\"timeInForce\":\"GTC\",\"type\":\"LIMIT\",\"side\":\"SELL\",\"stopPrice\":\"0.00000000\",\"icebergQty\":\"0.00000000\",\"time\":1581506942308,\"updateTime\":1581506942308,\"isWorking\":true,\"origQuoteOrderQty\":\"0.00000000\"},{\"symbol\":\"BTCUSDT\",\"orderId\":1260089828,\"orderListId\":-1,\"clientOrderId\":\"and_abe6014b4bf941409f49751dc85b696a\",\"price\":\"10096.00000000\",\"origQty\":\"0.00199800\",\"executedQty\":\"0.00000000\",\"cummulativeQuoteQty\":\"0.00000000\",\"status\":\"NEW\",\"timeInForce\":\"GTC\",\"type\":\"LIMIT\",\"side\":\"SELL\",\"stopPrice\":\"0.00000000\",\"icebergQty\":\"0.00000000\",\"time\":1582506033911,\"updateTime\":1582506033911,\"isWorking\":true,\"origQuoteOrderQty\":\"0.00000000\"}]";
        List<Order> orders = BinanceParser.parseOpenOrders(jsonString);
        Assertions.assertEquals(2, orders.size());
    }

}
