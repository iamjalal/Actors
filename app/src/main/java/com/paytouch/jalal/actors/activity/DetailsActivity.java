package com.paytouch.jalal.actors.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.paytouch.jalal.actors.AppController;
import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.fragment.DetailFragment;
import com.paytouch.jalal.actors.model.Actor;
import com.paytouch.jalal.actors.model.Film;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class DetailsActivity extends Activity {

    public static final String BUNDLE_ACTOR = "actor";

    private Actor mActor;

    public static DetailFragment newInstance(Actor actor) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_ACTOR, actor);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        if(savedInstanceState != null) {
            mActor = savedInstanceState.getParcelable(BUNDLE_ACTOR);
        }
        else {
            Bundle args = getIntent().getExtras();
            if(args != null) {
                mActor = args.getParcelable(BUNDLE_ACTOR);
            }
        }

        updateUi();
    }

    private void updateUi() {

        NetworkImageView image = (NetworkImageView)findViewById(R.id.actor_image);
        image.setImageUrl(mActor.profilePath, AppController.getInstance().getImageLoader());

        TextView name = (TextView)findViewById(R.id.actor_name);
        name.setText(mActor.name);

        TextView location = (TextView)findViewById(R.id.actor_location);
        location.setText(mActor.location);

        TextView description = (TextView)findViewById(R.id.actor_description);
        description.setText(mActor.description);

        LinearLayout filmography = (LinearLayout)findViewById(R.id.filmography_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        for(Film film : mActor.filmography) {
            View filmView = inflater.inflate(R.layout.item_film, filmography, false);

            NetworkImageView poster = (NetworkImageView)filmView.findViewById(R.id.poster_image);
            poster.setImageUrl(film.posterPath, AppController.getInstance().getImageLoader());

            TextView title = (TextView)filmView.findViewById(R.id.title);
            title.setText(film.title);

            TextView release = (TextView)filmView.findViewById(R.id.release_date);
            release.setText(film.releaseDate+"");

            TextView voteAverage = (TextView)filmView.findViewById(R.id.vote_average);
            voteAverage.setText(film.voteAverage+"");

            TextView voteCount = (TextView)filmView.findViewById(R.id.vote_count);
            voteCount.setText(film.voteCount+"");

            filmography.addView(filmView);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(BUNDLE_ACTOR, mActor);
    }
}
