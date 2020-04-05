package com.sadkoala.stockgate.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.sadkoala.stockgate.parser.model.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OkexParser extends AbstractStockParser {

    /**
     * [{"client_oid":"","created_at":"2020-04-04T21:30:48.812Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4672706891696128","order_type":"0","price":"6985","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00188817","state":"0","status":"open","timestamp":"2020-04-04T21:30:48.812Z","type":"limit"},{"client_oid":"","created_at":"2020-03-24T08:38:16.173Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4607383709241344","order_type":"0","price":"7725","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00193348","state":"0","status":"open","timestamp":"2020-03-24T08:38:16.173Z","type":"limit"},{"client_oid":"","created_at":"2020-03-12T09:39:50.391Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4539678088972288","order_type":"0","price":"7472","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00135614","state":"0","status":"open","timestamp":"2020-03-12T09:39:50.391Z","type":"limit"},{"client_oid":"","created_at":"2020-03-08T23:17:05.609Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4520242420206592","order_type":"0","price":"8323","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00181878","state":"0","status":"open","timestamp":"2020-03-08T23:17:05.609Z","type":"limit"},{"client_oid":"","created_at":"2020-03-08T14:42:08.216Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4518217528263680","order_type":"0","price":"8775","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.0015058","state":"0","status":"open","timestamp":"2020-03-08T14:42:08.216Z","type":"limit"},{"client_oid":"","created_at":"2020-02-25T19:53:51.805Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4451495562195968","order_type":"0","price":"9500","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00213768","state":"0","status":"open","timestamp":"2020-02-25T19:53:51.805Z","type":"limit"},{"client_oid":"","created_at":"2020-02-24T17:47:30.915Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4445336430658560","order_type":"0","price":"9774","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00207263","state":"0","status":"open","timestamp":"2020-02-24T17:47:30.915Z","type":"limit"},{"client_oid":"","created_at":"2020-02-24T01:01:37.775Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4441381127212032","order_type":"0","price":"10086","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.0020103","state":"0","status":"open","timestamp":"2020-02-24T01:01:37.775Z","type":"limit"},{"client_oid":"","created_at":"2020-02-12T11:48:04.050Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4375975299138560","order_type":"0","price":"10510","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00289397","state":"0","status":"open","timestamp":"2020-02-12T11:48:04.050Z","type":"limit"}]
     */
    public static List<Order> parseOpenOrders(final String jsonString) throws IOException {
        List<Order> parsedOrders = new ArrayList<>();
        for (JsonNode order : mapper.readTree(jsonString)) {
            parsedOrders.add(new Order("okex",
                    order.get("instrument_id").asText(),
                    order.get("order_id").asText(),
                    new BigDecimal(order.get("price").asText()),
                    new BigDecimal(order.get("size").asText()),
                    order.get("status").asText(),
                    null));
        }
        return parsedOrders;
    }

}
