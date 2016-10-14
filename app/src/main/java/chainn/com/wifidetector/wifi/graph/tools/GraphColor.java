package chainn.com.wifidetector.wifi.graph.tools;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GraphColor {
    public static final GraphColor TRANSPARENT = new GraphColor(0x009E9E9E, 0x009E9E9E);

    private final long primary;
    private final long background;

    GraphColor(long primary, long background) {
        this.primary = primary;
        this.background = background;
    }

    public long getPrimary() {
        return primary;
    }

    public long getBackground() {
        return background;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        GraphColor otherDetail = (GraphColor) other;
        return new EqualsBuilder()
            .append(getPrimary(), (otherDetail).getPrimary())
            .append(getBackground(), (otherDetail).getBackground())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getPrimary())
            .append(getBackground())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
