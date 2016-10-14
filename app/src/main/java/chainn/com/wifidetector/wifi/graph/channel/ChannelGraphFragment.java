package chainn.com.wifidetector.wifi.graph.channel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.jjoe64.graphview.GraphView;
import chainn.com.wifidetector.Configuration;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.wifi.graph.channel.ChannelGraphNavigation.NavigationItem;
import chainn.com.wifidetector.wifi.scanner.Scanner;

public class ChannelGraphFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChannelGraphAdapter channelGraphAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_content, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.graphRefresh);
        swipeRefreshLayout.setOnRefreshListener(new ListViewOnRefreshListener());

        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        ChannelGraphNavigation channelGraphNavigation = new ChannelGraphNavigation(getActivity(), configuration);
        channelGraphAdapter = new ChannelGraphAdapter(channelGraphNavigation);
        addGraphViews(swipeRefreshLayout, channelGraphAdapter);
        addGraphNavigation(view, channelGraphNavigation);

        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.register(channelGraphAdapter);

        return view;
    }

    private void addGraphNavigation(View view, ChannelGraphNavigation channelGraphNavigation) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.graphNavigation);
        for (NavigationItem navigationItem : channelGraphNavigation.getNavigationItems()) {
            linearLayout.addView(navigationItem.getButton());
        }
    }

    private void addGraphViews(View view, ChannelGraphAdapter channelGraphAdapter) {
        ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.graphFlipper);
        for (GraphView graphView : channelGraphAdapter.getGraphViews()) {
            viewFlipper.addView(graphView);
        }
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
        scanner.unregister(channelGraphAdapter);
        super.onDestroy();
    }

    private class ListViewOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            refresh();
        }
    }

}
