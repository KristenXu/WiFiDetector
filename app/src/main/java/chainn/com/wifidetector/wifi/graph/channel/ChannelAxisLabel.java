package chainn.com.wifidetector.wifi.graph.channel;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.band.WiFiChannel;
import chainn.com.wifidetector.wifi.band.WiFiChannels;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewBuilder;

import org.apache.commons.lang3.StringUtils;

class ChannelAxisLabel implements LabelFormatter {
    private final WiFiBand wiFiBand;
    private final Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;

    ChannelAxisLabel(@NonNull WiFiBand wiFiBand, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        this.wiFiBand = wiFiBand;
        this.wiFiChannelPair = wiFiChannelPair;
    }

    @Override
    public String formatLabel(double value, boolean isValueX) {
        String result = StringUtils.EMPTY;

        int valueAsInt = (int) (value + (value < 0 ? -0.5 : 0.5));
        if (isValueX) {
            result += findChannel(valueAsInt);
        } else {
            if (valueAsInt <= GraphViewBuilder.MAX_Y && valueAsInt > GraphViewBuilder.MIN_Y) {
                result += valueAsInt;
            }
        }
        return result;
    }

    @Override
    public void setViewport(Viewport viewport) {
        // ignore
    }

    private String findChannel(int value) {
        WiFiChannels wiFiChannels = wiFiBand.getWiFiChannels();
        WiFiChannel wiFiChannel = wiFiChannels.getWiFiChannelByFrequency(value, wiFiChannelPair);
        if (wiFiChannel == WiFiChannel.UNKNOWN) {
            return StringUtils.EMPTY;
        }

        int channel = wiFiChannel.getChannel();
        Settings settings = MainContext.INSTANCE.getSettings();
        String countryCode = settings.getCountryCode();
        if (!wiFiChannels.isChannelAvailable(countryCode, channel)) {
            return StringUtils.EMPTY;
        }
        return "" + channel;
    }

}
