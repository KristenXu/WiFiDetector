package chainn.com.wifidetector;

/**
 * Created by xuchen on 16/10/12.
 */

import android.support.annotation.NonNull;
import android.util.Log;

public class Logger {

    public void error(@NonNull Object object, @NonNull String msg, @NonNull Throwable e) {
        Log.e(object.getClass().getSimpleName() + " ", msg, e);
    }

    public void info(@NonNull Object object, @NonNull String msg) {
        Log.i(object.getClass().getSimpleName() + " ", msg);
    }

}
