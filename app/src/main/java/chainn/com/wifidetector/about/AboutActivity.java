package chainn.com.wifidetector.about;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import chainn.com.wifidetector.Configuration;
import chainn.com.wifidetector.MainActivity;
import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.settings.Settings;
import chainn.com.wifidetector.settings.ThemeStyle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setCustomTheme();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setApplicationName();
        setPackageName();
        setVersionNumber();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setCustomTheme() {
        Settings settings = MainContext.INSTANCE.getSettings();
        if (settings != null) {
            ThemeStyle themeStyle = settings.getThemeStyle();
            setTheme(themeStyle.themeAppCompatStyle());
        }
    }

    private void setVersionNumber() {
        MainContext mainContext = MainContext.INSTANCE;
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String versionInfo = packageInfo.versionName;
            Configuration configuration = mainContext.getConfiguration();
            if (configuration != null && configuration.isDevelopmentMode()) {
                versionInfo += " - " + packageInfo.versionCode + " SDK:" + Build.VERSION.SDK_INT;
            }
            ((TextView) findViewById(R.id.about_version_info)).setText(versionInfo);
        } catch (PackageManager.NameNotFoundException e) {
            mainContext.getLogger().error(this, "Version Information", e);
        }
    }

    private void setPackageName() {
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        if (configuration != null && configuration.isDevelopmentMode()) {
            TextView textView = (TextView) findViewById(R.id.about_package_name);
            textView.setText(getPackageName());
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void setApplicationName() {
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        if (configuration != null && configuration.isDevelopmentMode()) {
            TextView textView = (TextView) findViewById(R.id.about_app_name);
            textView.setText(textView.getText() + " " + MainActivity.WI_FI_ANALYZER_BETA);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
