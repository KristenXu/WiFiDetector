package chainn.com.wifidetector.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import chainn.com.wifidetector.wifi.band.WiFiChannelCountry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountryPreference extends CustomPreference {
    public CountryPreference(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs, getData(), getDefault(context));
    }

    private static List<Data> getData() {
        List<Data> result = new ArrayList<>();
        for (WiFiChannelCountry wiFiChannelCountry : WiFiChannelCountry.getAll()) {
            result.add(new Data(wiFiChannelCountry.getCountryCode(), wiFiChannelCountry.getCountryName()));
        }
        Collections.sort(result);
        return result;
    }

    private static String getDefault(@NonNull Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        return configuration.locale.getCountry();
    }
}
