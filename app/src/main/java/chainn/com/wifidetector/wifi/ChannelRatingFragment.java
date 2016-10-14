package chainn.com.wifidetector.wifi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.wifi.scanner.Scanner;

public class ChannelRatingFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChannelRatingAdapter channelRatingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();

        View view = inflater.inflate(R.layout.channel_rating_content, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.channelRatingRefresh);
        swipeRefreshLayout.setOnRefreshListener(new ListViewOnRefreshListener());

        TextView bestChannels = (TextView) view.findViewById(R.id.channelRatingBestChannels);
        ListView listView = (ListView) view.findViewById(R.id.channelRatingView);

        channelRatingAdapter = new ChannelRatingAdapter(activity, bestChannels);
        listView.setAdapter(channelRatingAdapter);

        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.register(channelRatingAdapter);

        return view;
    }

    private void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.update();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onDestroy() {
        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.unregister(channelRatingAdapter);
        super.onDestroy();
    }

    private class ListViewOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            refresh();
        }
    }
}
