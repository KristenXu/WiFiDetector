package chainn.com.wifidetector.settings;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;

class Data implements Comparable<Data> {
    private final String code;
    private final String name;

    Data(String code, String name) {
        this.code = code;
        this.name = name;
    }

    String getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    @Override
    public int compareTo(@NonNull Data another) {
        return new CompareToBuilder()
            .append(getName(), another.getName())
            .append(getCode(), another.getCode())
            .toComparison();
    }
}
