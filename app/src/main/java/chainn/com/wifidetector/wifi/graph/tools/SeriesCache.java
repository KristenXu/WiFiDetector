package chainn.com.wifidetector.wifi.graph.tools;

import android.support.annotation.NonNull;

import com.jjoe64.graphview.series.BaseSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.Series;
import chainn.com.wifidetector.wifi.model.WiFiDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

class SeriesCache {
    private final Map<WiFiDetail, BaseSeries<DataPoint>> cache;

    SeriesCache() {
        this.cache = new TreeMap<>();
    }

    BaseSeries<DataPoint> add(@NonNull WiFiDetail wiFiDetail, @NonNull BaseSeries<DataPoint> series) {
        if (!contains(wiFiDetail)) {
            cache.put(wiFiDetail, series);
        }
        return cache.get(wiFiDetail);
    }

    List<BaseSeries<DataPoint>> remove(@NonNull Set<WiFiDetail> series) {
        List<BaseSeries<DataPoint>> removeSeries = new ArrayList<>();
        List<WiFiDetail> removeFromCache = new ArrayList<>();
        for (WiFiDetail wiFiDetail : cache.keySet()) {
            if (series.contains(wiFiDetail)) {
                continue;
            }
            removeSeries.add(cache.get(wiFiDetail));
            removeFromCache.add(wiFiDetail);
        }
        for (WiFiDetail wiFiDetail : removeFromCache) {
            cache.remove(wiFiDetail);
        }
        return removeSeries;
    }

    WiFiDetail find(@NonNull Series series) {
        for (WiFiDetail wiFiDetail : cache.keySet()) {
            if (series.equals(cache.get(wiFiDetail))) {
                return wiFiDetail;
            }
        }
        return null;
    }

    boolean contains(@NonNull WiFiDetail wiFiDetail) {
        return cache.containsKey(wiFiDetail);
    }
}
