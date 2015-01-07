package com.paytouch.jalal.actors.request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.paytouch.jalal.actors.AppController;
import com.paytouch.jalal.actors.parser.ActorsParser;

import org.json.JSONObject;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class ActorsRequest {

    private static final String REQUEST_URL = "http://testandroid.pay-touch.com:8080/android-test/rest/actors?page=";
    private ActorsParser mParser;

    public ActorsRequest(ActorsParser.OnResponseListener listener) {
        mParser = new ActorsParser(listener);
    }

    public void request(int page) {

        JsonObjectRequest actorsRequest = new JsonObjectRequest(REQUEST_URL+page, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mParser.parse(response);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mParser.mOnResponseListener.onError();
            }
        });

        AppController.getInstance().addToRequestQueue(actorsRequest);

        Log.v("ACTORS", "PAGE: " + page);
    }
}
