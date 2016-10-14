package chainn.com.wifidetector.vendor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.vendor.model.VendorService;

public class VendorFragment extends ListFragment {
    private VendorAdapter vendorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vendor_content, container, false);
        VendorService vendorService = MainContext.INSTANCE.getVendorService();
        vendorAdapter = new VendorAdapter(getActivity(), vendorService.findAll());
        setListAdapter(vendorAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VendorService vendorService = MainContext.INSTANCE.getVendorService();
        vendorAdapter.setVendors(vendorService.findAll());
    }

}
