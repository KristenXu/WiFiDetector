package chainn.com.wifidetector.vendor.model;

/**
 * Created by xuchen on 16/10/12.
 */

import org.apache.commons.lang3.StringUtils;

class VendorNameUtils {
    private static final int VENDOR_NAME_MAX = 50;

    static String cleanVendorName(String name) {
        if (StringUtils.isBlank(name) || name.contains("<") || name.contains(">")) {
            return StringUtils.EMPTY;
        }
        String result = name
            .replaceAll("[^a-zA-Z0-9]", " ")
            .replaceAll(" +", " ")
            .trim()
            .toUpperCase();

        return result.substring(0, Math.min(result.length(), VENDOR_NAME_MAX));
    }
}
