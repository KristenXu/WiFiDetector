package chainn.com.wifidetector.wifi.graph.tools;

import android.content.res.Resources;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class GraphColors {
    private final List<GraphColor> availableGraphColors;
    private final Stack<GraphColor> graphColors;

    GraphColors() {
        graphColors = new Stack<>();
        availableGraphColors = new ArrayList<>();
    }

    private List<GraphColor> getAvailableGraphColors() {
        if (availableGraphColors.isEmpty()) {
            Resources resources = MainContext.INSTANCE.getResources();
            String[] colorsAsStrings = resources.getStringArray(R.array.graph_colors);
            for (int i = 0; i < colorsAsStrings.length; i += 2) {
                GraphColor graphColor = new GraphColor(Long.parseLong(colorsAsStrings[i].substring(1), 16), Long.parseLong(colorsAsStrings[i + 1].substring(1), 16));
                availableGraphColors.add(graphColor);
            }
        }
        return availableGraphColors;
    }

    GraphColor getColor() {
        if (graphColors.isEmpty()) {
            graphColors.addAll(getAvailableGraphColors());
        }
        return graphColors.pop();
    }

    void addColor(long primaryColor) {
        GraphColor graphColor = findColor(primaryColor);
        if (graphColor == null || graphColors.contains(graphColor)) {
            return;
        }
        graphColors.push(graphColor);
    }

    private GraphColor findColor(long primaryColor) {
        for (GraphColor graphColor : getAvailableGraphColors()) {
            if (primaryColor == graphColor.getPrimary()) {
                return graphColor;
            }
        }
        return null;
    }

}
