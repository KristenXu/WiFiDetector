package chainn.com.wifidetector.wifi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chainn.com.wifidetector.Configuration;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.wifi.band.WiFiChannelCountry;

import java.util.ArrayList;
import java.util.List;

public class ChannelAvailableFragment extends ListFragment {
    private ChannelAvailableAdapter channelAvailableAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channel_available_content, container, false);
        channelAvailableAdapter = new ChannelAvailableAdapter(getActivity(), getChannelAvailable());
        setListAdapter(channelAvailableAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        channelAvailableAdapter.clear();
        channelAvailableAdapter.addAll(getChannelAvailable());
    }

    private List<WiFiChannelCountry> getChannelAvailable() {
        List<WiFiChannelCountry> results = new ArrayList<>();
        Settings settings = MainContext.INSTANCE.getSettings();
        results.add(WiFiChannelCountry.find(settings.getCountryCode()));
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        if (configuration.isDevelopmentMode()) {
            results.addAll(WiFiChannelCountry.getAll());
        }
        return results;
    }

}
