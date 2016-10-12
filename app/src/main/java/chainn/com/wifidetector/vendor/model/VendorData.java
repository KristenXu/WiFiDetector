package chainn.com.wifidetector.vendor.model;

/**
 * Created by xuchen on 16/10/12.
 */

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

class VendorData {
    private final String name;
    private final String mac;
    private final long id;

    VendorData(long id, @NonNull String name, @NonNull String mac) {
        this.id = id;
        this.name = name;
        this.mac = mac;
    }

    long getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getMac() {
        return mac;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}