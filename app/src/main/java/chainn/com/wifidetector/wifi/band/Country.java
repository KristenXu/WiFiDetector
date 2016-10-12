package chainn.com.wifidetector.wifi.band;

/**
 * Created by xuchen on 16/10/12.
 */


import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

class Country {
    private final List<Locale> countries;

    public Country() {
        countries = new ArrayList<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            String countryCode = locale.getCountry();
            if (StringUtils.isNotEmpty(countryCode)) {
                countries.add(locale);
            }
        }
        Collections.sort(countries, new LocaleCountryComparator());
    }

    public Locale getCountry(@NonNull String countryCode) {
        Locale country = new Locale("", countryCode);
        int index = Collections.binarySearch(countries, country, new LocaleCountryComparator());
        if (index < 0) {
            return country;
        }
        return countries.get(index);
    }

    public List<Locale> getCountries() {
        return Collections.unmodifiableList(countries);
    }

    private class LocaleCountryComparator implements Comparator<Locale> {
        @Override
        public int compare(Locale lhs, Locale rhs) {
            return lhs.getCountry().compareTo(rhs.getCountry());
        }
    }

}
