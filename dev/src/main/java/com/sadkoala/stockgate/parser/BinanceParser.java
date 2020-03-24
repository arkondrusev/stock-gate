package com.sadkoala.stockgate.parser;

import com.sadkoala.stockgate.parser.model.Order;

import java.io.IOException;
import java.util.List;

public class BinanceParser extends AbstractStockParser {

    /**
     * пример инпута {"symbol":"BTCUSDT","price":"5343.93000000"}
     */
    public static String parseBtcPrice(final String jsonString) throws IOException {
        return mapper.readTree(jsonString).get("price").asText();
    }

    /**
     * [{"symbol":"BTCUSDT","orderId":1164435203,"orderListId":-1,"clientOrderId":"and_059bea04ee574a76878c4de37dae7e7c","price":"10510.00000000","origQty":"0.00299700","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1581506942308,"updateTime":1581506942308,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1260089828,"orderListId":-1,"clientOrderId":"and_abe6014b4bf941409f49751dc85b696a","price":"10096.00000000","origQty":"0.00199800","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1582506033911,"updateTime":1582506033911,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1267003061,"orderListId":-1,"clientOrderId":"and_207e111c2fa54299b6bc2478a9820afb","price":"9778.00000000","origQty":"0.00199800","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1582566264887,"updateTime":1582566264887,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1277532840,"orderListId":-1,"clientOrderId":"and_15340604ac5d4fe98e92a132218a34b5","price":"9500.00000000","origQty":"0.00199800","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1582660543049,"updateTime":1582660543049,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1391297417,"orderListId":-1,"clientOrderId":"and_5daa9b8507e6430aaf2d3213b3809f86","price":"8775.00000000","origQty":"0.00149800","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1583677831158,"updateTime":1583677831158,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1395823255,"orderListId":-1,"clientOrderId":"web_309cf887aedf4a8a90ba14504bc388c5","price":"8323.00000000","origQty":"0.00149900","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1583709539088,"updateTime":1583709539088,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1439514387,"orderListId":-1,"clientOrderId":"web_90167b5eb30f4feeb39d3d8ff12277d2","price":"7472.00000000","origQty":"0.00149900","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1584006155832,"updateTime":1584006155832,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1558996845,"orderListId":-1,"clientOrderId":"web_b26636b406374bb4b15cb23fc47e2047","price":"6955.00000000","origQty":"0.00199800","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1584700820952,"updateTime":1584700820952,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1600922780,"orderListId":-1,"clientOrderId":"web_ed1846e283db42ad820ee29cb5771c86","price":"7225.00000000","origQty":"0.00200000","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1584968125428,"updateTime":1584968125428,"isWorking":true,"origQuoteOrderQty":"0.00000000"},{"symbol":"BTCUSDT","orderId":1612483525,"orderListId":-1,"clientOrderId":"web_4d711ae8874d4fbca6cf3d770ac771d2","price":"7725.00000000","origQty":"0.00200000","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1585039042242,"updateTime":1585039042242,"isWorking":true,"origQuoteOrderQty":"0.00000000"}]
     */
    public static List<Order> parseOpenOrders(final String jsonString) throws IOException {
        mapper.readTree(jsonString);

        return null;
    }

}
