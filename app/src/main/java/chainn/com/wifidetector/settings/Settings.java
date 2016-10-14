package chainn.com.wifidetector.settings;

import android.content.Context;
import android.support.annotation.NonNull;

import chainn.com.wifidetector.R;
import chainn.com.wifidetector.navigation.NavigationMenu;
import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.graph.tools.GraphLegend;
import chainn.com.wifidetector.wifi.model.GroupBy;
import chainn.com.wifidetector.wifi.model.SortBy;

import static android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class Settings {
    private final Context context;
    private Repository repository;

    public Settings(@NonNull Context context) {
        this.context = context;
        setRepository(new Repository());
    }

    public void setRepository(@NonNull Repository repository) {
        this.repository = repository;
    }

    public void initializeDefaultValues() {
        repository.initializeDefaultValues();
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        repository.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public int getScanInterval() {
        return repository.getInteger(R.string.scan_interval_key, repository.getResourceInteger(R.integer.scan_interval_default));
    }

    public SortBy getSortBy() {
        return SortBy.find(repository.getStringAsInteger(R.string.sort_by_key, SortBy.STRENGTH.ordinal()));
    }

    public GroupBy getGroupBy() {
        return GroupBy.find(repository.getStringAsInteger(R.string.group_by_key, GroupBy.NONE.ordinal()));
    }

    public GraphLegend getChannelGraphLegend() {
        return GraphLegend.find(repository.getStringAsInteger(R.string.channel_graph_legend_key, GraphLegend.HIDE.ordinal()), GraphLegend.HIDE);
    }

    public GraphLegend getTimeGraphLegend() {
        return GraphLegend.find(repository.getStringAsInteger(R.string.time_graph_legend_key, GraphLegend.LEFT.ordinal()), GraphLegend.LEFT);
    }

    public WiFiBand getWiFiBand() {
        return WiFiBand.find(repository.getStringAsInteger(R.string.wifi_band_key, WiFiBand.GHZ2.ordinal()));
    }

    public ThemeStyle getThemeStyle() {
        return ThemeStyle.find(repository.getStringAsInteger(R.string.theme_key, ThemeStyle.DARK.ordinal()));
    }

    public void toggleWiFiBand() {
        repository.save(R.string.wifi_band_key, getWiFiBand().toggle().ordinal());
    }

    public String getCountryCode() {
        String countryCode = context.getResources().getConfiguration().locale.getCountry();
        return repository.getString(R.string.country_code_key, countryCode);
    }

    public NavigationMenu getStartMenu() {
        return NavigationMenu.find(repository.getStringAsInteger(R.string.start_menu_key, NavigationMenu.ACCESS_POINTS.ordinal()));
    }
}
