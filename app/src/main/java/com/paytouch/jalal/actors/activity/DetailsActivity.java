package com.paytouch.jalal.actors.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.paytouch.jalal.actors.AppController;
import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.model.Actor;
import com.paytouch.jalal.actors.model.Film;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class DetailsActivity extends Activity {

    public static final String BUNDLE_ACTOR = "actor";
    private Actor mActor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
        name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf"));
        name.setText(mActor.name);

        TextView location = (TextView)findViewById(R.id.actor_location);
        location.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-It.otf"));
        location.setText(mActor.location);

        TextView description = (TextView)findViewById(R.id.actor_description);
        description.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));
        description.setText(mActor.description);

        TextView filmographyTitle = (TextView)findViewById(R.id.filmography_title);
        filmographyTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf"));

        LinearLayout filmography = (LinearLayout)findViewById(R.id.filmography_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        for(Film film : mActor.filmography) {
            View filmView = inflater.inflate(R.layout.item_film, filmography, false);

            NetworkImageView poster = (NetworkImageView)filmView.findViewById(R.id.poster_image);
            poster.setImageUrl(film.posterPath, AppController.getInstance().getImageLoader());

            TextView title = (TextView)filmView.findViewById(R.id.title);
            title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf"));
            title.setText(film.title);

            TextView release = (TextView)filmView.findViewById(R.id.release_date);
            release.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));
            release.setText(getDate(film.releaseDate));

            TextView voteAverage = (TextView)filmView.findViewById(R.id.vote_average);
            voteAverage.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf"));
            voteAverage.setText(film.voteAverage+"");

            TextView voteCount = (TextView)filmView.findViewById(R.id.vote_count);
            voteCount.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));
            voteCount.setText(film.voteCount+" "+getResources().getString(R.string.vote_count_literal));

            filmography.addView(filmView);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(BUNDLE_ACTOR, mActor);
    }

    private String getDate(long milis) {
        DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(milis);
        return outFormat.format(date);
    }
}
