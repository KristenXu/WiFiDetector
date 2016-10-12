package chainn.com.wifidetector.wifi.model;

/**
 * Created by xuchen on 16/10/12.
 */
import android.support.annotation.NonNull;

import chainn.com.wifidetector.wifi.band.WiFiBand;
import chainn.com.wifidetector.wifi.band.WiFiChannel;
import chainn.com.wifidetector.wifi.band.WiFiWidth;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WiFiSignal {
    public static final WiFiSignal EMPTY = new WiFiSignal(0, 0, WiFiWidth.MHZ_20, 0);

    private final int primaryFrequency;
    private final int centerFrequency;
    private final WiFiWidth wiFiWidth;
    private final WiFiBand wiFiBand;
    private final int level;

    public WiFiSignal(int primaryFrequency, int centerFrequency, @NonNull WiFiWidth wiFiWidth, int level) {
        this.primaryFrequency = primaryFrequency;
        this.centerFrequency = centerFrequency;
        this.wiFiWidth = wiFiWidth;
        this.level = level;
        this.wiFiBand = WiFiBand.findByFrequency(primaryFrequency);
    }

    public int getPrimaryFrequency() {
        return primaryFrequency;
    }

    public int getCenterFrequency() {
        return centerFrequency;
    }

    public int getFrequencyStart() {
        return getCenterFrequency() - getWiFiWidth().getFrequencyWidthHalf();
    }

    public int getFrequencyEnd() {
        return getCenterFrequency() + getWiFiWidth().getFrequencyWidthHalf();
    }

    public WiFiBand getWiFiBand() {
        return wiFiBand;
    }

    public WiFiWidth getWiFiWidth() {
        return wiFiWidth;
    }

    public WiFiChannel getPrimaryWiFiChannel() {
        return getWiFiBand().getWiFiChannels().getWiFiChannelByFrequency(getPrimaryFrequency());
    }

    public WiFiChannel getCenterWiFiChannel() {
        return getWiFiBand().getWiFiChannels().getWiFiChannelByFrequency(getCenterFrequency());
    }

    public int getLevel() {
        return level;
    }

    public Strength getStrength() {
        return Strength.calculate(level);
    }

    public double getDistance() {
        return WiFiUtils.calculateDistance(getPrimaryFrequency(), getLevel());
    }

    public boolean isInRange(int frequency) {
        return frequency >= getFrequencyStart() && frequency <= getFrequencyEnd();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return new EqualsBuilder()
                .append(getPrimaryFrequency(), ((WiFiSignal) other).getPrimaryFrequency())
                .append(getWiFiWidth(), ((WiFiSignal) other).getWiFiWidth())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPrimaryFrequency())
                .append(getWiFiWidth())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
