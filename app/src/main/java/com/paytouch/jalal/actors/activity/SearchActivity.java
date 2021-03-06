package com.paytouch.jalal.actors.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.adapter.LocationSpinnerAdapter;
import com.paytouch.jalal.actors.fragment.ActorsFragment;
import com.paytouch.jalal.actors.model.Actor;
import com.paytouch.jalal.actors.util.ActorSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalals on 1/7/2015.
 */
public class SearchActivity extends Activity {

    public static final String BUNDLE_ACTORS = "actors";

    private String mLocation = "";
    private boolean mIsTop;
    private int mPopularity;

    private List<Actor> mActors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle args = getIntent().getExtras();
        if(args != null) {
            mActors = args.getParcelableArrayList(BUNDLE_ACTORS);
        }

        updateUi();
    }

    private void updateUi() {

        TextView searchTitle = (TextView)findViewById(R.id.search_title);
        searchTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));

        TextView nameTitle = (TextView)findViewById(R.id.search_name_title);
        nameTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf"));

        final EditText nameEdit = (EditText)findViewById(R.id.search_name_edit);
        nameEdit.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));

        TextView locationTitle = (TextView)findViewById(R.id.search_location_title);
        locationTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf"));

        LocationSpinnerAdapter adapter = new LocationSpinnerAdapter(this);
        Spinner locationSpinner = (Spinner)findViewById(R.id.search_location_spinner);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    mLocation = LocationSpinnerAdapter.LOCATIONS[position];
                }
                else {
                    mLocation = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView isTopTitle = (TextView)findViewById(R.id.search_top_title);
        isTopTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf"));

        RadioButton radioYes = (RadioButton)findViewById(R.id.radio_yes);
        radioYes.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));
        radioYes.setChecked(mIsTop);

        RadioButton radioNo = (RadioButton)findViewById(R.id.radio_no);
        radioNo.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));
        radioNo.setChecked(!mIsTop);

        TextView popularityTitle = (TextView)findViewById(R.id.search_popularity_title);
        popularityTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf"));

        SeekBar popularityBar = (SeekBar)findViewById(R.id.search_popularity_seek);
        popularityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPopularity = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ((TextView)findViewById(R.id.seek_init_label))
                .setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));
        ((TextView)findViewById(R.id.seek_end_label))
                .setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));

        Button searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf"));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Actor> filteredActors = ActorSearch.filter(mActors, nameEdit.getText().toString(),
                        mLocation, mIsTop, mPopularity);

                Intent returnIntent = new Intent();
                returnIntent.putExtra(ActorsFragment.BUNDLE_ACTORS, new ArrayList<Actor>(filteredActors));
                setResult(Activity.RESULT_OK, returnIntent);

                finish();
            }
        });

        ImageView closeButton = (ImageView)findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
        case R.id.radio_yes:
            if (checked){
                mIsTop = true;
            }
            break;
        case R.id.radio_no:
            if (checked) {
                mIsTop = false;
            }
            break;
        }
    }
}