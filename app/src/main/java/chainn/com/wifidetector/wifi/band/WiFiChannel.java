package chainn.com.wifidetector.wifi.band;

/**
 * Created by xuchen on 16/10/12.
 */


import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WiFiChannel implements Comparable<WiFiChannel> {
    public static final WiFiChannel UNKNOWN = new WiFiChannel();

    static final int FREQUENCY_SPREAD = 5;
    private static final int ALLOWED_RANGE = FREQUENCY_SPREAD / 2;

    private final int channel;
    private final int frequency;

    private WiFiChannel() {
        channel = frequency = 0;
    }

    public WiFiChannel(int channel, int frequency) {
        this.channel = channel;
        this.frequency = frequency;
    }

    public int getChannel() {
        return channel;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isInRange(int frequency) {
        return frequency >= this.frequency - ALLOWED_RANGE && frequency <= this.frequency + ALLOWED_RANGE;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        WiFiChannel otherDetail = (WiFiChannel) other;
        return new EqualsBuilder()
            .append(getChannel(), (otherDetail).getChannel())
            .append(getFrequency(), (otherDetail).getFrequency())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getChannel())
            .append(getFrequency())
            .toHashCode();
    }

    @Override
    public int compareTo(@NonNull WiFiChannel another) {
        return new CompareToBuilder()
            .append(getChannel(), another.getChannel())
            .append(getFrequency(), another.getFrequency())
            .toComparison();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
