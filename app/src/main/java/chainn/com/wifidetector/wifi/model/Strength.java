package chainn.com.wifidetector.wifi.model;

/**
 * Created by xuchen on 16/10/12.
 */

import chainn.com.wifidetector.R;

public enum Strength {
    ZERO(R.drawable.ic_signal_wifi_0_bar_black_36dp, R.color.error_color),
    ONE(R.drawable.ic_signal_wifi_1_bar_black_36dp, R.color.warning_color),
    TWO(R.drawable.ic_signal_wifi_2_bar_black_36dp, R.color.warning_color),
    THREE(R.drawable.ic_signal_wifi_3_bar_black_36dp, R.color.success_color),
    FOUR(R.drawable.ic_signal_wifi_4_bar_black_36dp, R.color.success_color);

    private final int imageResource;
    private final int colorResource;

    Strength(int imageResource, int colorResource) {
        this.imageResource = imageResource;
        this.colorResource = colorResource;
    }

    public static Strength calculate(int level) {
        int index = WiFiUtils.calculateSignalLevel(level, values().length);
        return Strength.values()[index];
    }

    public static Strength reverse(Strength strength) {
        int index = Strength.values().length - strength.ordinal() - 1;
        return Strength.values()[index];
    }

    public int colorResource() {
        return colorResource;
    }

    public int imageResource() {
        return imageResource;
    }

    public boolean weak() {
        return ZERO.equals(this);
    }

}
