package chainn.com.wifidetector.wifi.graph.channel;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import chainn.com.wifidetector.Configuration;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.band.WiFiChannel;
import chainn.com.wifidetector.wifi.band.WiFiChannels;
import chainn.com.wifidetector.wifi.scanner.Scanner;

import java.util.ArrayList;
import java.util.List;

class ChannelGraphNavigation {
    private static final float TEXT_SIZE_ADJUSTMENT = 0.8f;
    private final List<NavigationItem> navigationItems = new ArrayList<>();
    private final Configuration configuration;

    ChannelGraphNavigation(@NonNull Context context, @NonNull Configuration configuration) {
        this.configuration = configuration;
        makeNavigationItems(context);
    }

    List<NavigationItem> getNavigationItems() {
        return navigationItems;
    }

    void update() {
        List<NavigationItem> visible = getVisibleNavigationItems();

        Pair<WiFiChannel, WiFiChannel> selectedWiFiChannelPair = configuration.getWiFiChannelPair();
        for (NavigationItem navigationItem : navigationItems) {
            Button button = navigationItem.getButton();
            Pair<WiFiChannel, WiFiChannel> wiFiChannelPair = navigationItem.getWiFiChannelPair();
            if (visible.size() > 1 && visible.contains(navigationItem)) {
                button.setVisibility(View.VISIBLE);
                setSelectedButton(button, wiFiChannelPair.equals(selectedWiFiChannelPair));
            } else {
                button.setVisibility(View.GONE);
                setSelectedButton(button, false);
            }
        }
    }

    private List<NavigationItem> getVisibleNavigationItems() {
        Settings settings = MainContext.INSTANCE.getSettings();
        WiFiBand wiFiBand = settings.getWiFiBand();
        String countryCode = settings.getCountryCode();
        WiFiChannels wiFiChannels = wiFiBand.getWiFiChannels();
        List<NavigationItem> visible = new ArrayList<>();
        for (NavigationItem navigationItem : navigationItems) {
            Pair<WiFiChannel, WiFiChannel> wiFiChannelPair = navigationItem.getWiFiChannelPair();
            if (wiFiBand.isGHZ5() && wiFiChannels.isChannelAvailable(countryCode, wiFiChannelPair.first.getChannel())) {
                visible.add(navigationItem);
            }
        }
        return visible;
    }

    private void setSelectedButton(Button button, boolean selected) {
        Resources resources = MainContext.INSTANCE.getResources();
        if (selected) {
            button.setBackgroundColor(resources.getColor(R.color.connected));
            button.setSelected(true);
        } else {
            button.setBackgroundColor(resources.getColor(R.color.connected_background));
            button.setSelected(false);
        }
    }

    private void makeNavigationItems(@NonNull Context context) {
        for (Pair<WiFiChannel, WiFiChannel> pair : WiFiBand.GHZ5.getWiFiChannels().getWiFiChannelPairs()) {
            navigationItems.add(makeNavigationItem(context, pair));
        }
    }

    private NavigationItem makeNavigationItem(@NonNull Context context, Pair<WiFiChannel, WiFiChannel> pair) {
        Button button = new Button(context);
        String text = pair.first.getChannel() + " - " + pair.second.getChannel();
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, TEXT_SIZE_ADJUSTMENT);
        if (configuration.isLargeScreenLayout()) {
            params.setMargins(10, -10, 10, -10);
        } else {
            params.setMargins(5, -30, 5, -30);
        }
        button.setLayoutParams(params);
        button.setVisibility(View.GONE);
        button.setText(text);
        button.setOnClickListener(new ButtonOnClickListener(pair));
        return new NavigationItem(button, pair);
    }

    private class ButtonOnClickListener implements OnClickListener {
        private final Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;

        ButtonOnClickListener(@NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
            this.wiFiChannelPair = wiFiChannelPair;
        }

        @Override
        public void onClick(View view) {
            configuration.setWiFiChannelPair(wiFiChannelPair);
            Scanner scanner = MainContext.INSTANCE.getScanner();
            scanner.update();
        }
    }

    class NavigationItem {
        private final Button button;
        private final Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;

        NavigationItem(@NonNull Button button, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
            this.button = button;
            this.wiFiChannelPair = wiFiChannelPair;
        }

        Button getButton() {
            return button;
        }

        Pair<WiFiChannel, WiFiChannel> getWiFiChannelPair() {
            return wiFiChannelPair;
        }
    }

}
