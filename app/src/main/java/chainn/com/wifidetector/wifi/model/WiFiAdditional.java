package chainn.com.wifidetector.wifi.model;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by xuchen on 16/10/12.
 */
public class WiFiAdditional {
    public static final WiFiAdditional EMPTY = new WiFiAdditional(StringUtils.EMPTY, false);

    private final String vendorName;
    private final String ipAddress;
    private final int linkSpeed;
    private final boolean configuredNetwork;

    private WiFiAdditional(@NonNull String vendorName, @NonNull String ipAddress, int linkSpeed, boolean configuredNetwork) {
        this.vendorName = vendorName;
        this.ipAddress = ipAddress;
        this.configuredNetwork = configuredNetwork;
        this.linkSpeed = linkSpeed;
    }

    public WiFiAdditional(@NonNull String vendorName, boolean configuredNetwork) {
        this(vendorName, StringUtils.EMPTY, WiFiConnection.LINK_SPEED_INVALID, configuredNetwork);
    }

    public WiFiAdditional(@NonNull String vendorName, @NonNull String ipAddress, int linkSpeed) {
        this(vendorName, ipAddress, linkSpeed, true);
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getIPAddress() {
        return ipAddress;
    }

    public int getLinkSpeed() {
        return linkSpeed;
    }

    public boolean isConnected() {
        return StringUtils.isNotBlank(getIPAddress());
    }

    public boolean isConfiguredNetwork() {
        return configuredNetwork;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
