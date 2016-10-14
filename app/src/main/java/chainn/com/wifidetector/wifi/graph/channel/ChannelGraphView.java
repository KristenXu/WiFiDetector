package chainn.com.wifidetector.wifi.graph.channel;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import chainn.com.wifidetector.Configuration;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.band.WiFiChannel;
import chainn.com.wifidetector.wifi.band.WiFiChannels;
import chainn.com.wifidetector.wifi.graph.TitleLineGraphSeries;
import chainn.com.wifidetector.wifi.graph.tools.GraphColor;
import chainn.com.wifidetector.wifi.graph.tools.GraphLegend;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewBuilder;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewNotifier;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewWrapper;
import chainn.com.wifidetector.wifi.model.SortBy;
import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.wifi.model.WiFiDetail;
import chainn.com.wifidetector.wifi.model.WiFiSignal;

import java.util.Set;
import java.util.TreeSet;

class ChannelGraphView implements GraphViewNotifier {
    private static final int CNT_X_SMALL_2 = 16;
    private static final int CNT_X_SMALL_5 = 18;
    private static final int CNT_X_LARGE = 24;
    private static final int THICKNESS_INVISIBLE = 0;

    private final WiFiBand wiFiBand;
    private final Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;
    private GraphViewWrapper graphViewWrapper;

    ChannelGraphView(@NonNull WiFiBand wiFiBand, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        this.wiFiBand = wiFiBand;
        this.wiFiChannelPair = wiFiChannelPair;
        this.graphViewWrapper = makeGraphViewWrapper();
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        Settings settings = MainContext.INSTANCE.getSettings();
        GraphLegend channelGraphLegend = settings.getChannelGraphLegend();
        SortBy sortBy = settings.getSortBy();
        Set<WiFiDetail> newSeries = new TreeSet<>();
        for (WiFiDetail wiFiDetail : wiFiData.getWiFiDetails(wiFiBand, sortBy)) {
            if (isInRange(wiFiDetail.getWiFiSignal().getCenterFrequency(), wiFiChannelPair)) {
                newSeries.add(wiFiDetail);
                addData(wiFiDetail);
            }
        }
        graphViewWrapper.removeSeries(newSeries);
        graphViewWrapper.updateLegend(channelGraphLegend);
        graphViewWrapper.setVisibility(isSelected() ? View.VISIBLE : View.GONE);
    }

    private boolean isInRange(int frequency, Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        return frequency >= wiFiChannelPair.first.getFrequency() && frequency <= wiFiChannelPair.second.getFrequency();
    }

    private boolean isSelected() {
        Settings settings = MainContext.INSTANCE.getSettings();
        WiFiBand wiFiBand = settings.getWiFiBand();
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        Pair<WiFiChannel, WiFiChannel> wiFiChannelPair = configuration.getWiFiChannelPair();
        return this.wiFiBand.equals(wiFiBand) && (WiFiBand.GHZ2.equals(this.wiFiBand) || this.wiFiChannelPair.equals(wiFiChannelPair));
    }

    private void addData(@NonNull WiFiDetail wiFiDetail) {
        DataPoint[] dataPoints = createDataPoints(wiFiDetail);
        TitleLineGraphSeries<DataPoint> series = new TitleLineGraphSeries<>(dataPoints);
        if (graphViewWrapper.addSeries(wiFiDetail, series, dataPoints)) {
            GraphColor graphColor = graphViewWrapper.getColor();
            series.setColor((int) graphColor.getPrimary());
            series.setBackgroundColor((int) graphColor.getBackground());
        }
    }

    private DataPoint[] createDataPoints(@NonNull WiFiDetail wiFiDetail) {
        WiFiChannels wiFiChannels = wiFiBand.getWiFiChannels();
        int frequencySpread = wiFiChannels.getFrequencySpread();
        WiFiSignal wiFiSignal = wiFiDetail.getWiFiSignal();
        int frequency = wiFiSignal.getCenterFrequency();
        int frequencyStart = wiFiSignal.getFrequencyStart();
        int frequencyEnd = wiFiSignal.getFrequencyEnd();
        int level = wiFiSignal.getLevel();
        return new DataPoint[]{
            new DataPoint(frequencyStart, GraphViewBuilder.MIN_Y),
            new DataPoint(frequencyStart + frequencySpread, level),
            new DataPoint(frequency, level),
            new DataPoint(frequencyEnd - frequencySpread, level),
            new DataPoint(frequencyEnd, GraphViewBuilder.MIN_Y)
        };
    }

    @Override
    public GraphView getGraphView() {
        return graphViewWrapper.getGraphView();
    }

    private int getNumX() {
        int numX = CNT_X_LARGE;
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        if (!configuration.isLargeScreenLayout()) {
            numX = WiFiBand.GHZ2.equals(wiFiBand) ? CNT_X_SMALL_2 : CNT_X_SMALL_5;
        }
        WiFiChannels wiFiChannels = wiFiBand.getWiFiChannels();
        int channelFirst = wiFiChannelPair.first.getChannel() - wiFiChannels.getChannelOffset();
        int channelLast = wiFiChannelPair.second.getChannel() + wiFiChannels.getChannelOffset();
        return Math.min(numX, channelLast - channelFirst + 1);
    }

    private GraphView makeGraphView() {
        Resources resources = MainContext.INSTANCE.getResources();
        Context context = MainContext.INSTANCE.getContext();
        return new GraphViewBuilder(context, getNumX())
            .setLabelFormatter(new ChannelAxisLabel(wiFiBand, wiFiChannelPair))
            .setVerticalTitle(resources.getString(R.string.graph_axis_y))
            .setHorizontalTitle(resources.getString(R.string.graph_channel_axis_x))
            .build();
    }

    private GraphViewWrapper makeGraphViewWrapper() {
        Settings settings = MainContext.INSTANCE.getSettings();
        graphViewWrapper = new GraphViewWrapper(makeGraphView(), settings.getChannelGraphLegend());

        WiFiChannels wiFiChannels = wiFiBand.getWiFiChannels();
        int frequencyOffset = wiFiChannels.getFrequencyOffset();
        int minX = wiFiChannelPair.first.getFrequency() - frequencyOffset;
        int maxX = minX + (graphViewWrapper.getViewportCntX() * wiFiChannels.getFrequencySpread());
        graphViewWrapper.setViewport(minX, maxX);

        DataPoint[] dataPoints = new DataPoint[]{
            new DataPoint(minX, GraphViewBuilder.MIN_Y),
            new DataPoint(wiFiChannelPair.second.getFrequency() + frequencyOffset, GraphViewBuilder.MIN_Y)
        };

        TitleLineGraphSeries<DataPoint> series = new TitleLineGraphSeries<>(dataPoints);
        series.setColor((int) GraphColor.TRANSPARENT.getPrimary());
        series.setThickness(THICKNESS_INVISIBLE);
        graphViewWrapper.addSeries(series);
        return graphViewWrapper;
    }

    void setGraphViewWrapper(@NonNull GraphViewWrapper graphViewWrapper) {
        this.graphViewWrapper = graphViewWrapper;
    }

}
