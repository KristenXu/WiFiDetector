package chainn.com.wifidetector.wifi;

import android.net.wifi.WifiInfo;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import chainn.com.wifidetector.MainActivity;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.wifi.model.WiFiAdditional;
import chainn.com.wifidetector.wifi.model.WiFiConnection;
import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.wifi.model.WiFiDetail;
import chainn.com.wifidetector.wifi.scanner.UpdateNotifier;

import java.util.List;

public class ConnectionView implements UpdateNotifier {
    private final MainActivity mainActivity;
    private AccessPointsDetail accessPointsDetail;

    public ConnectionView(@NonNull MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        setAccessPointsDetail(new AccessPointsDetail());
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        setConnectionVisibility(wiFiData);
        setNoDataVisibility(wiFiData);
    }

    private void setNoDataVisibility(@NonNull WiFiData wiFiData) {
        int noDataVisibility = View.GONE;
        int noDataGeoVisibility = View.GONE;
        if (mainActivity.getNavigationMenuView().getCurrentNavigationMenu().isWiFiBandSwitchable()) {
            Settings settings = MainContext.INSTANCE.getSettings();
            List<WiFiDetail> wiFiDetails = wiFiData.getWiFiDetails(settings.getWiFiBand(), settings.getSortBy());
            if (wiFiDetails.isEmpty()) {
                noDataVisibility = View.VISIBLE;
                if (wiFiData.getWiFiDetails().isEmpty()) {
                    noDataGeoVisibility = View.VISIBLE;
                }
            }
        }
        mainActivity.findViewById(R.id.nodata).setVisibility(noDataVisibility);
    }

    private void setConnectionVisibility(@NonNull WiFiData wiFiData) {
        WiFiDetail connection = wiFiData.getConnection();
        View connectionView = mainActivity.findViewById(R.id.connection);
        WiFiAdditional wiFiAdditional = connection.getWiFiAdditional();
        if (wiFiAdditional.isConnected()) {
            connectionView.setVisibility(View.VISIBLE);
            accessPointsDetail.setView(mainActivity.getResources(), connectionView, connection, false);

            String ipAddress = wiFiAdditional.getIPAddress();
            int linkSpeed = wiFiAdditional.getLinkSpeed();

            TextView textIPAddress = (TextView) connectionView.findViewById(R.id.ipAddress);
            TextView textLinkSpeed = (TextView) connectionView.findViewById(R.id.linkSpeed);
            textIPAddress.setVisibility(View.VISIBLE);
            textIPAddress.setText(ipAddress);
            if (linkSpeed == WiFiConnection.LINK_SPEED_INVALID) {
                textLinkSpeed.setVisibility(View.GONE);
            } else {
                textLinkSpeed.setVisibility(View.VISIBLE);
                textLinkSpeed.setText(linkSpeed + WifiInfo.LINK_SPEED_UNITS);
            }
        } else {
            connectionView.setVisibility(View.GONE);
        }
    }

    void setAccessPointsDetail(@NonNull AccessPointsDetail accessPointsDetail) {
        this.accessPointsDetail = accessPointsDetail;
    }
}
