package com.sadkoala.stockgate.parser;

import com.sadkoala.stockgate.parser.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class OkexParserTest {

    @Test
    public void testParseOpenOrders() throws IOException {
        String jsonResponse = "[{\"client_oid\":\"\",\"created_at\":\"2020-04-04T21:30:48.812Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4672706891696128\",\"order_type\":\"0\",\"price\":\"6985\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.00188817\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-04-04T21:30:48.812Z\",\"type\":\"limit\"},{\"client_oid\":\"\",\"created_at\":\"2020-03-24T08:38:16.173Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4607383709241344\",\"order_type\":\"0\",\"price\":\"7725\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.00193348\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-03-24T08:38:16.173Z\",\"type\":\"limit\"},{\"client_oid\":\"\",\"created_at\":\"2020-03-12T09:39:50.391Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4539678088972288\",\"order_type\":\"0\",\"price\":\"7472\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.00135614\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-03-12T09:39:50.391Z\",\"type\":\"limit\"},{\"client_oid\":\"\",\"created_at\":\"2020-03-08T23:17:05.609Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4520242420206592\",\"order_type\":\"0\",\"price\":\"8323\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.00181878\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-03-08T23:17:05.609Z\",\"type\":\"limit\"},{\"client_oid\":\"\",\"created_at\":\"2020-03-08T14:42:08.216Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4518217528263680\",\"order_type\":\"0\",\"price\":\"8775\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.0015058\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-03-08T14:42:08.216Z\",\"type\":\"limit\"},{\"client_oid\":\"\",\"created_at\":\"2020-02-25T19:53:51.805Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4451495562195968\",\"order_type\":\"0\",\"price\":\"9500\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.00213768\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-02-25T19:53:51.805Z\",\"type\":\"limit\"},{\"client_oid\":\"\",\"created_at\":\"2020-02-24T17:47:30.915Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4445336430658560\",\"order_type\":\"0\",\"price\":\"9774\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.00207263\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-02-24T17:47:30.915Z\",\"type\":\"limit\"},{\"client_oid\":\"\",\"created_at\":\"2020-02-24T01:01:37.775Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4441381127212032\",\"order_type\":\"0\",\"price\":\"10086\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.0020103\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-02-24T01:01:37.775Z\",\"type\":\"limit\"},{\"client_oid\":\"\",\"created_at\":\"2020-02-12T11:48:04.050Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4375975299138560\",\"order_type\":\"0\",\"price\":\"10510\",\"price_avg\":\"\",\"product_id\":\"BTC-USDT\",\"side\":\"sell\",\"size\":\"0.00289397\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-02-12T11:48:04.050Z\",\"type\":\"limit\"}]\n";
        List<Order> orders = OkexParser.parseOpenOrders(jsonResponse);
        Assertions.assertEquals(9, orders.size());
    }

    @Test
    public void testParseBalanceAvailable() throws IOException {
        String json = "[{\"frozen\":\"0.01764952\",\"hold\":\"0.01764952\",\"id\":\"\",\"currency\":\"BTC\",\"balance\":\"0.01764952\",\"available\":\"0\",\"holds\":\"0.01764952\"},{\"frozen\":\"0.252976\",\"hold\":\"0.252976\",\"id\":\"\",\"currency\":\"LTC\",\"balance\":\"0.25297695\",\"available\":\"0.00000095\",\"holds\":\"0.252976\"},{\"frozen\":\"0.069773\",\"hold\":\"0.069773\",\"id\":\"\",\"currency\":\"ETH\",\"balance\":\"0.06977318\",\"available\":\"0.00000018\",\"holds\":\"0.069773\"},{\"frozen\":\"0.23167\",\"hold\":\"0.23167\",\"id\":\"\",\"currency\":\"XMR\",\"balance\":\"0.23167008\",\"available\":\"0.00000008\",\"holds\":\"0.23167\"},{\"frozen\":\"0\",\"hold\":\"0\",\"id\":\"\",\"currency\":\"USDT\",\"balance\":\"142.63933592\",\"available\":\"142.63933592\",\"holds\":\"0\"}]\n";
        BigDecimal balance = OkexParser.parseBalanceAvailable(json,"USDT");
        System.out.println("balance=" + balance.toPlainString());
    }

    @Test
    public void testParseOrderbook() throws Exception {
        String jsonResponse = "{\"asks\":[[\"6858.4\",\"0.02555115\",\"2\"],[\"6858.5\",\"0.001\",\"1\"],[\"6858.8\",\"0.008\",\"2\"]],\"bids\":[[\"6858.3\",\"2.22068959\",\"14\"],[\"6858.2\",\"0.005\",\"1\"],[\"6858.1\",\"0.814\",\"4\"]],\"timestamp\":\"2020-04-10T18:15:46.006Z\"}\n";
        System.out.println("order book size = " + OkexParser.parseOrderbookAsk(jsonResponse,   1).size());
    }

    @Test
    public void testParseCreateNewOrderResponse() throws IOException {
        String jsonString = "{\"client_oid\":\"\",\"code\":\"0\",\"error_code\":\"0\",\"error_message\":\"\",\"message\":\"\",\"order_id\":\"4771850469064704\",\"result\":true}";
        System.out.println("order id : " + OkexParser.parseCreateOrderResponse(jsonString));
    }

    @Test
    public void testParseCheckOrderStatusResponse() throws IOException {
        String jsonString = "{\"client_oid\":\"\",\"created_at\":\"2020-04-22T09:44:19.746Z\",\"filled_notional\":\"0\",\"filled_size\":\"0\",\"funds\":\"\",\"instrument_id\":\"BTC-USDT\",\"notional\":\"\",\"order_id\":\"4771850469064704\",\"order_type\":\"0\",\"price\":\"5948.4\",\"price_avg\":\"0\",\"product_id\":\"BTC-USDT\",\"side\":\"buy\",\"size\":\"0.002\",\"state\":\"0\",\"status\":\"open\",\"timestamp\":\"2020-04-22T09:44:19.746Z\",\"type\":\"limit\"}\n";
        System.out.println("order status : " + OkexParser.parseCheckOrderStatusResponse(jsonString).getStatus());
    }

    @Test
    public void testParseCancelOrderResponse() {
        String jsonString = "{\"client_oid\":\"\",\"code\":\"0\",\"error_code\":\"0\",\"error_message\":\"\",\"message\":\"\",\"order_id\":\"4771850469064704\",\"result\":true}";
        System.out.println("result : " + OkexParser.parseCancelOrderResponse(jsonString));
    }

    @Test
    public void testParseTicker() throws IOException {
        String jsonString = "{\"best_ask\":\"19103\",\"best_bid\":\"19102.9\",\"instrument_id\":\"BTC-USDT\",\"product_id\":\"BTC-USDT\",\"last\":\"19101.1\",\"last_qty\":\"0.01\",\"ask\":\"19103\",\"best_ask_size\":\"5.24520915\",\"bid\":\"19102.9\",\"best_bid_size\":\"0.00238693\",\"open_24h\":\"19090.3\",\"high_24h\":\"19350\",\"low_24h\":\"18854.4\",\"base_volume_24h\":\"12973.87557909\",\"timestamp\":\"2020-12-06T17:09:56.269Z\",\"quote_volume_24h\":\"248072260.6\"}\n";
        System.out.println("result : " + OkexParser.parseTicker(jsonString).lastPrice);
    }

}
