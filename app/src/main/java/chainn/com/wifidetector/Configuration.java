package chainn.com.wifidetector;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import chainn.com.wifidetector.wifi.band.WiFiChannel;
import chainn.com.wifidetector.wifi.band.WiFiChannels;

public class Configuration {
    private final boolean developmentMode;
    private final boolean largeScreenLayout;
    private Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;

    public Configuration(boolean largeScreenLayout, boolean developmentMode) {
        this.largeScreenLayout = largeScreenLayout;
        this.developmentMode = developmentMode;
        setWiFiChannelPair(WiFiChannels.UNKNOWN);
    }

    public boolean isLargeScreenLayout() {
        return largeScreenLayout;
    }

    public Pair<WiFiChannel, WiFiChannel> getWiFiChannelPair() {
        return wiFiChannelPair;
    }

    public void setWiFiChannelPair(@NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        this.wiFiChannelPair = wiFiChannelPair;
    }

    public boolean isDevelopmentMode() {
        return developmentMode;
    }
}
