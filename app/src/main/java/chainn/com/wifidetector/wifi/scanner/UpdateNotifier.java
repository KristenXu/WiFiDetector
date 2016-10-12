package chainn.com.wifidetector.wifi.scanner;

/**
 * Created by xuchen on 16/10/12.
 */
import android.support.annotation.NonNull;

import chainn.com.wifidetector.wifi.model.WiFiData;

public interface UpdateNotifier {
    void update(@NonNull WiFiData wiFiData);
}
