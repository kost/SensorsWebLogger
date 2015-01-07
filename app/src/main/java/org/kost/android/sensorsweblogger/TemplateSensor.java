package org.kost.android.sensorsweblogger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.IBinder;

import java.util.HashMap;

public class TemplateSensor extends Service implements SensorEventListener {
    private static final String DEBUG_TAG = "SensorLoggerService";
    private static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;

    private SensorManager sensorManager = null;
    private Sensor sensorSingle = null;
    protected int sensorType = Sensor.TYPE_PRESSURE;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorSingle = sensorManager.getDefaultSensor(sensorType);
        if (sensorSingle!=null) {
            sensorManager.registerListener(this, sensorSingle, SENSOR_DELAY);
        } else {
        }

        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long timestamp = event.timestamp;
        float value = event.values[0];
        new SensorEventLoggerTask().execute(event);

        sensorManager.unregisterListener(this);
        stopSelf();
    }

    private class SensorEventLoggerTask extends
            AsyncTask<SensorEvent, Void, Void> {

        @Override
        protected Void doInBackground(SensorEvent... events) {
            SensorEvent event = events[0];
            long timestamp = event.timestamp;
            float value = event.values[0];
            String sensorName=event.sensor.getName();
            String lat;
            String lon;

            HashMap parms = new HashMap();

            // Log.i("MySVC","timestamp: "+String.valueOf(timestamp)+" Sensor: "+sensorName+" Value: "+String.valueOf(value));

            // SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences sharedPrefs = getSharedPreferences(getApplicationContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
            String device_name = sharedPrefs.getString("pref_device_name","MyDevice");
            String url = sharedPrefs.getString("pref_url","http://127.0.0.1/");

            // Location
            BestLastLocation LocationProv = new BestLastLocation();
            Location CurLocation = LocationProv.getBestLocation(getApplicationContext());
            if (CurLocation != null) {
                lat=String.valueOf(CurLocation.getLatitude());
                lon=String.valueOf(CurLocation.getLongitude());
            } else {
                lat="na";
                lon="na";
            }

            parms.put("device",device_name);
            parms.put("sensor",sensorName);
            parms.put("value",value);
            parms.put("timestamp",String.valueOf(timestamp));
            parms.put("lat",lat);
            parms.put("lon",lon);

            SendSensorData c=new SendSensorData();
            c.SendData(url,parms);

            return (null);
        }
    }

    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
