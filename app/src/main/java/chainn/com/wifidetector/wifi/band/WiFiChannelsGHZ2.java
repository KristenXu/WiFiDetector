package chainn.com.wifidetector.wifi.band;

/**
 * Created by xuchen on 16/10/12.
 */


import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class WiFiChannelsGHZ2 extends WiFiChannels {
    private static final Pair<Integer, Integer> RANGE = new Pair<>(2400, 2499);
    private static final List<Pair<WiFiChannel, WiFiChannel>> SETS = Arrays.asList(
        new Pair<>(new WiFiChannel(1, 2412), new WiFiChannel(13, 2472)),
        new Pair<>(new WiFiChannel(14, 2484), new WiFiChannel(14, 2484)));
    private static final Pair<WiFiChannel, WiFiChannel> SET = new Pair<>(SETS.get(0).first, SETS.get(SETS.size() - 1).second);
    private static final int FREQUENCY_OFFSET = WiFiChannel.FREQUENCY_SPREAD * 2;
    private static final int FREQUENCY_SPREAD = WiFiChannel.FREQUENCY_SPREAD;

    WiFiChannelsGHZ2() {
        super(RANGE, SETS, FREQUENCY_OFFSET, FREQUENCY_SPREAD);
    }

    @Override
    public List<Pair<WiFiChannel, WiFiChannel>> getWiFiChannelPairs() {
        return Arrays.asList(SET);
    }

    @Override
    public Pair<WiFiChannel, WiFiChannel> getWiFiChannelPairFirst(String countryCode) {
        return SET;
    }

    @Override
    public List<WiFiChannel> getAvailableChannels(String countryCode) {
        List<WiFiChannel> wiFiChannels = new ArrayList<>();
        for (int channel : WiFiChannelCountry.find(countryCode).getChannelsGHZ2()) {
            wiFiChannels.add(getWiFiChannelByChannel(channel));
        }
        return wiFiChannels;
    }

    @Override
    public boolean isChannelAvailable(String countryCode, int channel) {
        return WiFiChannelCountry.find(countryCode).isChannelAvailableGHZ2(channel);
    }

    @Override
    public WiFiChannel getWiFiChannelByFrequency(int frequency, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        return isInRange(frequency) ? getWiFiChannel(frequency, SET) : WiFiChannel.UNKNOWN;
    }

}

