package com.paytouch.jalal.actors.parser;

import com.paytouch.jalal.actors.model.Actor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class ActorsParser {

    public static final String JSON_ROOT = "data";

    private OnResponseListener mOnResponseListener;

    public ActorsParser(OnResponseListener listener) {
        mOnResponseListener = listener;
    }

    public void parse(JSONObject data) {

        List<Actor> actors = new ArrayList<Actor>();

        try {
            JSONArray root = data.getJSONArray(JSON_ROOT);
            for(int i=0; i < root.length(); i++) {
                Actor actor = new Actor(root.getJSONObject(i));
                actors.add(actor);
            }

            mOnResponseListener.onDataReady(actors);
        }
        catch (JSONException e) {
            mOnResponseListener.onError();
            e.printStackTrace();
        }
    }

    public interface OnResponseListener {
        public void onDataReady(List<Actor> actors);
        public void onError();
    }
}
