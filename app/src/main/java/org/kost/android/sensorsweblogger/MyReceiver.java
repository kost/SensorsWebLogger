package org.kost.android.sensorsweblogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);

        if (sharedPrefs.getBoolean("pref_on_boot",true)) {
            Intent myIntent = new Intent(context, MainService.class);
            context.startService(myIntent);
        }
    }
}

