package chainn.com.wifidetector.vendor.model;

/**
 * Created by xuchen on 16/10/12.
 */

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

class RemoteResult {
    private static final String MAC_ADDRESS = "mac_address";
    private static final String VENDOR_NAME = "vendor_name";

    private final String macAddress;
    private final String vendorName;

    RemoteResult(@NonNull String macAddress, @NonNull String vendorName) {
        this.macAddress = macAddress;
        this.vendorName = vendorName;
    }

    RemoteResult(@NonNull String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        macAddress = getValue(jsonObject, MAC_ADDRESS);
        vendorName = getValue(jsonObject, VENDOR_NAME);
    }

    String getMacAddress() {
        return macAddress;
    }

    String getVendorName() {
        return vendorName;
    }

    String toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MAC_ADDRESS, macAddress);
        jsonObject.put(VENDOR_NAME, vendorName);
        return jsonObject.toString();
    }

    private String getValue(@NonNull JSONObject jsonObject, @NonNull String key) {
        try {
            String result = jsonObject.getString(key);
            return result == null ? StringUtils.EMPTY : result;
        } catch (JSONException e) {
            return StringUtils.EMPTY;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
