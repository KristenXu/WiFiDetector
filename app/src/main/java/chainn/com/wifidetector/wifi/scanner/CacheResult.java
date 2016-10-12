package chainn.com.wifidetector.wifi.scanner;

/**
 * Created by xuchen on 16/10/12.
 */
import android.net.wifi.ScanResult;
import android.support.annotation.NonNull;

class CacheResult {
    private final ScanResult scanResult;
    private final int levelAverage;

    CacheResult(@NonNull ScanResult scanResult, int levelAverage) {
        this.scanResult = scanResult;
        this.levelAverage = levelAverage;
    }

    ScanResult getScanResult() {
        return scanResult;
    }

    int getLevelAverage() {
        return levelAverage;
    }
}
