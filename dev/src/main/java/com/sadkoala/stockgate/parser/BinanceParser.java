package com.sadkoala.stockgate.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.sadkoala.stockgate.GateUtils;
import com.sadkoala.stockgate.parser.model.Order;
import com.sadkoala.stockgate.parser.model.OrderbookEntry;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List<Order> parsedOrders = new ArrayList<>();
        for (JsonNode order : mapper.readTree(jsonString)) {
            parsedOrders.add(parseOrder(order, "time"));
        }
        return parsedOrders;
    }

    /**
     * {"makerCommission":10,"takerCommission":10,"buyerCommission":0,"sellerCommission":0,"canTrade":true,"canWithdraw":true,"canDeposit":true,"updateTime":1586198814036,"accountType":"MARGIN","balances":[{"asset":"BTC","free":"0.00000078","locked":"0.01548700"},{"asset":"LTC","free":"0.00000000","locked":"0.24975000"},{"asset":"ETH","free":"0.00000390","locked":"0.06993000"},{"asset":"NEO","free":"0.00243000","locked":"0.00000000"},{"asset":"BNB","free":"1.02212388","locked":"0.00000000"},{"asset":"QTUM","free":"0.00000000","locked":"0.00000000"},{"asset":"EOS","free":"0.00767000","locked":"0.00000000"},{"asset":"SNT","free":"0.00000000","locked":"0.00000000"},{"asset":"BNT","free":"0.00000000","locked":"0.00000000"},{"asset":"GAS","free":"0.01616750","locked":"0.00000000"},{"asset":"BCC","free":"0.00000000","locked":"0.00000000"},{"asset":"USDT","free":"123.10131005","locked":"0.00000000"},{"asset":"HSR","free":"0.00000000","locked":"0.00000000"},{"asset":"OAX","free":"0.00000000","locked":"0.00000000"},{"asset":"DNT","free":"0.00000000","locked":"0.00000000"},{"asset":"MCO","free":"0.00000000","locked":"0.00000000"},{"asset":"ICN","free":"0.00000000","locked":"0.00000000"},{"asset":"ZRX","free":"0.00000000","locked":"0.00000000"},{"asset":"OMG","free":"0.00000000","locked":"0.00000000"},{"asset":"WTC","free":"0.00000000","locked":"0.00000000"},{"asset":"YOYO","free":"0.00000000","locked":"0.00000000"},{"asset":"LRC","free":"0.00000000","locked":"0.00000000"},{"asset":"TRX","free":"0.88800000","locked":"0.00000000"},{"asset":"SNGLS","free":"0.00000000","locked":"0.00000000"},{"asset":"STRAT","free":"0.00000000","locked":"0.00000000"},{"asset":"BQX","free":"0.00000000","locked":"0.00000000"},{"asset":"FUN","free":"0.00000000","locked":"0.00000000"},{"asset":"KNC","free":"0.00000000","locked":"0.00000000"},{"asset":"CDT","free":"0.00000000","locked":"0.00000000"},{"asset":"XVG","free":"0.00000000","locked":"0.00000000"},{"asset":"IOTA","free":"7.86900000","locked":"0.00000000"},{"asset":"SNM","free":"0.77200000","locked":"0.00000000"},{"asset":"LINK","free":"0.00000000","locked":"0.00000000"},{"asset":"CVC","free":"0.00100000","locked":"0.00000000"},{"asset":"TNT","free":"0.00000000","locked":"0.00000000"},{"asset":"REP","free":"0.00000000","locked":"0.00000000"},{"asset":"MDA","free":"0.00000000","locked":"0.00000000"},{"asset":"MTL","free":"0.00000000","locked":"0.00000000"},{"asset":"SALT","free":"0.00000000","locked":"0.00000000"},{"asset":"NULS","free":"0.00000000","locked":"0.00000000"},{"asset":"SUB","free":"0.00000000","locked":"0.00000000"},{"asset":"STX","free":"0.00000000","locked":"0.00000000"},{"asset":"MTH","free":"0.00000000","locked":"0.00000000"},{"asset":"ADX","free":"0.00000000","locked":"0.00000000"},{"asset":"ETC","free":"0.00920000","locked":"0.00000000"},{"asset":"ENG","free":"0.00000000","locked":"0.00000000"},{"asset":"ZEC","free":"0.00000000","locked":"0.00000000"},{"asset":"AST","free":"0.00000000","locked":"0.00000000"},{"asset":"GNT","free":"0.00000000","locked":"0.00000000"},{"asset":"DGD","free":"0.00000000","locked":"0.00000000"},{"asset":"BAT","free":"0.00000000","locked":"0.00000000"},{"asset":"DASH","free":"0.04274700","locked":"0.00000000"},{"asset":"POWR","free":"0.00000000","locked":"0.00000000"},{"asset":"BTG","free":"0.00000000","locked":"0.00000000"},{"asset":"REQ","free":"0.00000000","locked":"0.00000000"},{"asset":"XMR","free":"0.00000600","locked":"0.24975000"},{"asset":"EVX","free":"0.00000000","locked":"0.00000000"},{"asset":"VIB","free":"0.00000000","locked":"0.00000000"},{"asset":"ENJ","free":"0.00000000","locked":"0.00000000"},{"asset":"VEN","free":"0.00000000","locked":"0.00000000"},{"asset":"ARK","free":"0.00000000","locked":"0.00000000"},{"asset":"XRP","free":"0.91700000","locked":"0.00000000"},{"asset":"MOD","free":"0.00000000","locked":"0.00000000"},{"asset":"STORJ","free":"0.00000000","locked":"0.00000000"},{"asset":"KMD","free":"0.00000000","locked":"0.00000000"},{"asset":"RCN","free":"0.00000000","locked":"0.00000000"},{"asset":"EDO","free":"0.00000000","locked":"0.00000000"},{"asset":"DATA","free":"0.00000000","locked":"0.00000000"},{"asset":"DLT","free":"0.00000000","locked":"0.00000000"},{"asset":"MANA","free":"0.00000000","locked":"0.00000000"},{"asset":"PPT","free":"0.00000000","locked":"0.00000000"},{"asset":"RDN","free":"0.00000000","locked":"0.00000000"},{"asset":"GXS","free":"0.00000000","locked":"0.00000000"},{"asset":"AMB","free":"0.00000000","locked":"0.00000000"},{"asset":"ARN","free":"0.00000000","locked":"0.00000000"},{"asset":"BCPT","free":"0.00000000","locked":"0.00000000"},{"asset":"CND","free":"0.00000000","locked":"0.00000000"},{"asset":"GVT","free":"0.00000000","locked":"0.00000000"},{"asset":"POE","free":"0.00000000","locked":"0.00000000"},{"asset":"BTS","free":"0.00000000","locked":"0.00000000"},{"asset":"FUEL","free":"0.00000000","locked":"0.00000000"},{"asset":"XZC","free":"0.00000000","locked":"0.00000000"},{"asset":"QSP","free":"0.00000000","locked":"0.00000000"},{"asset":"LSK","free":"0.00000000","locked":"0.00000000"},{"asset":"BCD","free":"0.00012800","locked":"0.00000000"},{"asset":"TNB","free":"0.00000000","locked":"0.00000000"},{"asset":"ADA","free":"20.95800000","locked":"0.00000000"},{"asset":"LEND","free":"0.00000000","locked":"0.00000000"},{"asset":"XLM","free":"0.57800000","locked":"0.00000000"},{"asset":"CMT","free":"0.00000000","locked":"0.00000000"},{"asset":"WAVES","free":"0.00000000","locked":"0.00000000"},{"asset":"WABI","free":"0.00000000","locked":"0.00000000"},{"asset":"GTO","free":"0.00000000","locked":"0.00000000"},{"asset":"ICX","free":"0.00000000","locked":"0.00000000"},{"asset":"OST","free":"0.00000000","locked":"0.00000000"},{"asset":"ELF","free":"0.00000000","locked":"0.00000000"},{"asset":"AION","free":"0.00000000","locked":"0.00000000"},{"asset":"WINGS","free":"0.00000000","locked":"0.00000000"},{"asset":"BRD","free":"0.00000000","locked":"0.00000000"},{"asset":"NEBL","free":"0.00000000","locked":"0.00000000"},{"asset":"NAV","free":"0.00000000","locked":"0.00000000"},{"asset":"VIBE","free":"0.00000000","locked":"0.00000000"},{"asset":"LUN","free":"0.00000000","locked":"0.00000000"},{"asset":"TRIG","free":"0.00000000","locked":"0.00000000"},{"asset":"APPC","free":"0.00000000","locked":"0.00000000"},{"asset":"CHAT","free":"0.00000000","locked":"0.00000000"},{"asset":"RLC","free":"0.00000000","locked":"0.00000000"},{"asset":"INS","free":"0.00000000","locked":"0.00000000"},{"asset":"PIVX","free":"0.00000000","locked":"0.00000000"},{"asset":"IOST","free":"0.02300000","locked":"0.00000000"},{"asset":"STEEM","free":"0.00000000","locked":"0.00000000"},{"asset":"NANO","free":"0.00000000","locked":"0.00000000"},{"asset":"AE","free":"0.00315000","locked":"0.00000000"},{"asset":"VIA","free":"0.00000000","locked":"0.00000000"},{"asset":"BLZ","free":"0.00000000","locked":"0.00000000"},{"asset":"SYS","free":"0.00000000","locked":"0.00000000"},{"asset":"RPX","free":"0.00000000","locked":"0.00000000"},{"asset":"NCASH","free":"0.00000000","locked":"0.00000000"},{"asset":"POA","free":"0.00000000","locked":"0.00000000"},{"asset":"ONT","free":"0.00000000","locked":"0.00000000"},{"asset":"ZIL","free":"0.00000000","locked":"0.00000000"},{"asset":"STORM","free":"0.00000000","locked":"0.00000000"},{"asset":"XEM","free":"0.00000000","locked":"0.00000000"},{"asset":"WAN","free":"0.00000000","locked":"0.00000000"},{"asset":"WPR","free":"0.00000000","locked":"0.00000000"},{"asset":"QLC","free":"0.00000000","locked":"0.00000000"},{"asset":"GRS","free":"0.00000000","locked":"0.00000000"},{"asset":"CLOAK","free":"0.00000000","locked":"0.00000000"},{"asset":"LOOM","free":"0.00000000","locked":"0.00000000"},{"asset":"BCN","free":"0.00000000","locked":"0.00000000"},{"asset":"TUSD","free":"0.00000000","locked":"0.00000000"},{"asset":"ZEN","free":"0.00100000","locked":"0.00000000"},{"asset":"SKY","free":"0.00000000","locked":"0.00000000"},{"asset":"THETA","free":"0.00000000","locked":"0.00000000"},{"asset":"IOTX","free":"0.00000000","locked":"0.00000000"},{"asset":"QKC","free":"0.00000000","locked":"0.00000000"},{"asset":"AGI","free":"0.00000000","locked":"0.00000000"},{"asset":"NXS","free":"0.00000000","locked":"0.00000000"},{"asset":"SC","free":"0.00000000","locked":"0.00000000"},{"asset":"NPXS","free":"0.00000000","locked":"0.00000000"},{"asset":"KEY","free":"0.00000000","locked":"0.00000000"},{"asset":"NAS","free":"0.00000000","locked":"0.00000000"},{"asset":"MFT","free":"0.00000000","locked":"0.00000000"},{"asset":"DENT","free":"0.00000000","locked":"0.00000000"},{"asset":"ARDR","free":"0.00000000","locked":"0.00000000"},{"asset":"HOT","free":"0.00000000","locked":"0.00000000"},{"asset":"VET","free":"0.00000000","locked":"0.00000000"},{"asset":"DOCK","free":"0.00000000","locked":"0.00000000"},{"asset":"POLY","free":"0.00000000","locked":"0.00000000"},{"asset":"ONG","free":"0.00000000","locked":"0.00000000"},{"asset":"PHX","free":"0.00000000","locked":"0.00000000"},{"asset":"HC","free":"0.00000000","locked":"0.00000000"},{"asset":"GO","free":"0.00000000","locked":"0.00000000"},{"asset":"PAX","free":"0.00000000","locked":"0.00000000"},{"asset":"RVN","free":"0.00000000","locked":"0.00000000"},{"asset":"DCR","free":"0.00000000","locked":"0.00000000"},{"asset":"USDC","free":"0.00000000","locked":"0.00000000"},{"asset":"MITH","free":"0.00000000","locked":"0.00000000"},{"asset":"BCHABC","free":"0.00000000","locked":"0.00000000"},{"asset":"BCHSV","free":"0.00060400","locked":"0.00000000"},{"asset":"REN","free":"0.00000000","locked":"0.00000000"},{"asset":"BTT","free":"0.00000000","locked":"0.00000000"},{"asset":"USDS","free":"0.00000000","locked":"0.00000000"},{"asset":"FET","free":"0.00000000","locked":"0.00000000"},{"asset":"TFUEL","free":"0.00000000","locked":"0.00000000"},{"asset":"CELR","free":"0.00000000","locked":"0.00000000"},{"asset":"MATIC","free":"0.00000000","locked":"0.00000000"},{"asset":"ATOM","free":"0.00000000","locked":"0.00000000"},{"asset":"PHB","free":"0.75700000","locked":"0.00000000"},{"asset":"ONE","free":"0.00000000","locked":"0.00000000"},{"asset":"FTM","free":"0.00000000","locked":"0.00000000"},{"asset":"BTCB","free":"0.00000000","locked":"0.00000000"},{"asset":"USDSB","free":"0.00000000","locked":"0.00000000"},{"asset":"CHZ","free":"0.00000000","locked":"0.00000000"},{"asset":"COS","free":"0.00000000","locked":"0.00000000"},{"asset":"ALGO","free":"0.00000000","locked":"0.00000000"},{"asset":"ERD","free":"0.00000000","locked":"0.00000000"},{"asset":"DOGE","free":"0.00000000","locked":"0.00000000"},{"asset":"BGBP","free":"0.00000000","locked":"0.00000000"},{"asset":"DUSK","free":"0.00000000","locked":"0.00000000"},{"asset":"ANKR","free":"0.00000000","locked":"0.00000000"},{"asset":"WIN","free":"0.00000000","locked":"0.00000000"},{"asset":"TUSDB","free":"0.00000000","locked":"0.00000000"},{"asset":"COCOS","free":"0.00000000","locked":"0.00000000"},{"asset":"PERL","free":"0.00000000","locked":"0.00000000"},{"asset":"TOMO","free":"0.00000000","locked":"0.00000000"},{"asset":"BUSD","free":"0.00000000","locked":"0.00000000"},{"asset":"BAND","free":"0.00000000","locked":"0.00000000"},{"asset":"BEAM","free":"0.00000000","locked":"0.00000000"},{"asset":"HBAR","free":"0.00000000","locked":"0.00000000"},{"asset":"XTZ","free":"0.03229919","locked":"9.99000000"},{"asset":"NGN","free":"0.00000000","locked":"0.00000000"},{"asset":"NKN","free":"0.00000000","locked":"0.00000000"},{"asset":"EUR","free":"0.00000000","locked":"0.00000000"},{"asset":"KAVA","free":"0.00000000","locked":"0.00000000"},{"asset":"RUB","free":"0.00000000","locked":"0.00000000"},{"asset":"ARPA","free":"0.00000000","locked":"0.00000000"},{"asset":"TRY","free":"0.00000000","locked":"0.00000000"},{"asset":"CTXC","free":"0.00000000","locked":"0.00000000"},{"asset":"BCH","free":"0.00060400","locked":"0.00000000"},{"asset":"TROY","free":"0.00000000","locked":"0.00000000"},{"asset":"VITE","free":"0.00000000","locked":"0.00000000"},{"asset":"FTT","free":"0.00000000","locked":"0.00000000"},{"asset":"OGN","free":"0.00000000","locked":"0.00000000"},{"asset":"DREP","free":"0.00000000","locked":"0.00000000"},{"asset":"BULL","free":"0.00000000","locked":"0.00000000"},{"asset":"BEAR","free":"0.00000000","locked":"0.00000000"},{"asset":"ETHBULL","free":"0.00000000","locked":"0.00000000"},{"asset":"ETHBEAR","free":"0.00000000","locked":"0.00000000"},{"asset":"XRPBULL","free":"0.00000000","locked":"0.00000000"},{"asset":"XRPBEAR","free":"0.00000000","locked":"0.00000000"},{"asset":"EOSBULL","free":"0.00000000","locked":"0.00000000"},{"asset":"EOSBEAR","free":"0.00000000","locked":"0.00000000"},{"asset":"TCT","free":"0.00000000","locked":"0.00000000"},{"asset":"WRX","free":"0.00000000","locked":"0.00000000"},{"asset":"LTO","free":"0.00000000","locked":"0.00000000"},{"asset":"ZAR","free":"0.00000000","locked":"0.00000000"},{"asset":"MBL","free":"0.00000000","locked":"0.00000000"},{"asset":"COTI","free":"0.00000000","locked":"0.00000000"},{"asset":"BKRW","free":"0.00000000","locked":"0.00000000"},{"asset":"BNBBULL","free":"0.00000000","locked":"0.00000000"},{"asset":"BNBBEAR","free":"0.00000000","locked":"0.00000000"},{"asset":"STPT","free":"0.00000000","locked":"0.00000000"}]}
     */
    public static BigDecimal parseAssetBalanceFree(final String jsonString, final String asset) throws IOException {
        GateUtils.checkParamNotEmpty(asset, "asset");

        for (JsonNode balanceNode : mapper.readTree(jsonString).get("balances")) {
            if (asset.equals(balanceNode.get("asset").asText())) {
                return new BigDecimal(balanceNode.get("free").asText());
            }
        }
        return null;
    }

    /**
     * {"lastUpdateId":3171106213,"bids":[["7364.90000000","7.00000000"],["7364.69000000","4.16569300"],["7364.53000000","0.00452200"],["7363.58000000","4.00000000"],["7363.57000000","0.00950400"],["7363.42000000","0.19356900"],["7363.21000000","1.91276100"],["7363.01000000","4.00000000"],["7362.72000000","0.13574400"],["7362.30000000","0.30020200"],["7362.21000000","0.13577800"],["7362.16000000","0.01628600"],["7361.99000000","0.20000000"],["7361.98000000","0.29100000"],["7361.18000000","0.49995500"],["7361.12000000","0.05029000"],["7360.53000000","0.20000000"],["7360.16000000","0.01502600"],["7360.15000000","0.30600000"],["7360.06000000","0.01000000"],["7359.96000000","0.12600000"],["7359.92000000","0.40400000"],["7359.84000000","4.20000000"],["7359.68000000","0.00288000"],["7359.34000000","0.04250000"],["7358.58000000","0.27179200"],["7358.57000000","2.56262700"],["7358.54000000","0.29700000"],["7358.39000000","0.30154400"],["7358.23000000","0.10000000"],["7358.15000000","0.39200000"],["7358.04000000","0.08394200"],["7358.00000000","0.00291000"],["7356.97000000","0.07713000"],["7356.78000000","0.29400000"],["7356.58000000","0.15699300"],["7356.57000000","0.39200000"],["7356.54000000","0.56216900"],["7356.50000000","0.00297000"],["7356.13000000","0.23530900"],["7356.05000000","0.76287600"],["7356.04000000","0.61118400"],["7356.01000000","0.31230900"],["7356.00000000","4.39587100"],["7355.11000000","0.15606400"],["7355.08000000","0.39600000"],["7354.92000000","4.62300000"],["7354.72000000","3.10000000"],["7354.66000000","0.00300000"],["7354.05000000","0.12633900"],["7353.95000000","0.59743700"],["7353.91000000","0.00800000"],["7353.82000000","0.34482100"],["7353.58000000","0.10199400"],["7353.50000000","0.00310900"],["7353.42000000","0.40800000"],["7353.32000000","0.04137700"],["7353.11000000","0.02361200"],["7353.06000000","0.00290200"],["7353.00000000","1.29267800"],["7352.92000000","1.00000000"],["7352.75000000","0.26667900"],["7352.74000000","0.33336600"],["7352.60000000","0.04080200"],["7352.59000000","0.30300000"],["7352.02000000","0.27203400"],["7352.01000000","0.08585900"],["7352.00000000","0.66200000"],["7351.65000000","0.02000000"],["7351.59000000","0.34006200"],["7351.51000000","0.53150000"],["7351.50000000","0.05250800"],["7351.49000000","0.06720000"],["7351.45000000","0.01810000"],["7351.31000000","0.16542600"],["7351.12000000","0.04712700"],["7350.94000000","0.26676600"],["7350.52000000","0.00398300"],["7350.46000000","3.51300000"],["7350.41000000","0.04721800"],["7350.35000000","8.40000000"],["7350.25000000","0.31279100"],["7350.18000000","0.10000000"],["7350.07000000","0.13586600"],["7350.01000000","0.08080200"],["7350.00000000","1.07386500"],["7349.95000000","0.21254200"],["7349.37000000","0.34016000"],["7349.22000000","0.60172500"],["7349.00000000","0.04287700"],["7348.68000000","6.35525900"],["7348.46000000","0.02361600"],["7348.00000000","0.04199600"],["7347.99000000","0.01021400"],["7347.45000000","0.00155400"],["7347.41000000","0.31213100"],["7347.07000000","0.07080200"],["7347.00000000","0.09273400"],["7346.71000000","0.00160000"],["7346.67000000","0.51410000"]],"asks":[["7364.91000000","0.13308600"],["7365.00000000","2.16937800"],["7365.34000000","0.10000000"],["7365.40000000","0.00420200"],["7366.00000000","0.00150000"],["7366.33000000","0.04250000"],["7366.53000000","0.08148900"],["7366.54000000","0.14586600"],["7366.66000000","0.04716800"],["7366.70000000","0.00219500"],["7366.99000000","0.40934500"],["7367.00000000","0.00150000"],["7367.03000000","0.41156000"],["7367.05000000","0.27147900"],["7367.10000000","0.00369700"],["7367.21000000","0.00160000"],["7367.41000000","0.10137300"],["7367.42000000","0.04072000"],["7367.43000000","0.03062300"],["7367.56000000","0.00671100"],["7367.84000000","0.30024100"],["7367.89000000","0.30020200"],["7367.96000000","0.00160000"],["7367.98000000","0.00160000"],["7368.00000000","0.05150000"],["7368.19000000","0.13634300"],["7368.20000000","0.07250000"],["7368.36000000","0.03000000"],["7368.63000000","0.29700000"],["7368.67000000","0.17816900"],["7368.73000000","0.13576900"],["7368.78000000","0.00150000"],["7368.96000000","0.13179600"],["7369.00000000","0.00150000"],["7369.14000000","0.00150000"],["7369.23000000","0.08000000"],["7369.50000000","0.00150000"],["7369.56000000","0.04719600"],["7369.57000000","0.00275400"],["7369.83000000","0.33567500"],["7369.85000000","0.00150000"],["7369.88000000","1.39524200"],["7369.89000000","0.95260300"],["7369.90000000","0.29762700"],["7369.91000000","0.65796900"],["7369.92000000","0.14158900"],["7369.99000000","0.20094600"],["7370.00000000","6.54163800"],["7370.21000000","0.00150000"],["7370.25000000","0.29400000"],["7370.39000000","0.01358600"],["7370.43000000","0.00679500"],["7370.50000000","0.33953500"],["7370.57000000","0.00150000"],["7370.77000000","0.00407000"],["7370.88000000","0.13577000"],["7370.89000000","0.01357800"],["7370.92000000","0.00150000"],["7371.15000000","0.02359700"],["7371.22000000","0.00479900"],["7371.28000000","0.21392300"],["7371.34000000","2.00000000"],["7371.35000000","0.06483700"],["7371.57000000","0.00150000"],["7371.64000000","0.00150000"],["7372.00000000","2.05221500"],["7372.03000000","0.30982600"],["7372.35000000","0.00150000"],["7372.59000000","0.40800000"],["7372.61000000","0.00162100"],["7372.71000000","0.00150000"],["7372.72000000","0.00174300"],["7372.76000000","0.00952700"],["7372.81000000","0.06664600"],["7372.90000000","0.19991600"],["7372.99000000","0.27126000"],["7373.00000000","1.00000000"],["7373.03000000","4.20000000"],["7373.06000000","0.33323800"],["7373.07000000","0.00150000"],["7373.19000000","0.00877700"],["7373.33000000","0.50000000"],["7373.41000000","0.01000000"],["7373.42000000","0.00150000"],["7373.46000000","0.00290200"],["7373.78000000","0.00150000"],["7373.86000000","0.55205300"],["7374.00000000","0.64043100"],["7374.13000000","0.00172200"],["7374.14000000","0.00150000"],["7374.21000000","0.00487500"],["7374.33000000","0.00534700"],["7374.44000000","0.15700000"],["7374.48000000","0.01126700"],["7374.50000000","0.00150000"],["7374.52000000","0.00678500"],["7374.58000000","0.20000000"],["7374.72000000","0.00273900"],["7374.80000000","0.00282000"],["7374.85000000","0.00150000"]]}
     */
    private static List<OrderbookEntry> parseOrderBookByBookType(String jsonString, String bookType, int limit) throws IOException {
        List<OrderbookEntry> entryList = new ArrayList<>(limit);
        int i = 0;
        for (JsonNode entry : mapper.readTree(jsonString).get(bookType)) {
            i++;
            entryList.add(new OrderbookEntry(
                    new BigDecimal(entry.get(0).asText()), new BigDecimal(entry.get(1).asText())));
            if (i == limit) {
                break;
            }
        }

        return entryList;
    }

    public static List<OrderbookEntry> parseOrderbookAsk(String jsonString, int limit) throws IOException {
        return parseOrderBookByBookType(jsonString, "asks", limit);
    }

    public static List<OrderbookEntry> parseOrderbookBid(String jsonString, int limit) throws IOException {
        return parseOrderBookByBookType(jsonString, "bids", limit);
    }

    /**
     * {"symbol":"BTCUSDT","orderId":1869476469,"orderListId":-1,"clientOrderId":"LFp8Sxzt81BZhxVg0a8LXs","transactTime":1587237988189,"price":"6215.02000000","origQty":"0.00200000","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"BUY","fills":[]}
     */
    public static Order parseCreateOrderResponse(String jsonString) throws IOException {
        return parseOrder(mapper.readTree(jsonString), "transactTime");
    }

    /**
     * {"symbol":"BTCUSDT","orderId":1876373751,"orderListId":-1,"clientOrderId":"Eqjda89LX2QUWNfbkwRMQR","price":"6122.09000000","origQty":"0.00200000","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"BUY","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1587313870739,"updateTime":1587313870739,"isWorking":true,"origQuoteOrderQty":"0.00000000"}
     */
    public static String parseCheckOrderStatusResponse(String jsonString) {
        return parseStatus(jsonString);
    }

    /**
     * {"symbol":"BTCUSDT","origClientOrderId":"LFp8Sxzt81BZhxVg0a8LXs","orderId":1869476469,"orderListId":-1,"clientOrderId":"RTVrvzNrvItZoAK6TPgm8o","price":"6215.02000000","origQty":"0.00200000","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"CANCELED","timeInForce":"GTC","type":"LIMIT","side":"BUY"}
     */
    public static String parseCancelOrderResponse(String jsonString) {
        return parseStatus(jsonString);
    }

    private static String parseStatus(String inputString) {
        Pattern pattern = Pattern.compile("\"status\":\"([A-Z]+)\",");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


    private static Order parseOrder(JsonNode orderNode, String createTimeNodeName) {
        return new Order("binance",
                orderNode.get("symbol").asText(),
                orderNode.get("clientOrderId").asText(),
                new BigDecimal(orderNode.get("price").asText()),
                new BigDecimal(orderNode.get("origQty").asText()),
                orderNode.get("status").asText(),
                orderNode.get(createTimeNodeName).asLong(),
                orderNode.get("side").asText(),
                orderNode.get("type").asText());
    }

}
