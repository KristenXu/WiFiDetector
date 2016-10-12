package chainn.com.wifidetector;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import chainn.com.wifidetector.adapter.WifiListAdapter;
import chainn.com.wifidetector.vendor.model.Database;
import chainn.com.wifidetector.vendor.model.VendorService;
import chainn.com.wifidetector.wifi.scanner.Scanner;
import chainn.com.wifidetector.wifi.scanner.Transformer;


public class MainActivity extends AppCompatActivity {

    private WifiManager wm;         //WifiManager在包 android.net.wifi.WifiManager中
    private WifiInfo wi;            // WifiInfo在包android.net.wifi.WifiInfo中
    private int strength;           //信号强度
    private List<ScanResult> wmList;
    private TextView tvwifi;
    private ListView wifiListView;
    private WifiListAdapter wifiListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvwifi = (TextView) findViewById(R.id.title);
        wifiListView = (ListView) findViewById(R.id.wifi_listView);

        wm = (WifiManager) getSystemService(WIFI_SERVICE); //getSystemService(String)，通过名字来返回系统级服务的句柄。返回类型随要求变化。
        wmList = wm.getScanResults();
        wifiListAdapter = new WifiListAdapter(this, wmList);
        wifiListView.setAdapter(wifiListAdapter);
        new TvThread().start();
    }
    protected void refreshListView (List<ScanResult> list) {
        if (list.size() == 0){
            Toast.makeText(this, "当前没有wifi信号", Toast.LENGTH_LONG).show();
        }else {
            wifiListAdapter.refresh(list);
        }
    }
    class TvThread extends Thread{
        @Override
        public void run(){
            do{
                try{
                    Thread.sleep(500);
                    Message msg = new Message();
                    msg.what = 1;//what，int类型，未定义的消息，以便接收消息者可以鉴定消息是关于什么的。每个句柄都有自己的消息命名空间，不必担心冲突
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }while (true);

        }

    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    if(wm.startScan()){
                        wmList = null;
                        wmList = wm.getScanResults();
                        refreshListView(wmList);
                    }else{
                        Toast.makeText(MainActivity.this, "当前没有wifi信号", Toast.LENGTH_LONG).show();
                    }

                    //使用getSystemService(String)来取回WifiManager然后处理wifi接入，
                    wi = wm.getConnectionInfo();//getConnectionInfo返回wifi连接的动态信息

                    if(wi.getBSSID() != null) {//getBSSID返回基本服务集标识符，如果未连接，返回null，否则返回MAC地址形式：XX:XX:XX:XX:XX
                        strength = wi.getRssi();//返回接收到的目前的802.11网络的信号强度
                        tvwifi.setText(((Integer) strength).toString());
                    }else{
                        tvwifi.setText("当前尚未连接wifi");
                    }
                    break;
                default:
                    break;
            }

        }
    };


    private void initializeMainContext(@NonNull Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Handler handler = new Handler();
        Configuration configuration = new Configuration(false, true);

        MainContext mainContext = MainContext.INSTANCE;
        mainContext.setContext(context);
        mainContext.setConfiguration(configuration);
        mainContext.setResources(context.getResources());
        mainContext.setDatabase(new Database(context));
        mainContext.setVendorService(new VendorService());
        mainContext.setLayoutInflater((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        mainContext.setLogger(new Logger());
        mainContext.setScanner(new Scanner(wifiManager, handler, new Transformer()));
    }


}
