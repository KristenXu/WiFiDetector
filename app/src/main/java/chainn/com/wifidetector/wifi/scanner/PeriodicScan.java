package chainn.com.wifidetector.wifi.scanner;

/**
 * Created by xuchen on 16/10/12.
 */
import android.os.Handler;
import android.support.annotation.NonNull;
import chainn.com.wifidetector.settings.Settings;


class PeriodicScan implements Runnable {
    static final int DELAY_INITIAL = 1;
    static final int DELAY_INTERVAL = 1000;

    private final Scanner scanner;
    private final Handler handler;
    private final Settings settings;
    private boolean running;

    PeriodicScan(@NonNull Scanner scanner, @NonNull Handler handler, @NonNull Settings settings) {
        this.scanner = scanner;
        this.handler = handler;
        this.settings = settings;
        start();
    }

    void stop() {
        handler.removeCallbacks(this);
        running = false;
    }

    void start() {
        nextRun(DELAY_INITIAL);
    }

    private void nextRun(int delayInitial) {
        stop();
        handler.postDelayed(this, delayInitial);
        running = true;
    }

    @Override
    public void run() {
        scanner.update();
        nextRun(settings.getScanInterval() * DELAY_INTERVAL);
    }

    boolean isRunning() {
        return running;
    }
}
