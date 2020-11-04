package com.sadkoala.stockgate.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.OrderbookEntry;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OkexParser extends AbstractStockParser {

    /**
     * [{"client_oid":"","created_at":"2020-04-04T21:30:48.812Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4672706891696128","order_type":"0","price":"6985","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00188817","state":"0","status":"open","timestamp":"2020-04-04T21:30:48.812Z","type":"limit"},{"client_oid":"","created_at":"2020-03-24T08:38:16.173Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4607383709241344","order_type":"0","price":"7725","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00193348","state":"0","status":"open","timestamp":"2020-03-24T08:38:16.173Z","type":"limit"},{"client_oid":"","created_at":"2020-03-12T09:39:50.391Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4539678088972288","order_type":"0","price":"7472","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00135614","state":"0","status":"open","timestamp":"2020-03-12T09:39:50.391Z","type":"limit"},{"client_oid":"","created_at":"2020-03-08T23:17:05.609Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4520242420206592","order_type":"0","price":"8323","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00181878","state":"0","status":"open","timestamp":"2020-03-08T23:17:05.609Z","type":"limit"},{"client_oid":"","created_at":"2020-03-08T14:42:08.216Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4518217528263680","order_type":"0","price":"8775","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.0015058","state":"0","status":"open","timestamp":"2020-03-08T14:42:08.216Z","type":"limit"},{"client_oid":"","created_at":"2020-02-25T19:53:51.805Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4451495562195968","order_type":"0","price":"9500","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00213768","state":"0","status":"open","timestamp":"2020-02-25T19:53:51.805Z","type":"limit"},{"client_oid":"","created_at":"2020-02-24T17:47:30.915Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4445336430658560","order_type":"0","price":"9774","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00207263","state":"0","status":"open","timestamp":"2020-02-24T17:47:30.915Z","type":"limit"},{"client_oid":"","created_at":"2020-02-24T01:01:37.775Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4441381127212032","order_type":"0","price":"10086","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.0020103","state":"0","status":"open","timestamp":"2020-02-24T01:01:37.775Z","type":"limit"},{"client_oid":"","created_at":"2020-02-12T11:48:04.050Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4375975299138560","order_type":"0","price":"10510","price_avg":"","product_id":"BTC-USDT","side":"sell","size":"0.00289397","state":"0","status":"open","timestamp":"2020-02-12T11:48:04.050Z","type":"limit"}]
     */
    public static List<Order> parseOpenOrders(final String jsonString) throws IOException {
        List<Order> parsedOrders = new ArrayList<>();
        for (JsonNode order : mapper.readTree(jsonString)) {
            parsedOrders.add(parseOrder(order));
        }
        return parsedOrders;
    }

    /**
     * [{"frozen":"0.01764952","hold":"0.01764952","id":"","currency":"BTC","balance":"0.01764952","available":"0","holds":"0.01764952"},{"frozen":"0.252976","hold":"0.252976","id":"","currency":"LTC","balance":"0.25297695","available":"0.00000095","holds":"0.252976"},{"frozen":"0.069773","hold":"0.069773","id":"","currency":"ETH","balance":"0.06977318","available":"0.00000018","holds":"0.069773"},{"frozen":"0.23167","hold":"0.23167","id":"","currency":"XMR","balance":"0.23167008","available":"0.00000008","holds":"0.23167"},{"frozen":"0","hold":"0","id":"","currency":"USDT","balance":"142.63933592","available":"142.63933592","holds":"0"}]
     */
    public static BigDecimal parseBalanceAvailable(final String jsonString, final String currency) throws IOException {
        GateUtils.checkParamNotEmpty(currency, "currency");

        for (JsonNode balanceNode : mapper.readTree(jsonString)) {
            if (currency.equals(balanceNode.get("currency").asText())) {
                return new BigDecimal(balanceNode.get("available").asText());
            }
        }

        return null;
    }

    /**
     * {"asks":[["6924","0.00752523","4"],["6924.3","0.01","1"],["6924.4","0.001","1"]],"bids":[["6923.9","1.03182977","11"],["6923.5","0.001","1"],["6923.4","0.006","1"]],"timestamp":"2020-04-10T19:19:03.844Z"}
     */
    private static List<OrderbookEntry> parseOrderbook(String jsonString, String bookType, Integer limit) throws IOException {
        List<OrderbookEntry> entryList = Objects.nonNull(limit) ? new ArrayList<>(limit) : new ArrayList<>();
        int i = 0;
        for (JsonNode entry : mapper.readTree(jsonString).get(bookType)) {
            i++;
            entryList.add(new OrderbookEntry(
                    new BigDecimal(entry.get(0).asText()), new BigDecimal(entry.get(1).asText())));
            if (limit != null && i == limit) {
                break;
            }
        }

        return entryList;
    }

    public static List<OrderbookEntry> parseOrderbookAsk(String jsonString, Integer limit) throws IOException {
        return parseOrderbook(jsonString,"asks", limit);
    }

    public static List<OrderbookEntry> parseOrderbookBid(String jsonString, Integer limit) throws IOException {
        return parseOrderbook(jsonString,"bids", limit);
    }

    /**
     * {"client_oid":"","code":"0","error_code":"0","error_message":"","message":"","order_id":"4771850469064704","result":true}
     */
    public static String parseCreateOrderResponse(String jsonString) throws IOException {
        return parseOrderId(jsonString);
    }

    /**
     * {"client_oid":"","created_at":"2020-04-22T09:44:19.746Z","filled_notional":"0","filled_size":"0","funds":"","instrument_id":"BTC-USDT","notional":"","order_id":"4771850469064704","order_type":"0","price":"5948.4","price_avg":"0","product_id":"BTC-USDT","side":"buy","size":"0.002","state":"0","status":"open","timestamp":"2020-04-22T09:44:19.746Z","type":"limit"}
     */
    public static Order parseCheckOrderStatusResponse(String jsonString) throws IOException {
        return parseOrder(mapper.readTree(jsonString));
    }

    /**
     * {"client_oid":"","code":"0","error_code":"0","error_message":"","message":"","order_id":"4771850469064704","result":true}
     */
    public static String parseCancelOrderResponse(String jsonString) {
        return parseResult(jsonString);
    }

    private static String parseOrderId(String inputString) {
        Pattern pattern = Pattern.compile("\"order_id\":\"(\\d+)\",");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String parseResult(String inputString) {
        Pattern pattern = Pattern.compile("\"result\":(\\w+)}");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static Order parseOrder(JsonNode orderNode) {
        return new Order("okex",
                orderNode.get("instrument_id").asText(),
                orderNode.get("order_id").asText(),
                new BigDecimal(orderNode.get("price").asText()),
                new BigDecimal(orderNode.get("size").asText()),
                orderNode.get("status").asText(),
                null,
                orderNode.get("side").asText(),
                null,
                null);
    }

}
