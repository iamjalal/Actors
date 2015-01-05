package com.paytouch.jalal.actors.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.activity.DetailsActivity;
import com.paytouch.jalal.actors.adapter.ActorAdapter;
import com.paytouch.jalal.actors.model.Actor;
import com.paytouch.jalal.actors.parser.ActorsParser;
import com.paytouch.jalal.actors.request.ActorsRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class ActorsFragment extends Fragment implements ActorsParser.OnResponseListener{

    private static final String BUNDLE_ACTORS = "actors";

    private ActorsRequest mActorsRequest;

    private ActorAdapter mAdapter;

    private List<Actor> mActors;

    public static ActorsFragment newInstance() {
        ActorsFragment fragment = new ActorsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActorsRequest = new ActorsRequest(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mActors == null) {
            mActorsRequest.request();
        }
        else {
            onDataReady(mActors);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            mActors = savedInstanceState.getParcelableArrayList(BUNDLE_ACTORS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_actors, container, false);

        mAdapter = new ActorAdapter(getActivity());
        ListView list = (ListView)view.findViewById(R.id.listView);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Actor actor = mActors.get(position);
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.BUNDLE_ACTOR, actor);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDataReady(List<Actor> actors) {
        mActors = actors;
        mAdapter.setEntries(mActors);
    }

    @Override
    public void onError() {
        Toast.makeText(getActivity(), R.string.request_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(BUNDLE_ACTORS, new ArrayList<Parcelable>(mActors));
    }

}