package com.paytouch.jalal.actors.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paytouch.jalal.actors.R;

/**
 * Created by jalals on 1/7/2015.
 */
public class LocationSpinnerAdapter extends BaseAdapter {

    private Context mContext;

    public static final String[] LOCATIONS = new String[] {
            "select location",
            "Barcelona", "London", "Paris", "New York", "Dallas", "Dublin", "Helsinki", "Berlin",
            "San Francisco", "Sydney", "Vancouver", "Rome", "Chicago", "Boston", "Los Angeles"};

    public LocationSpinnerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return LOCATIONS.length;
    }

    @Override
    public Object getItem(int position) {
        return LOCATIONS[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_location, null);
            holder = new ViewHolder();
            holder.mLocation = (TextView) convertView.findViewById(R.id.location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mLocation.setText(LOCATIONS[position]);
        holder.mLocation.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
                "fonts/MyriadPro-Regular.otf"));

        return convertView;
    }

    private static class ViewHolder {
        private TextView mLocation;
    }
}
