package com.paytouch.jalal.actors.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.paytouch.jalal.actors.AppController;
import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.model.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class ActorAdapter extends BaseAdapter {

    private List<Actor> mEntries = new ArrayList<Actor>();
    private Context mContext;

    public ActorAdapter(Context context) {
        mContext = context;
    }

    public void setEntries(List<Actor> actors) {
        mEntries = actors;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Actor getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_actor, null);

            holder = new ViewHolder();
            holder.mProfileImage = (NetworkImageView) convertView.findViewById(R.id.image);
            holder.mName = (TextView) convertView.findViewById(R.id.name);
            holder.mLocation = (TextView) convertView.findViewById(R.id.location);
            holder.mPopularity = (TextView)convertView.findViewById(R.id.popularity);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Actor actor = getItem(position);

        holder.mProfileImage.setImageUrl(actor.profilePath, AppController.getInstance().getImageLoader());
        holder.mName.setText(actor.name);
        holder.mLocation.setText(actor.location);
        holder.mPopularity.setText(actor.popularity+"");

        return convertView;
    }

    private static class ViewHolder {
        private NetworkImageView mProfileImage;
        private TextView mName;
        private TextView mLocation;
        private TextView mPopularity;
    }
}