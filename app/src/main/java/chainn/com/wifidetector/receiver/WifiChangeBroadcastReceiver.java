package chainn.com.wifidetector.receiver;

/**
 * Created by xuchen on 16/10/12.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiChangeBroadcastReceiver extends BroadcastReceiver {
    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext=context;
        System.out.println("Wifi发生变化");
        getWifiInfo();
    }

    private void getWifiInfo() {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getBSSID() != null) {
            //wifi名称
            String ssid = wifiInfo.getSSID();
            //wifi信号强度
            int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
            //wifi速度
            int speed = wifiInfo.getLinkSpeed();
            //wifi速度单位
            String units = WifiInfo.LINK_SPEED_UNITS;
            System.out.println("ssid="+ssid+",signalLevel="+signalLevel+",speed="+speed+",units="+units);
        }
    }

}