package chainn.com.wifidetector.navigation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import chainn.com.wifidetector.R;
import chainn.com.wifidetector.about.AboutActivity;
import chainn.com.wifidetector.settings.SettingActivity;
import chainn.com.wifidetector.vendor.VendorFragment;
import chainn.com.wifidetector.wifi.AccessPointsFragment;
import chainn.com.wifidetector.wifi.ChannelAvailableFragment;
import chainn.com.wifidetector.wifi.ChannelRatingFragment;
import chainn.com.wifidetector.wifi.graph.channel.ChannelGraphFragment;
import chainn.com.wifidetector.wifi.graph.time.TimeGraphFragment;

public enum NavigationMenu {
    ACCESS_POINTS(R.drawable.ic_network_wifi_grey_500_48dp, R.string.action_access_points, true, new AccessPointsFragment()),
    CHANNEL_GRAPH(R.drawable.ic_insert_chart_grey_500_48dp, R.string.action_channel_graph, true, new ChannelGraphFragment()),
    TIME_GRAPH(R.drawable.ic_show_chart_grey_500_48dp, R.string.action_time_graph, true, new TimeGraphFragment()),
    CHANNEL_AVAILABLE(R.drawable.ic_wifi_tethering_grey_500_48dp, R.string.action_channel_available, false, null),
    VENDOR_LIST(R.drawable.ic_list_grey_500_48dp, R.string.action_vendors, false, null),
    SETTINGS(R.drawable.ic_settings_grey_500_48dp, R.string.action_settings, null),
    ABOUT(R.drawable.ic_info_outline_grey_500_48dp, R.string.action_about, AboutActivity.class);

    private final int icon;
    private final int title;
    private final boolean wiFiBandSwitchable;
    private final Fragment fragment;
    private final Class<? extends Activity> activity;

    NavigationMenu(int icon, int title, boolean wiFiBandSwitchable, @NonNull Fragment fragment) {
        this.icon = icon;
        this.title = title;
        this.wiFiBandSwitchable = wiFiBandSwitchable;
        this.fragment = fragment;
        this.activity = null;
    }

    NavigationMenu(int icon, int title, @NonNull Class<? extends Activity> activity) {
        this.icon = icon;
        this.title = title;
        this.wiFiBandSwitchable = false;
        this.fragment = null;
        this.activity = activity;
    }

    public static NavigationMenu find(int index) {
        if (index < 0 || index >= values().length) {
            return ACCESS_POINTS;
        }
        return values()[index];
    }

    public Fragment getFragment() {
        return fragment;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public int getTitle() {
        return title;
    }

    public boolean isWiFiBandSwitchable() {
        return wiFiBandSwitchable;
    }

    int getIcon() {
        return icon;
    }
}
