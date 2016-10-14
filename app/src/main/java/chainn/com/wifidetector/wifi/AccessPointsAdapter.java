package chainn.com.wifidetector.wifi;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import chainn.com.wifidetector.MainContext;
import chainn.com.wifidetector.R;
import chainn.com.wifidetector.wifi.model.WiFiData;
import chainn.com.wifidetector.wifi.model.WiFiDetail;
import chainn.com.wifidetector.wifi.scanner.UpdateNotifier;

class AccessPointsAdapter extends BaseExpandableListAdapter implements UpdateNotifier {
    private final Resources resources;
    private AccessPointsAdapterData accessPointsAdapterData;
    private AccessPointsDetail accessPointsDetail;

    AccessPointsAdapter(@NonNull Context context) {
        super();
        this.resources = context.getResources();
        setAccessPointsAdapterData(new AccessPointsAdapterData());
        setAccessPointsDetail(new AccessPointsDetail());
    }

    void setAccessPointsAdapterData(@NonNull AccessPointsAdapterData accessPointsAdapterData) {
        this.accessPointsAdapterData = accessPointsAdapterData;
    }

    void setAccessPointsDetail(@NonNull AccessPointsDetail accessPointsDetail) {
        this.accessPointsDetail = accessPointsDetail;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = getView(convertView, parent);
        WiFiDetail wiFiDetail = (WiFiDetail) getGroup(groupPosition);
        accessPointsDetail.setView(resources, view, wiFiDetail, false);

        ImageView groupIndicator = (ImageView) view.findViewById(R.id.groupIndicator);
        int childrenCount = getChildrenCount(groupPosition);
        if (childrenCount > 0) {
            groupIndicator.setVisibility(View.VISIBLE);
            groupIndicator.setImageResource(isExpanded
                ? R.drawable.ic_expand_less_black_24dp
                : R.drawable.ic_expand_more_black_24dp);
            groupIndicator.setColorFilter(resources.getColor(R.color.icons_color));
        } else {
            groupIndicator.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = getView(convertView, parent);
        WiFiDetail wiFiDetail = (WiFiDetail) getChild(groupPosition, childPosition);
        accessPointsDetail.setView(resources, view, wiFiDetail, true);
        view.findViewById(R.id.groupIndicator).setVisibility(View.GONE);
        return view;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        accessPointsAdapterData.update(wiFiData);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return accessPointsAdapterData.parentsCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return accessPointsAdapterData.childrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return accessPointsAdapterData.parent(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return accessPointsAdapterData.child(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View getView(View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = MainContext.INSTANCE.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.access_points_details, parent, false);
        }
        return view;
    }
}
