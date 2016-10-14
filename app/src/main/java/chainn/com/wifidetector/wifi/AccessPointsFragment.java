package chainn.com.wifidetector.wifi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.wifi.scanner.Scanner;

public class AccessPointsFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private AccessPointsAdapter accessPointsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();

        View view = inflater.inflate(R.layout.access_points_content, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.accessPointsRefresh);
        swipeRefreshLayout.setOnRefreshListener(new ListViewOnRefreshListener());

        accessPointsAdapter = new AccessPointsAdapter(activity);
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.accessPointsView);
        expandableListView.setAdapter(accessPointsAdapter);

        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.register(accessPointsAdapter);

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
        scanner.unregister(accessPointsAdapter);
        super.onDestroy();
    }

    private class ListViewOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            refresh();
        }
    }
}
