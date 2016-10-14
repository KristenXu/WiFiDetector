package chainn.com.wifidetector.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;

class Repository {
    void initializeDefaultValues() {
        Context context = MainContext.INSTANCE.getContext();
        PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
    }

    void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    void save(int key, int value) {
        Context context = MainContext.INSTANCE.getContext();
        save(context.getString(key), "" + value);
    }

    private void save(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    int getStringAsInteger(int key, int defaultValue) {
        try {
            return Integer.parseInt(getString(key, "" + defaultValue));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    String getString(int key, String defaultValue) {
        Context context = MainContext.INSTANCE.getContext();
        String keyValue = context.getString(key);
        try {
            return getSharedPreferences().getString(keyValue, defaultValue);
        } catch (Exception e) {
            save(keyValue, defaultValue);
            return defaultValue;
        }
    }

    int getResourceInteger(int key) {
        Resources resources = MainContext.INSTANCE.getResources();
        return resources.getInteger(key);
    }

    int getInteger(int key, int defaultValue) {
        Context context = MainContext.INSTANCE.getContext();
        String keyValue = context.getString(key);
        try {
            return getSharedPreferences().getInt(keyValue, defaultValue);
        } catch (Exception e) {
            save(keyValue, "" + defaultValue);
            return defaultValue;
        }
    }

    private SharedPreferences getSharedPreferences() {
        Context context = MainContext.INSTANCE.getContext();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
