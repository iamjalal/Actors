package com.paytouch.jalal.actors.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.paytouch.jalal.actors.AppController;
import com.paytouch.jalal.actors.R;
import com.paytouch.jalal.actors.fragment.ActorsFragment;

/**
 * Created by jalalsouky on 05/01/15.
 */
public class ActorsActivity extends Activity implements AppController.OnConnectionErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppController.getInstance().setConnectionErrorListener(this);
        setContentView(R.layout.activity_actors);

        showActorsFragment();
    }

    private void showActorsFragment() {
        ActorsFragment fragment = ActorsFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onConnectionError() {
        Toast.makeText(this, R.string.no_connection_error, Toast.LENGTH_LONG).show();
    }
}