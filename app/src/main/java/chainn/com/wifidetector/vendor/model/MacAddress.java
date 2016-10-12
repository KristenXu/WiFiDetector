package chainn.com.wifidetector.vendor.model;

/**
 * Created by xuchen on 16/10/12.
 */

import android.support.annotation.NonNull;

class MacAddress {
    private static final int MAX_LEN = 6;

    private MacAddress() {
    }

    static String clean(@NonNull String macAddress) {
        String result = macAddress.replace(":", "");
        return result.substring(0, Math.min(result.length(), MAX_LEN)).toUpperCase();
    }

}
