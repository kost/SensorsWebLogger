package org.kost.android.sensorsweblogger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView txtView;
    ScrollView scroller;
    HandleSensors MySensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_main);

        txtView=(TextView)findViewById(R.id.txtDisplay);
        txtView.setText("Initializing...\n");

        // Keep textview scrolled down
        scroller = (ScrollView) findViewById(R.id.scrollView);
        scroller.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    scroller.fullScroll(View.FOCUS_DOWN);
                }
            }
        });

        MySensors = new HandleSensors(getApplicationContext(),txtView);

        txtView.append("Logging to "+sharedPrefs.getString("pref_url","http://127.0.0.1/")+"\n");
        txtView.append("Change if URL is not good and start service!\n");

        scroller.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivityForResult(i, 1);

            return true;
        }
        if (id == R.id.action_startservice) {
            txtView.append("Starting service\n");

            Intent intent = new Intent(getApplicationContext(), MainService.class);
            startService(intent);

            return true;
        }
        if (id == R.id.action_stopservice ) {
            txtView.append("Stopping service\n");

            Intent intent = new Intent(getApplicationContext(), MainService.class );
            stopService(intent);
            return (true);
        }
        if (id == R.id.action_dispsensors) {
            HandleSensors DispSensors = new HandleSensors(getApplicationContext(),txtView);
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            txtView.append("Logging to "+sharedPrefs.getString("pref_url","http://127.0.0.1/")+"\n");
            txtView.append("Change if URL is not good and start service!\n");

            scroller.fullScroll(View.FOCUS_DOWN);
            return (true);
        }


        return super.onOptionsItemSelected(item);
    }
}
