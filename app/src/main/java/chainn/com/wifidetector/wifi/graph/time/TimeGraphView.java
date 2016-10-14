package chainn.com.wifidetector.wifi.graph.time;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import chainn.com.wifidetector.Configuration;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.graph.tools.GraphColor;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewBuilder;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewNotifier;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewWrapper;
import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.wifi.model.WiFiDetail;

import java.util.Set;
import java.util.TreeSet;

class TimeGraphView implements GraphViewNotifier {
    private static final int MAX_SCAN_COUNT = 400;
    private static final int NUM_X_SMALL = 18;
    private static final int NUM_X_LARGE = 24;
    private static final int THICKNESS_INVISIBLE = 0;

    private final WiFiBand wiFiBand;
    private GraphViewWrapper graphViewWrapper;
    private int scanCount;
    private int xValue;

    TimeGraphView(@NonNull WiFiBand wiFiBand) {
        this.wiFiBand = wiFiBand;
        this.scanCount = this.xValue = 0;
        this.graphViewWrapper = makeGraphViewWrapper();
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        Settings settings = MainContext.INSTANCE.getSettings();
        Set<WiFiDetail> newSeries = new TreeSet<>();
        for (WiFiDetail wiFiDetail : wiFiData.getWiFiDetails(wiFiBand, settings.getSortBy())) {
            newSeries.add(wiFiDetail);
            addData(wiFiDetail);
        }
        graphViewWrapper.removeSeries(newSeries);
        graphViewWrapper.updateLegend(settings.getTimeGraphLegend());
        graphViewWrapper.setVisibility(isSelected() ? View.VISIBLE : View.GONE);
        xValue++;
        if (scanCount < MAX_SCAN_COUNT) {
            scanCount++;
        }
    }

    private boolean isSelected() {
        return wiFiBand.equals(MainContext.INSTANCE.getSettings().getWiFiBand());
    }

    private void addData(@NonNull WiFiDetail wiFiDetail) {
        DataPoint dataPoint = new DataPoint(xValue, wiFiDetail.getWiFiSignal().getLevel());
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{dataPoint});
        if (graphViewWrapper.appendSeries(wiFiDetail, series, dataPoint, scanCount)) {
            series.setColor((int) graphViewWrapper.getColor().getPrimary());
            series.setDrawBackground(false);
        }
    }

    @Override
    public GraphView getGraphView() {
        return graphViewWrapper.getGraphView();
    }

    private int getNumX() {
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        return configuration.isLargeScreenLayout() ? NUM_X_LARGE : NUM_X_SMALL;
    }

    void setGraphViewWrapper(@NonNull GraphViewWrapper graphViewWrapper) {
        this.graphViewWrapper = graphViewWrapper;
    }

    private GraphView makeGraphView() {
        Resources resources = MainContext.INSTANCE.getResources();
        Context context = MainContext.INSTANCE.getContext();
        return new GraphViewBuilder(context, getNumX())
            .setLabelFormatter(new TimeAxisLabel())
            .setVerticalTitle(resources.getString(R.string.graph_axis_y))
            .setHorizontalTitle(resources.getString(R.string.graph_time_axis_x))
            .build();
    }

    private GraphViewWrapper makeGraphViewWrapper() {
        Settings settings = MainContext.INSTANCE.getSettings();
        graphViewWrapper = new GraphViewWrapper(makeGraphView(), settings.getTimeGraphLegend());

        graphViewWrapper.setViewport();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, GraphViewBuilder.MIN_Y),
            new DataPoint(getNumX() - 1, GraphViewBuilder.MIN_Y)
        });
        series.setColor((int) GraphColor.TRANSPARENT.getPrimary());
        series.setThickness(THICKNESS_INVISIBLE);
        graphViewWrapper.addSeries(series);

        return graphViewWrapper;
    }

}
