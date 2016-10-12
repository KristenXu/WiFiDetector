package chainn.com.wifidetector.wifi.model;

/**
 * Created by xuchen on 16/10/12.
 */

import chainn.com.wifidetector.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public enum Security {
    // weak getSecurity first - keep this order
    NONE(R.drawable.ic_lock_open_black_18dp),
    WPS(R.drawable.ic_lock_outline_black_18dp),
    WEP(R.drawable.ic_lock_outline_black_18dp),
    WPA(R.drawable.ic_lock_black_18dp),
    WPA2(R.drawable.ic_lock_black_18dp);

    private final int imageResource;

    Security(int imageResource) {
        this.imageResource = imageResource;
    }

    public static List<Security> findAll(String capabilities) {
        Set<Security> results = new TreeSet<>();
        if (capabilities != null) {
            String[] values = capabilities.toUpperCase()
                    .replace("][", "-").replace("]", "").replace("[", "").split("-");
            for (String value : values) {
                try {
                    results.add(Security.valueOf(value));
                } catch (Exception e) {
                    // skip getCapabilities that are not getSecurity
                }
            }
        }
        return new ArrayList<>(results);
    }

    public static Security findOne(String capabilities) {
        List<Security> securities = findAll(capabilities);
        for (Security security : Security.values()) {
            if (securities.contains(security)) {
                return security;
            }
        }
        return Security.NONE;
    }

    public int imageResource() {
        return imageResource;
    }

}