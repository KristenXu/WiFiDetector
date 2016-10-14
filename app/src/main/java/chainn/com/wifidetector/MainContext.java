package chainn.com.wifidetector;

/**
 * Created by xuchen on 16/10/12.
 */
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.vendor.model.Database;
import chainn.com.wifidetector.vendor.model.VendorService;
import chainn.com.wifidetector.wifi.scanner.Scanner;

public enum MainContext {
    INSTANCE;

    private Settings settings;
    private Context context;
    private Resources resources;
    private Scanner scanner;
    private VendorService vendorService;
    private LayoutInflater layoutInflater;
    private Database database;
    private Logger logger;
    private Configuration configuration;

    public Settings getSettings() {
        return settings;
    }

    void setSettings(Settings settings) {
        this.settings = settings;
    }

    public VendorService getVendorService() {
        return vendorService;
    }

    void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    public Scanner getScanner() {
        return scanner;
    }

    void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public Database getDatabase() {
        return database;
    }

    void setDatabase(Database database) {
        this.database = database;
    }

    public Resources getResources() {
        return resources;
    }

    void setResources(Resources resources) {
        this.resources = resources;
    }

    public Context getContext() {
        return context;
    }

    void setContext(Context context) {
        this.context = context;
    }

    public Logger getLogger() {
        return logger;
    }

    void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
