package chainn.com.wifidetector.wifi.graph.channel;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.band.WiFiChannel;
import chainn.com.wifidetector.wifi.graph.tools.GraphAdapter;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewNotifier;
import chainn.com.wifidetector.wifi.model.WiFiData;

import java.util.ArrayList;
import java.util.List;

class ChannelGraphAdapter extends GraphAdapter {
    private final ChannelGraphNavigation channelGraphNavigation;

    ChannelGraphAdapter(@NonNull ChannelGraphNavigation channelGraphNavigation) {
        super(makeGraphViewNotifiers());
        this.channelGraphNavigation = channelGraphNavigation;
    }

    private static List<GraphViewNotifier> makeGraphViewNotifiers() {
        List<GraphViewNotifier> graphViewNotifiers = new ArrayList<>();
        for (WiFiBand wiFiBand : WiFiBand.values()) {
            for (Pair<WiFiChannel, WiFiChannel> wiFiChannelPair : wiFiBand.getWiFiChannels().getWiFiChannelPairs()) {
                graphViewNotifiers.add(new ChannelGraphView(wiFiBand, wiFiChannelPair));
            }
        }
        return graphViewNotifiers;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        super.update(wiFiData);
        channelGraphNavigation.update();
    }
}
