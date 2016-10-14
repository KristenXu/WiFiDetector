package chainn.com.wifidetector.wifi;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.band.WiFiChannel;
import chainn.com.wifidetector.wifi.model.ChannelRating;
import chainn.com.wifidetector.wifi.model.SortBy;
import chainn.com.wifidetector.wifi.model.Strength;
import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.wifi.scanner.UpdateNotifier;

import java.util.ArrayList;
import java.util.List;

class ChannelRatingAdapter extends ArrayAdapter<WiFiChannel> implements UpdateNotifier {
    private static final int MAX_CHANNELS_TO_DISPLAY = 10;

    private final Resources resources;
    private final TextView bestChannels;
    private ChannelRating channelRating;

    public ChannelRatingAdapter(@NonNull Context context, @NonNull TextView bestChannels) {
        super(context, R.layout.channel_rating_details, new ArrayList<WiFiChannel>());
        this.resources = context.getResources();
        this.bestChannels = bestChannels;
        setChannelRating(new ChannelRating());
    }

    void setChannelRating(@NonNull ChannelRating channelRating) {
        this.channelRating = channelRating;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        Settings settings = MainContext.INSTANCE.getSettings();
        WiFiBand wiFiBand = settings.getWiFiBand();
        List<WiFiChannel> wiFiChannels = setWiFiChannels(wiFiBand);
        channelRating.setWiFiDetails(wiFiData.getWiFiDetails(wiFiBand, SortBy.STRENGTH));
        bestChannels(wiFiBand, wiFiChannels);
        notifyDataSetChanged();
    }

    private List<WiFiChannel> setWiFiChannels(WiFiBand wiFiBand) {
        Settings settings = MainContext.INSTANCE.getSettings();
        String countryCode = settings.getCountryCode();
        List<WiFiChannel> wiFiChannels = wiFiBand.getWiFiChannels().getAvailableChannels(countryCode);
        clear();
        addAll(wiFiChannels);
        return wiFiChannels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = MainContext.INSTANCE.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.channel_rating_details, parent, false);
        }

        WiFiChannel wiFiChannel = getItem(position);
        int count = channelRating.getCount(wiFiChannel);

        ((TextView) view.findViewById(R.id.channelNumber)).setText("" + wiFiChannel.getChannel());
        ((TextView) view.findViewById(R.id.accessPointCount)).setText("" + count);

        Strength strength = Strength.reverse(channelRating.getStrength(wiFiChannel));
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.channelRating);
        int size = Strength.values().length;
        ratingBar.setMax(size);
        ratingBar.setNumStars(size);
        ratingBar.setRating(strength.ordinal() + 1);

        return view;
    }

    void bestChannels(@NonNull WiFiBand wiFiBand, @NonNull List<WiFiChannel> wiFiChannels) {
        List<ChannelRating.ChannelAPCount> channelAPCounts = channelRating.getBestChannels(wiFiChannels);
        int channelCount = 0;
        StringBuilder result = new StringBuilder();
        for (ChannelRating.ChannelAPCount channelAPCount : channelAPCounts) {
            if (channelCount > MAX_CHANNELS_TO_DISPLAY) {
                result.append("...");
                break;
            }
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(channelAPCount.getWiFiChannel().getChannel());
            channelCount++;
        }
        if (result.length() > 0) {
            bestChannels.setText(result.toString());
            bestChannels.setTextColor(resources.getColor(R.color.success_color));
        } else {
            StringBuilder message = new StringBuilder(resources.getText(R.string.channel_rating_best_none));
            if (WiFiBand.GHZ2.equals(wiFiBand)) {
                message.append(resources.getText(R.string.channel_rating_best_alternative));
                message.append(" ");
                message.append(WiFiBand.GHZ5.getBand());
            }
            bestChannels.setText(message);
            bestChannels.setTextColor(resources.getColor(R.color.error_color));
        }
    }

}
