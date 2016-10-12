package chainn.com.wifidetector.wifi.model;

/**
 * Created by xuchen on 16/10/12.
 */
import android.support.annotation.NonNull;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class WiFiUtils {

    private static final double DISTANCE_MHZ_M = 27.55;
    private static final int MIN_RSSI = -100;
    private static final int MAX_RSSI = -55;
    private static final String QUOTE = "\"";

    public static double calculateDistance(int frequency, int level) {
        return Math.pow(10.0, (DISTANCE_MHZ_M - (20 * Math.log10(frequency)) + Math.abs(level)) / 20.0);
    }

    public static int calculateSignalLevel(int rssi, int numLevels) {
        if (rssi <= MIN_RSSI) {
            return 0;
        }
        if (rssi >= MAX_RSSI) {
            return numLevels - 1;
        }
        return (rssi - MIN_RSSI) * (numLevels - 1) / (MAX_RSSI - MIN_RSSI);
    }

    public static String convertSSID(@NonNull String SSID) {
        return StringUtils.removeEnd(StringUtils.removeStart(SSID, QUOTE), QUOTE);
    }

    public static String convertIpAddress(int ipAddress) {
        byte[] bytes = BigInteger.valueOf(ipAddress).toByteArray();
        ArrayUtils.reverse(bytes);
        try {
            return InetAddress.getByAddress(bytes).getHostAddress();
        } catch (UnknownHostException e) {
            return StringUtils.EMPTY;
        }
    }

}