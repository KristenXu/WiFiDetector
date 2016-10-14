package chainn.com.wifidetector.settings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import chainn.com.wifidetector.navigation.NavigationGroup;
import chainn.com.wifidetector.navigation.NavigationMenu;

import java.util.ArrayList;
import java.util.List;

public class StartMenuPreference extends CustomPreference {
    public StartMenuPreference(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs, getData(context), getDefault());
    }

    private static List<Data> getData(@NonNull Context context) {
        List<Data> result = new ArrayList<>();
        for (NavigationMenu navigationMenu : NavigationGroup.GROUP_FEATURE.navigationMenu()) {
            result.add(new Data("" + navigationMenu.ordinal(), context.getString(navigationMenu.getTitle())));
        }
        return result;
    }

    private static String getDefault() {
        return "" + NavigationGroup.GROUP_FEATURE.navigationMenu()[0].ordinal();
    }

}
