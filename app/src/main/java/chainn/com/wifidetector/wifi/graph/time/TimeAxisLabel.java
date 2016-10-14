package chainn.com.wifidetector.wifi.graph.time;

import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import chainn.com.wifidetector.wifi.graph.tools.GraphViewBuilder;

import org.apache.commons.lang3.StringUtils;

class TimeAxisLabel implements LabelFormatter {
    @Override
    public String formatLabel(double value, boolean isValueX) {
        String result = StringUtils.EMPTY;
        int valueAsInt = (int) (value + (value < 0 ? -0.5 : 0.5));
        if (isValueX) {
            if (valueAsInt > 0 && valueAsInt % 2 == 0) {
                result += valueAsInt;
            }
        } else {
            if (valueAsInt <= GraphViewBuilder.MAX_Y && valueAsInt > GraphViewBuilder.MIN_Y) {
                result += valueAsInt;
            }
        }
        return result;
    }

    @Override
    public void setViewport(Viewport viewport) {
        // ignore
    }
}
