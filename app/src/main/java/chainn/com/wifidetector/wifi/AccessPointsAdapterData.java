package chainn.com.wifidetector.wifi;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.wifi.model.WiFiDetail;

import java.util.ArrayList;
import java.util.List;

class AccessPointsAdapterData {
    private List<WiFiDetail> wiFiDetails = new ArrayList<>();

    void update(WiFiData wiFiData) {
        Settings settings = MainContext.INSTANCE.getSettings();
        wiFiDetails = wiFiData.getWiFiDetails(settings.getWiFiBand(), settings.getSortBy(), settings.getGroupBy());
    }

    int parentsCount() {
        return wiFiDetails.size();
    }

    private boolean validParentIndex(int index) {
        return index >= 0 && index < parentsCount();
    }

    private boolean validChildrenIndex(int indexParent, int indexChild) {
        return validParentIndex(indexParent) && indexChild >= 0 && indexChild < childrenCount(indexParent);
    }

    WiFiDetail parent(int index) {
        return validParentIndex(index) ? wiFiDetails.get(index) : WiFiDetail.EMPTY;
    }

    int childrenCount(int index) {
        return validParentIndex(index) ? wiFiDetails.get(index).getChildren().size() : 0;
    }

    WiFiDetail child(int indexParent, int indexChild) {
        return validChildrenIndex(indexParent, indexChild) ? wiFiDetails.get(indexParent).getChildren().get(indexChild) : WiFiDetail.EMPTY;
    }

}
