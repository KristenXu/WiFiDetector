package chainn.com.wifidetector.wifi.scanner;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.settings.Settings;

/**
 * Created by xuchen on 16/10/12.
 */
public class Scanner {
    private final List<UpdateNotifier> updateNotifiers;
    private final WifiManager wifiManager;
    private final Transformer transformer;
    private Cache cache;
    private PeriodicScan periodicScan;

    public Scanner(@NonNull WifiManager wifiManager, @NonNull Handler handler, @NonNull Settings settings, @NonNull Transformer transformer) {
        this.updateNotifiers = new ArrayList<>();
        this.wifiManager = wifiManager;
        this.transformer = transformer;
        this.setCache(new Cache());
        this.periodicScan = new PeriodicScan(this, handler, settings);
    }

    public void update() {
        List<ScanResult> scanResults = new ArrayList<>();
        WifiInfo connectionInfo = null;
        List<WifiConfiguration> configuredNetworks = null;
        try {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            if (wifiManager.startScan()) {
                scanResults = wifiManager.getScanResults();
            }
            connectionInfo = wifiManager.getConnectionInfo();
            configuredNetworks = wifiManager.getConfiguredNetworks();
        } catch (Exception e) {
            // critical error: set to no results and do not die
        }
        cache.add(scanResults);
        WiFiData wiFiData = transformer.transformToWiFiData(cache.getScanResults(), connectionInfo, configuredNetworks);
        for (UpdateNotifier updateNotifier : updateNotifiers) {
            updateNotifier.update(wiFiData);
        }
    }

    public void register(@NonNull UpdateNotifier updateNotifier) {
        updateNotifiers.add(updateNotifier);
    }

    public void unregister(@NonNull UpdateNotifier updateNotifier) {
        updateNotifiers.remove(updateNotifier);
    }

    public void pause() {
        periodicScan.stop();
    }

    public boolean isRunning() {
        return periodicScan.isRunning();
    }

    public void resume() {
        periodicScan.start();
    }

    PeriodicScan getPeriodicScan() {
        return periodicScan;
    }

    void setPeriodicScan(@NonNull PeriodicScan periodicScan) {
        this.periodicScan = periodicScan;
    }

    void setCache(@NonNull Cache cache) {
        this.cache = cache;
    }

    List<UpdateNotifier> getUpdateNotifiers() {
        return updateNotifiers;
    }
}
