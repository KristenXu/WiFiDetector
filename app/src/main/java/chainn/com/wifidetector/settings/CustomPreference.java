package chainn.com.wifidetector.settings;

import android.content.Context;
import android.preference.ListPreference;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

class CustomPreference extends ListPreference {
    CustomPreference(@NonNull Context context, AttributeSet attrs, List<Data> datas, String defaultValue) {
        super(context, attrs);
        setEntries(getNames(datas));
        setEntryValues(getCodes(datas));
        setDefaultValue(defaultValue);
    }

    private CharSequence[] getCodes(List<Data> datas) {
        List<String> entryValues = new ArrayList<>();
        for (Data data : datas) {
            entryValues.add(data.getCode());
        }
        return entryValues.toArray(new CharSequence[]{});
    }

    private CharSequence[] getNames(List<Data> datas) {
        List<String> entries = new ArrayList<>();
        for (Data data : datas) {
            entries.add(data.getName());
        }
        return entries.toArray(new CharSequence[]{});
    }

}
