package chainn.com.wifidetector.wifi.graph.time;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.jjoe64.graphview.GraphView;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.wifi.scanner.Scanner;

public class TimeGraphFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private TimeGraphAdapter timeGraphAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_content, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.graphRefresh);
        swipeRefreshLayout.setOnRefreshListener(new ListViewOnRefreshListener());

        timeGraphAdapter = new TimeGraphAdapter();
        addGraphViews(swipeRefreshLayout, timeGraphAdapter);

        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.register(timeGraphAdapter);

        return view;
    }

    private void addGraphViews(View view, TimeGraphAdapter timeGraphAdapter) {
        ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.graphFlipper);
        for (GraphView graphView : timeGraphAdapter.getGraphViews()) {
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
        scanner.unregister(timeGraphAdapter);
        super.onDestroy();
    }

    private class ListViewOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            refresh();
        }
    }

}
