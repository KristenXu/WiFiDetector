package chainn.com.wifidetector.wifi.scanner;

/**
 * Created by xuchen on 16/10/12.
 */
import android.net.wifi.ScanResult;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

class Cache {
    private final Deque<List<ScanResult>> cache = new ArrayDeque<>();

    List<CacheResult> getScanResults() {
        ScanResult current = null;
        int levelTotal = 0;
        int count = 0;
        List<CacheResult> results = new ArrayList<>();
        for (ScanResult scanResult : combineCache()) {
            if (current != null && !scanResult.BSSID.equals(current.BSSID)) {
                results.add(new CacheResult(current, levelTotal / count));
                count = 0;
                levelTotal = 0;
            }
            current = scanResult;
            count++;
            levelTotal += scanResult.level;
        }
        if (current != null) {
            results.add(new CacheResult(current, levelTotal / count));
        }
        return results;
    }

    private List<ScanResult> combineCache() {
        List<ScanResult> scanResults = new ArrayList<>();
        for (List<ScanResult> cachedScanResults : cache) {
            scanResults.addAll(cachedScanResults);
        }
        Collections.sort(scanResults, new ScanResultComparator());
        return scanResults;
    }

    void add(List<ScanResult> scanResults) {
        int cacheSize = getCacheSize();
        while (cache.size() >= cacheSize) {
            cache.pollLast();
        }
        if (scanResults != null) {
            cache.addFirst(scanResults);
        }
    }

    Deque<List<ScanResult>> getCache() {
        return cache;
    }

    int getCacheSize() {
        int scanInterval = 1;
        if (scanInterval < 5) {
            return 4;
        }
        if (scanInterval < 10) {
            return 3;
        }
        if (scanInterval < 20) {
            return 2;
        }
        return 1;
    }

    private static class ScanResultComparator implements Comparator<ScanResult> {
        @Override
        public int compare(ScanResult lhs, ScanResult rhs) {
            return new CompareToBuilder()
                    .append(lhs.BSSID, rhs.BSSID)
                    .append(lhs.level, rhs.level)
                    .toComparison();
        }
    }
}