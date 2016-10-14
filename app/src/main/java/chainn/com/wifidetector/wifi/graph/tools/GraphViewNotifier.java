package chainn.com.wifidetector.wifi.graph.tools;

import android.support.annotation.NonNull;

import com.jjoe64.graphview.GraphView;
import chainn.com.wifidetector.wifi.model.WiFiData;

public interface GraphViewNotifier {
    GraphView getGraphView();

    void update(@NonNull WiFiData wiFiData);
}
