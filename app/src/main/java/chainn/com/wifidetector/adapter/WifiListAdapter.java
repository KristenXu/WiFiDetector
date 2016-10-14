package chainn.com.wifidetector.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import chainn.com.wifidetector.R;
import chainn.com.wifidetector.wifi.model.WiFiUtils;

/**
 * Created by xuchen on 16/10/12.
 */
public class WifiListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<ScanResult> list;
    Context context;
    TextView wifiName;
    TextView wifiStrenth;
    TextView wifiDistance;
    public WifiListAdapter(Context context, List<ScanResult> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        view = inflater.inflate(R.layout.item_wifi_list, null);
        Log.d("list", list.get(0).toString());
        ScanResult scanResult = list.get(position);
        wifiName = (TextView) view.findViewById(R.id.wifi_name);
        wifiName.setText(scanResult.SSID);
        wifiStrenth = (TextView) view.findViewById(R.id.wifi_strenth);
        wifiStrenth.setText(String.valueOf(Math.abs(scanResult.level)));
        wifiDistance = (TextView) view.findViewById(R.id.wifi_distance);
        wifiDistance.setText(WiFiUtils.calculateDistance(scanResult.frequency, scanResult.level) + "m");
        ImageView imageView = (ImageView) view.findViewById(R.id.wifi_list_icon);
        if (Math.abs(scanResult.level) > 100) {
            imageView.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_signal_wifi_0_bar_black_36dp));
        } else if (Math.abs(scanResult.level) > 80) {
            imageView.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_signal_wifi_0_bar_black_36dp));
        } else if (Math.abs(scanResult.level) > 70) {
            imageView.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_signal_wifi_1_bar_black_36dp));
        } else if (Math.abs(scanResult.level) > 60) {
            imageView.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_signal_wifi_2_bar_black_36dp));
        } else if (Math.abs(scanResult.level) > 50) {
            imageView.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_signal_wifi_3_bar_black_36dp));
        } else {
            imageView.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_signal_wifi_4_bar_black_36dp));
        }

        return view;
    }

    public void refresh(List<ScanResult> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
