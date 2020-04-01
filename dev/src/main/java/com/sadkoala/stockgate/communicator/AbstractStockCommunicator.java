package com.sadkoala.stockgate.communicator;

import com.sadkoala.stockgate.GateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public abstract class AbstractStockCommunicator {

    private final Logger log = LogManager.getLogger(AbstractStockCommunicator.class.getName());

    /*
    public static IStockCommunicator createCommunicator(String stockShortName) {
        if (STOCK_BINANCE_MARKER.equals(stockShortName)) {
            return new BinanceCommunicator();
        } else if (STOCK_HITBTC_MARKER.equals(stockShortName)) {
            return null;// new HitbtcCommunicator();
        } else if (STOCK_OKEX_MARKER.equals(stockShortName)) {
            return null;
        } else {
            throw new NotImplementedException(String.format("Нет имплементации коммуникатора для биржи [%s]", stockShortName));
        }
    }
    */

    protected static String encodeHmac256(String message, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        GateUtils.checkParamEmpty(message, "message");
        GateUtils.checkParamEmpty(key, "key");

        String hmacSha256_Algo = "HmacSHA256";
        Mac sha256_HMAC = Mac.getInstance(hmacSha256_Algo);
        sha256_HMAC.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), hmacSha256_Algo));
        return bytesToHex(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    protected static String encodeBase64(String text) {
        return new String(Base64.getEncoder().encode(text.getBytes(StandardCharsets.UTF_8)));
    }

}
