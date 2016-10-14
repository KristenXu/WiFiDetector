package chainn.com.wifidetector.navigation;

import android.support.annotation.NonNull;

public enum NavigationGroup {
    GROUP_FEATURE(NavigationMenu.ACCESS_POINTS, NavigationMenu.CHANNEL_RATING, NavigationMenu.CHANNEL_GRAPH, NavigationMenu.TIME_GRAPH),
    GROUP_OTHER(NavigationMenu.CHANNEL_AVAILABLE, NavigationMenu.VENDOR_LIST),
    GROUP_SETTINGS(NavigationMenu.SETTINGS, NavigationMenu.ABOUT);

    private final NavigationMenu[] navigationMenu;

    NavigationGroup(@NonNull NavigationMenu... navigationMenu) {
        this.navigationMenu = navigationMenu;
    }

    public NavigationMenu[] navigationMenu() {
        return navigationMenu;
    }
}
