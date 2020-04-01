package com.sadkoala.stockgate.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.sadkoala.stockgate.parser.model.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HitbtcParser extends AbstractStockParser {

    /**
     * [{"id":225718606381,"clientOrderId":"e10f7723a05b40388a7f44169c8ef66a","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"7725.00","quantity":"0.00200","postOnly":false,"cumQuantity":"0","createdAt":"2020-03-24T08:36:42.847Z","updatedAt":"2020-03-24T08:36:42.847Z"},{"id":218085627589,"clientOrderId":"3f440af39de540788cdd1e8c15f4da0d","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"8323.00","quantity":"0.00150","postOnly":false,"cumQuantity":"0","createdAt":"2020-03-08T23:15:20.726Z","updatedAt":"2020-03-08T23:15:20.726Z"},{"id":219577472229,"clientOrderId":"7cf12c22403d408484ec45bbaf46450d","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"7477.00","quantity":"0.00150","postOnly":false,"cumQuantity":"0","createdAt":"2020-03-12T09:38:29.633Z","updatedAt":"2020-03-12T09:38:29.633Z"},{"id":212641851519,"clientOrderId":"82d4e808efd945e1a04610c97e10e14f","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"9793.00","quantity":"0.00200","postOnly":false,"cumQuantity":"0","createdAt":"2020-02-24T17:42:13.828Z","updatedAt":"2020-02-24T17:42:13.828Z"},{"id":226946723121,"clientOrderId":"e8e45b2fce654c35a772c81b329428ab","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"6745.00","quantity":"0.00200","postOnly":false,"cumQuantity":"0","createdAt":"2020-03-27T14:56:56.942Z","updatedAt":"2020-03-27T14:56:56.942Z"},{"id":213562926372,"clientOrderId":"3dfa9bc0ec1648a9afc1d5f247503815","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"9313.00","quantity":"0.00200","postOnly":false,"cumQuantity":"0","createdAt":"2020-02-26T13:42:49.504Z","updatedAt":"2020-02-26T13:42:49.504Z"},{"id":206813589902,"clientOrderId":"ref5Fgs725147b1e-1cc6-4dbe-b033-","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"10510.00","quantity":"0.00300","postOnly":false,"cumQuantity":"0","createdAt":"2020-02-12T11:30:08.344Z","updatedAt":"2020-02-12T11:30:08.344Z"},{"id":217907502363,"clientOrderId":"ref5Fgs70797b0c2-074f-4787-aaa1-","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"8775.00","quantity":"0.00150","postOnly":false,"cumQuantity":"0","createdAt":"2020-03-08T14:31:17.604Z","updatedAt":"2020-03-08T14:31:17.604Z"},{"id":213163996266,"clientOrderId":"95dfb65501a34a179b08eed567149051","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"9500.00","quantity":"0.00200","postOnly":false,"cumQuantity":"0","createdAt":"2020-02-25T19:48:29.225Z","updatedAt":"2020-02-25T19:48:29.225Z"},{"id":226206821923,"clientOrderId":"949177b120d8431495716ab63a0082da","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"6975.00","quantity":"0.00200","postOnly":false,"cumQuantity":"0","createdAt":"2020-03-25T11:54:57.234Z","updatedAt":"2020-03-25T11:54:57.234Z"},{"id":212312807618,"clientOrderId":"466e57034950493c90e2502f4b5d66c1","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"10092.00","quantity":"0.00200","postOnly":false,"cumQuantity":"0","createdAt":"2020-02-24T00:58:45.772Z","updatedAt":"2020-02-24T00:58:45.772Z"},{"id":225340349001,"clientOrderId":"2b445e2b1c6b462eb453660822d0eb34","symbol":"BTCUSD","side":"sell","status":"new","type":"limit","timeInForce":"GTC","price":"7225.00","quantity":"0.00200","postOnly":false,"cumQuantity":"0","createdAt":"2020-03-23T12:55:51.541Z","updatedAt":"2020-03-23T12:55:51.541Z"}]
     */
    public static List<Order> parseOpenOrders(final String jsonString) throws IOException {
        List<Order> parsedOrders = new ArrayList<>();
        for (JsonNode order : mapper.readTree(jsonString)) {
            parsedOrders.add(new Order("hitbtc",
                    order.get("symbol").asText(),
                    order.get("clientOrderId").asText(),
                    new BigDecimal(order.get("price").asText()),
                    new BigDecimal(order.get("quantity").asText()),
                    order.get("status").asText(),
                    hitbtcTimeDateToMilliseconds(order.get("createdAt").asText())));
        }
        return parsedOrders;
    }

    private static Long hitbtcTimeDateToMilliseconds(String timeDate) {
        return null;
    }

}
