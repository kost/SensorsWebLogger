package org.kost.android.sensorsweblogger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MainService extends Service {
    HandleSensors MySensors;

    public MainService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MySensors = new HandleSensors(getApplicationContext(),null);
        MySensors.StartServiceHandlers(getApplicationContext());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        MySensors.StopServiceHandlers(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
