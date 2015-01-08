package com.paytouch.jalal.actors.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.activity.ActorsActivity;
import com.paytouch.jalal.actors.activity.DetailsActivity;
import com.paytouch.jalal.actors.activity.SearchActivity;
import com.paytouch.jalal.actors.adapter.ActorAdapter;
import com.paytouch.jalal.actors.model.Actor;
import com.paytouch.jalal.actors.parser.ActorsParser;
import com.paytouch.jalal.actors.request.ActorsRequest;
import com.paytouch.jalal.actors.util.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class ActorsFragment extends Fragment implements ActorsParser.OnResponseListener,
        EndlessScrollListener.OnEndReachedListener, ActorAdapter.OnActorClickListener{

    public static final String BUNDLE_ACTORS = "actors";
    private static final String BUNDLE_PAGE = "page";

    public static final int SEARCH_REQUEST_CODE = 100;

    private ActorsRequest mActorsRequest;
    private EndlessScrollListener mEndlessScrollListener;
    private ActorAdapter mAdapter;
    private List<Actor> mActors;
    private int mCurrentPage;

    public static ActorsFragment newInstance(List<Actor> actors) {
        ActorsFragment fragment = new ActorsFragment();

        if(actors != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(BUNDLE_ACTORS, new ArrayList<Parcelable>(actors));
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActorsRequest = new ActorsRequest(this);
        mEndlessScrollListener = new EndlessScrollListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mActors == null) {
            mCurrentPage += 1;
            mActorsRequest.request(mCurrentPage);
            getActivity().setProgressBarIndeterminateVisibility(true);
        }
        else {
            onDataReady(null);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState != null) {
            mActors = savedInstanceState.getParcelableArrayList(BUNDLE_ACTORS);
            mCurrentPage = savedInstanceState.getInt(BUNDLE_PAGE);
        }
        else {
            Bundle args = getArguments();
            if(args != null) {
                mActors = args.getParcelableArrayList(BUNDLE_ACTORS);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_actors, container, false);

        mAdapter = new ActorAdapter(getActivity(), this);
        ListView list = (ListView)view.findViewById(R.id.listView);
        list.setAdapter(mAdapter);
        if(getTag() == ActorsActivity.ACTOR_FRAGMENT) {
            list.setOnScrollListener(mEndlessScrollListener);
        }

        return view;
    }

    @Override
    public void onDataReady(List<Actor> actors) {
        getActivity().setProgressBarIndeterminateVisibility(false);

        if(mActors == null) {
            mActors = new ArrayList<Actor>();
        }

        if(actors != null) {
            mActors.addAll(actors);
        }

        mAdapter.setEntries(mActors);
    }

    @Override
    public void onEndReached() {
        mCurrentPage += 1;
        mActorsRequest.request(mCurrentPage);
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void onError() {
        Toast.makeText(getActivity(), R.string.request_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu_actors, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putParcelableArrayListExtra(SearchActivity.BUNDLE_ACTORS,
                        new ArrayList<Actor>(mActors));
                getActivity().startActivityForResult(intent, SEARCH_REQUEST_CODE);
                return true;
            case R.id.action_by_name:
                mAdapter.sort(ActorAdapter.SortType.NAME);
                return true;
            case R.id.action_by_popularity:
                mAdapter.sort(ActorAdapter.SortType.POPULARITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_ACTORS, new ArrayList<Parcelable>(mActors));
        outState.putInt(BUNDLE_PAGE, mCurrentPage);
    }

    @Override
    public void onActorClick(Actor actor) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.BUNDLE_ACTOR, actor);
        startActivity(intent);
    }
}