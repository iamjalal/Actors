package com.paytouch.jalal.actors.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.paytouch.jalal.actors.AppController;
import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.model.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class ActorAdapter extends BaseAdapter {

    private List<Actor> mEntries = new ArrayList<Actor>();
    private Context mContext;
    private OnActorClickListener mOnActorClickListener;

    public class SortType {
        public static final int NAME = 0;
        public static final int POPULARITY = 1;
    }

    public class PopularityIndex {
        public static final int HIGH = 25;
        public static final int MED = 10;
    }

    public ActorAdapter(Context context, OnActorClickListener listener) {
        mContext = context;
        mOnActorClickListener = listener;
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
            holder.mDescription = (TextView)convertView.findViewById(R.id.description);
            holder.mLocationIcon = (ImageView)convertView.findViewById(R.id.location_icon);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Actor actor = getItem(position);

        holder.mProfileImage.setImageUrl(actor.profilePath, AppController.getInstance().getImageLoader());

        setName(holder.mName, actor.name, position);
        setLocation(holder.mLocation, actor.location, position);
        setLocationIcon(holder.mLocationIcon, position);
        setPopularity(holder.mPopularity, actor.popularity, position);
        setDescription(holder.mDescription, actor.description, position);
        setBackground(convertView, position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnActorClickListener.onActorClick(actor);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        private NetworkImageView mProfileImage;
        private TextView mName;
        private TextView mLocation;
        private TextView mPopularity;
        private TextView mDescription;
        private ImageView mLocationIcon;
    }

    public void sort(int sortType) {
        switch (sortType) {
            case SortType.NAME:
                Collections.sort(mEntries, Actor.COMPARE_BY_NAME);
                break;
            case SortType.POPULARITY:
                Collections.sort(mEntries, Actor.COMPARE_BY_POPULARITY);
                break;
        }

        notifyDataSetChanged();
    }

    private void setPopularity(TextView view, double popularity, int position){

        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/MyriadPro-Bold.otf");
        view.setTypeface(typeFace);
        view.setText(String.format("%.2f", popularity));

        if(popularity >= PopularityIndex.HIGH) {
            view.setBackgroundResource(R.drawable.bg_popularity_high);
        }
        else if(popularity < PopularityIndex.HIGH && popularity >= PopularityIndex.MED) {
            view.setBackgroundResource(R.drawable.bg_popularity_med);
        }
        else {
            view.setBackgroundResource(R.drawable.bg_popularity_low);
        }
    }

    private void setLocation(TextView view, String location, int position) {

        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/MyriadPro-It.otf");
        view.setTypeface(typeFace);
        view.setText(location);

        if(position%2 == 0) {
            view.setTextColor(mContext.getResources().getColor(R.color.font_light));
        }
        else {
            view.setTextColor(mContext.getResources().getColor(R.color.font_dark));
        }
    }

    private void setLocationIcon(ImageView view, int position) {
        if(position%2 == 0) {
            view.setImageResource(R.drawable.ic_location_light);
        }
        else {
            view.setImageResource(R.drawable.ic_location_dark);
        }
    }

    private void setBackground(View view, int position) {
        if(position%2 == 0) {
            view.setBackgroundResource(R.drawable.bg_actor_dark);
        }
        else {
            view.setBackgroundResource(R.drawable.bg_actor_light);
        }
    }

    private void setName(TextView view, String name, int position) {
        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/MyriadPro-Bold.otf");
        view.setTypeface(typeFace);
        view.setText(name);

        if(position%2 == 0) {
            view.setTextColor(mContext.getResources().getColor(R.color.font_light));
        }
        else {
            view.setTextColor(mContext.getResources().getColor(R.color.font_dark));
        }
    }

    private void setDescription(TextView view, String description, int position) {
        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/MyriadPro-Regular.otf");
        view.setTypeface(typeFace);
        view.setText(description);

        if(position%2 == 0) {
            view.setTextColor(mContext.getResources().getColor(R.color.font_light));
        }
        else {
            view.setTextColor(mContext.getResources().getColor(R.color.font_dark));
        }
    }

    public interface OnActorClickListener {
        public void onActorClick(Actor actor);
    }
}