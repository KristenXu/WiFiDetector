package chainn.com.wifidetector.vendor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;

/**
 * Created by xuchen on 16/10/13.
 */
class VendorAdapter extends ArrayAdapter<String> {
    private SortedMap<String, List<String>> vendors;

    VendorAdapter(@NonNull Context context, @NonNull SortedMap<String, List<String>> vendors) {
        super(context, R.layout.vendor_details, new ArrayList<>(vendors.keySet()));
        this.vendors = vendors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = MainContext.INSTANCE.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.vendor_details, parent, false);
        }
        String name = getItem(position);
        ((TextView) view.findViewById(R.id.vendor_name)).setText(name);

        StringBuilder stringBuilder = new StringBuilder();
        for (String mac : vendors.get(name)) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            String macAddress =
                    mac.length() < 6
                            ? "*" + mac + "*"
                            : String.format("%s:%s:%s", mac.substring(0, 2), mac.substring(2, 4), mac.substring(4, 6));
            stringBuilder.append(macAddress);
        }
        ((TextView) view.findViewById(R.id.vendor_macs)).setText(stringBuilder.toString());
        return view;
    }

    SortedMap<String, List<String>> getVendors() {
        return vendors;
    }

    public void setVendors(@NonNull SortedMap<String, List<String>> vendors) {
        this.vendors = vendors;
        clear();
        addAll(new ArrayList<>(vendors.keySet()));
    }
}
