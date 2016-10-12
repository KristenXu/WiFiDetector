package chainn.com.wifidetector.wifi.scanner;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;

import chainn.com.wifidetector.wifi.band.WiFiWidth;
import chainn.com.wifidetector.wifi.model.WiFiConnection;
import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.wifi.model.WiFiDetail;
import chainn.com.wifidetector.wifi.model.WiFiSignal;
import chainn.com.wifidetector.wifi.model.WiFiUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Created by xuchen on 16/10/12.
 */
public class Transformer {
    WiFiConnection transformWifiInfo(WifiInfo wifiInfo) {
        if (wifiInfo == null || wifiInfo.getNetworkId() == -1) {
            return WiFiConnection.EMPTY;
        }
        return new WiFiConnection(
            WiFiUtils.convertSSID(wifiInfo.getSSID()),
            wifiInfo.getBSSID(),
            WiFiUtils.convertIpAddress(wifiInfo.getIpAddress()),
            wifiInfo.getLinkSpeed());
    }

    List<String> transformWifiConfigurations(List<WifiConfiguration> configuredNetworks) {
        List<String> results = new ArrayList<>();
        if (configuredNetworks != null) {
            for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                results.add(WiFiUtils.convertSSID(wifiConfiguration.SSID));
            }
        }
        return Collections.unmodifiableList(results);
    }

    List<WiFiDetail> transformCacheResults(List<CacheResult> cacheResults) {
        List<WiFiDetail> results = new ArrayList<>();
        if (cacheResults != null) {
            for (CacheResult cacheResult : cacheResults) {
                ScanResult scanResult = cacheResult.getScanResult();
                WiFiSignal wiFiSignal = new WiFiSignal(scanResult.frequency, getCenterFrequency(scanResult), getWiFiWidth(scanResult), cacheResult.getLevelAverage());
                WiFiDetail wiFiDetail = new WiFiDetail(scanResult.SSID, scanResult.BSSID, scanResult.capabilities, wiFiSignal);
                results.add(wiFiDetail);
            }
        }
        return Collections.unmodifiableList(results);
    }

    WiFiWidth getWiFiWidth(ScanResult scanResult) {
        try {
            return WiFiWidth.find(getFieldValue(scanResult, Fields.channelWidth));
        } catch (Exception e) {
            return WiFiWidth.MHZ_20;
        }
    }

    int getCenterFrequency(ScanResult scanResult) {
        try {
            int centerFrequency = getFieldValue(scanResult, Fields.centerFreq0);
            return centerFrequency == 0 ? scanResult.frequency : centerFrequency;
        } catch (Exception e) {
            return scanResult.frequency;
        }
    }

    int getFieldValue(ScanResult scanResult, Fields field) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = scanResult.getClass().getDeclaredField(field.name());
        return (int) declaredField.get(scanResult);
    }

    WiFiData transformToWiFiData(List<CacheResult> cacheResults, WifiInfo wifiInfo, List<WifiConfiguration> configuredNetworks) {
        List<WiFiDetail> wiFiDetails = transformCacheResults(cacheResults);
        WiFiConnection wiFiConnection = transformWifiInfo(wifiInfo);
        List<String> wifiConfigurations = transformWifiConfigurations(configuredNetworks);
        return new WiFiData(wiFiDetails, wiFiConnection, wifiConfigurations);
    }

    enum Fields {
        centerFreq0,
        centerFreq1,
        channelWidth
    }

}
