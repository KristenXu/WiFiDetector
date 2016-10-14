package chainn.com.wifidetector.wifi.graph.tools;

import android.support.annotation.NonNull;

import com.jjoe64.graphview.GraphView;
import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.wifi.scanner.UpdateNotifier;

import java.util.ArrayList;
import java.util.List;

public class GraphAdapter implements UpdateNotifier {
    private final List<GraphViewNotifier> graphViewNotifiers;

    public GraphAdapter(@NonNull List<GraphViewNotifier> graphViewNotifiers) {
        this.graphViewNotifiers = graphViewNotifiers;
    }

    public List<GraphView> getGraphViews() {
        List<GraphView> graphViews = new ArrayList<>();
        for (GraphViewNotifier graphViewNotifier : graphViewNotifiers) {
            graphViews.add(graphViewNotifier.getGraphView());
        }
        return graphViews;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        for (GraphViewNotifier graphViewNotifier : graphViewNotifiers) {
            graphViewNotifier.update(wiFiData);
        }
    }

    public List<GraphViewNotifier> getGraphViewNotifiers() {
        return graphViewNotifiers;
    }
}
