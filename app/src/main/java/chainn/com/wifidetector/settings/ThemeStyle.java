package chainn.com.wifidetector.settings;

/**
 * Created by xuchen on 16/10/13.
 */
import chainn.com.wifidetector.R;

public enum ThemeStyle {
    DARK(R.style.ThemeAppCompatDark, R.style.ThemeDeviceDefaultDark),
    LIGHT(R.style.ThemeAppCompatLight, R.style.ThemeDeviceDefaultLight);

    private final int themeAppCompatStyle;
    private final int themeDeviceDefaultStyle;

    ThemeStyle(int themeAppCompatStyle, int themeDeviceDefaultStyle) {
        this.themeAppCompatStyle = themeAppCompatStyle;
        this.themeDeviceDefaultStyle = themeDeviceDefaultStyle;
    }

    public static ThemeStyle find(int index) {
        if (index < 0 || index >= values().length) {
            return DARK;
        }
        return values()[index];

    }

    public int themeAppCompatStyle() {
        return themeAppCompatStyle;
    }

    public int themeDeviceDefaultStyle() {
        return themeDeviceDefaultStyle;
    }
}

