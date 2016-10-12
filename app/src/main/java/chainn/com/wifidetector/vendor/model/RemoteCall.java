package chainn.com.wifidetector.vendor.model;

/**
 * Created by xuchen on 16/10/12.
 */

import android.os.AsyncTask;

import chainn.com.wifidetector.Logger;
import chainn.com.wifidetector.MainContext;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

class RemoteCall extends AsyncTask<String, Void, String> {
    static final String MAC_VENDOR_LOOKUP = "http://api.macvendors.com/%s";

    @Override
    protected String doInBackground(String... params) {
        if (params == null || params.length < 1 || StringUtils.isBlank(params[0])) {
            return StringUtils.EMPTY;
        }
        String macAddress = params[0];
        String request = String.format(MAC_VENDOR_LOOKUP, macAddress);
        BufferedReader bufferedReader = null;
        try {
            URLConnection urlConnection = getURLConnection(request);
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            String vendorName = VendorNameUtils.cleanVendorName(response.toString().trim());
            if (StringUtils.isNotBlank(vendorName)) {
                return new RemoteResult(macAddress, vendorName).toJson();
            }
            return StringUtils.EMPTY;
        } catch (Exception e) {
            return StringUtils.EMPTY;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    URLConnection getURLConnection(String request) throws IOException {
        return new URL(request).openConnection();
    }

    @Override
    protected void onPostExecute(String result) {
        if (StringUtils.isNotBlank(result)) {
            Logger logger = MainContext.INSTANCE.getLogger();
            try {
                RemoteResult remoteResult = new RemoteResult(result);
                String macAddress = remoteResult.getMacAddress();
                String vendorName = remoteResult.getVendorName();
                if (StringUtils.isNotBlank(vendorName) && StringUtils.isNotBlank(macAddress)) {
                    logger.info(this, macAddress + " " + vendorName);
                    Database database = MainContext.INSTANCE.getDatabase();
                    database.insert(macAddress, vendorName);
                }
            } catch (JSONException e) {
                logger.error(this, result, e);
            }
        }
    }
}
