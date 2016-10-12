package chainn.com.wifidetector.vendor.model;

/**
 * Created by xuchen on 16/10/12.
 */

import android.support.annotation.NonNull;

import chainn.com.wifidetector.MainContext;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class VendorService {
    private final Set<String> remoteCalls = new TreeSet<>();
    private final Map<String, String> cache = new HashMap<>();

    private RemoteCall remoteCall;

    public String findVendorName(String macAddress) {
        String key = MacAddress.clean(macAddress);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        Database database = MainContext.INSTANCE.getDatabase();
        String result = database.find(macAddress);
        if (result != null) {
            result = VendorNameUtils.cleanVendorName(result);
            cache.put(key, result);
            return result;
        }
        if (!remoteCalls.contains(key)) {
            remoteCalls.add(key);
            getRemoteCall().execute(macAddress);
        }
        return StringUtils.EMPTY;
    }

    void clear() {
        cache.clear();
        remoteCalls.clear();
    }

    public SortedMap<String, List<String>> findAll() {
        SortedMap<String, List<String>> results = new TreeMap<>();
        Database database = MainContext.INSTANCE.getDatabase();
        List<VendorData> vendorDatas = database.findAll();
        for (VendorData vendorData : vendorDatas) {
            String key = VendorNameUtils.cleanVendorName(vendorData.getName());
            List<String> macs = results.get(key);
            if (macs == null) {
                macs = new ArrayList<>();
                results.put(key, macs);
            }
            macs.add(vendorData.getMac());
            Collections.sort(macs);
        }
        return results;
    }

    // injectors start
    private RemoteCall getRemoteCall() {
        return remoteCall == null ? new RemoteCall() : remoteCall;
    }

    void setRemoteCall(@NonNull RemoteCall remoteCall) {
        this.remoteCall = remoteCall;
    }
    // injectors end
}
