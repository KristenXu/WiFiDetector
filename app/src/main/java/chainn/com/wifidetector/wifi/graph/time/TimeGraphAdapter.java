package chainn.com.wifidetector.wifi.graph.time;

import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.graph.tools.GraphAdapter;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewNotifier;

import java.util.ArrayList;
import java.util.List;

class TimeGraphAdapter extends GraphAdapter {
    TimeGraphAdapter() {
        super(makeGraphViewNotifiers());
    }

    private static List<GraphViewNotifier> makeGraphViewNotifiers() {
        List<GraphViewNotifier> graphViewNotifiers = new ArrayList<>();
        for (WiFiBand wiFiBand : WiFiBand.values()) {
            graphViewNotifiers.add(new TimeGraphView(wiFiBand));
        }
        return graphViewNotifiers;
    }
}
