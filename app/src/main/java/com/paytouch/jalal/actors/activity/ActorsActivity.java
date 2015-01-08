package com.paytouch.jalal.actors.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.paytouch.jalal.actors.AppController;
import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.fragment.ActorsFragment;
import com.paytouch.jalal.actors.model.Actor;

import java.util.List;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class ActorsActivity extends Activity implements AppController.OnConnectionErrorListener {

    public static final String ACTOR_FRAGMENT = "actors_frag";
    private static final String RESULT_FRAGMENT = "result_frag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        AppController.getInstance().setConnectionErrorListener(this);
        setContentView(R.layout.activity_actors);

        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentByTag(ACTOR_FRAGMENT) == null) {
            addActorsFragment(false, ACTOR_FRAGMENT, null);
        }
    }

    private void addActorsFragment(boolean addToBackStack, String tag, List<Actor> actors) {
        ActorsFragment fragment = ActorsFragment.newInstance(actors);
        FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag);
        if(addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onConnectionError() {
        Toast.makeText(this, R.string.no_connection_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ActorsFragment.SEARCH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<Actor> actors = data.getParcelableArrayListExtra(SearchActivity.BUNDLE_ACTORS);
            addActorsFragment(true, RESULT_FRAGMENT, actors);
        }
    }
}