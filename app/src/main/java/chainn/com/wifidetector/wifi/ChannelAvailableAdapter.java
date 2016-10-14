package chainn.com.wifidetector.wifi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.band.WiFiChannelCountry;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

class ChannelAvailableAdapter extends ArrayAdapter<WiFiChannelCountry> {
    ChannelAvailableAdapter(@NonNull Context context, @NonNull List<WiFiChannelCountry> wiFiChannelCountries) {
        super(context, R.layout.channel_available_details, wiFiChannelCountries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = MainContext.INSTANCE.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.channel_available_details, parent, false);
        }
        WiFiChannelCountry wiFiChannelCountry = getItem(position);
        ((TextView) view.findViewById(R.id.channel_available_country))
            .setText(wiFiChannelCountry.getCountryCode() + " - " + wiFiChannelCountry.getCountryName());
        ((TextView) view.findViewById(R.id.channel_available_title_ghz_2))
            .setText(WiFiBand.GHZ2.getBand() + " : ");
        ((TextView) view.findViewById(R.id.channel_available_ghz_2))
            .setText(StringUtils.join(wiFiChannelCountry.getChannelsGHZ2().toArray(), ","));
        ((TextView) view.findViewById(R.id.channel_available_title_ghz_5))
            .setText(WiFiBand.GHZ5.getBand() + " : ");
        ((TextView) view.findViewById(R.id.channel_available_ghz_5))
            .setText(StringUtils.join(wiFiChannelCountry.getChannelsGHZ5().toArray(), ","));
        return view;
    }

}
