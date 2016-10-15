package chainn.com.wifidetector.wifi;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import chainn.com.wifidetector.R;
import chainn.com.wifidetector.wifi.model.Security;
import chainn.com.wifidetector.wifi.model.Strength;
import chainn.com.wifidetector.wifi.model.WiFiAdditional;
import chainn.com.wifidetector.wifi.model.WiFiDetail;
import chainn.com.wifidetector.wifi.model.WiFiSignal;

import org.apache.commons.lang3.StringUtils;

public class AccessPointsDetail {
    private static final int VENDOR_NAME_MAX = 12;

    public void setView(@NonNull Resources resources, @NonNull View view, @NonNull WiFiDetail wiFiDetail, boolean isChild) {
        TextView textSSID = (TextView) view.findViewById(R.id.ssid);

        textSSID.setText(wiFiDetail.getTitle());

        view.findViewById(R.id.ipAddress).setVisibility(View.GONE);
        view.findViewById(R.id.linkSpeed).setVisibility(View.GONE);

        ImageView configuredImage = (ImageView) view.findViewById(R.id.configuredImage);
        WiFiAdditional wiFiAdditional = wiFiDetail.getWiFiAdditional();
        if (wiFiAdditional.isConfiguredNetwork()) {
            configuredImage.setVisibility(View.VISIBLE);
            configuredImage.setColorFilter(resources.getColor(R.color.connected));
        } else {
            configuredImage.setVisibility(View.GONE);
        }

        WiFiSignal wiFiSignal = wiFiDetail.getWiFiSignal();
        Strength strength = wiFiSignal.getStrength();
        ImageView imageView = (ImageView) view.findViewById(R.id.levelImage);
        imageView.setImageResource(strength.imageResource());
        imageView.setColorFilter(resources.getColor(strength.colorResource()));

        Security security = wiFiDetail.getSecurity();
//        ImageView securityImage = (ImageView) view.findViewById(R.id.securityImage);
//        securityImage.setImageResource(security.imageResource());
//        securityImage.setColorFilter(resources.getColor(R.color.icons_color));

        TextView textLevel = (TextView) view.findViewById(R.id.level);
        textLevel.setText(wiFiSignal.getLevel() + "dBm");
        textLevel.setTextColor(resources.getColor(strength.colorResource()));

        ((TextView) view.findViewById(R.id.channel))
            .setText("" + wiFiSignal.getPrimaryWiFiChannel().getChannel());
        ((TextView) view.findViewById(R.id.primaryFrequency))
            .setText(wiFiSignal.getPrimaryFrequency() + WifiInfo.FREQUENCY_UNITS);
        ((TextView) view.findViewById(R.id.distance))
            .setText(String.format("%.1fm", wiFiSignal.getDistance()));
        ((TextView) view.findViewById(R.id.channel_frequency_range))
            .setText(wiFiSignal.getFrequencyStart() + " - " + wiFiSignal.getFrequencyEnd());
        ((TextView) view.findViewById(R.id.width))
            .setText("(" + wiFiSignal.getWiFiWidth().getFrequencyWidth() + WifiInfo.FREQUENCY_UNITS + ")");
//        ((TextView) view.findViewById(R.id.capabilities))
//            .setText(wiFiDetail.getCapabilities());

        TextView textVendor = ((TextView) view.findViewById(R.id.vendor));
        String vendor = wiFiAdditional.getVendorName();
        if (StringUtils.isBlank(vendor)) {
            textVendor.setVisibility(View.GONE);
        } else {
            textVendor.setVisibility(View.VISIBLE);
            textVendor.setText(vendor.substring(0, Math.min(VENDOR_NAME_MAX, vendor.length())));
        }

        if (isChild) {
            view.findViewById(R.id.tab).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.tab).setVisibility(View.GONE);
        }

    }

    public Dialog popupDialog(@NonNull Context context, @NonNull LayoutInflater inflater, @NonNull WiFiDetail wiFiDetail) {
        View view = inflater.inflate(R.layout.access_points_details_popup, null);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        setView(context.getResources(), view, wiFiDetail, false);
        dialog.findViewById(R.id.popupButton).setOnClickListener(new PopupDialogListener(dialog));
        return dialog;
    }

    private class PopupDialogListener implements OnClickListener {
        private final Dialog dialog;

        PopupDialogListener(@NonNull Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    }
}
