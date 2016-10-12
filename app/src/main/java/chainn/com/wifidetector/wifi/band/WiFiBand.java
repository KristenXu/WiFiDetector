package chainn.com.wifidetector.wifi.band;

/**
 * Created by xuchen on 16/10/12.
 */

import android.support.annotation.NonNull;

public enum WiFiBand {
    GHZ2("2.4 GHz", new WiFiChannelsGHZ2()),
    GHZ5("5 GHz", new WiFiChannelsGHZ5());

    private final String band;
    private final WiFiChannels wiFiChannels;

    WiFiBand(@NonNull String band, @NonNull WiFiChannels wiFiChannels) {
        this.band = band;
        this.wiFiChannels = wiFiChannels;
    }

    public static WiFiBand findByFrequency(int frequency) {
        for (WiFiBand wiFiBand : WiFiBand.values()) {
            if (wiFiBand.getWiFiChannels().isInRange(frequency)) {
                return wiFiBand;
            }
        }
        return WiFiBand.GHZ2;
    }

    public static WiFiBand find(int index) {
        if (index < 0 || index >= values().length) {
            return GHZ2;
        }
        return values()[index];
    }

    public String getBand() {
        return band;
    }

    public WiFiBand toggle() {
        return isGHZ5() ? WiFiBand.GHZ2 : WiFiBand.GHZ5;
    }

    public boolean isGHZ5() {
        return WiFiBand.GHZ5.equals(this);
    }

    public WiFiChannels getWiFiChannels() {
        return wiFiChannels;
    }
}
