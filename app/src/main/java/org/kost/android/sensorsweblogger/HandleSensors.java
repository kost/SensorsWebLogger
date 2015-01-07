package org.kost.android.sensorsweblogger;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HandleSensors {
    Sensor sensorSingle = null;
    List<Class<?>> sensorClasses = new ArrayList<Class<?>>();

    public HandleSensors (Context context, TextView txtView) {
        SensorManager sensorManager = null;

        if (txtView != null) txtView.append("Detecting Sensors\n");

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            if (txtView != null) txtView.append("Sensor: " + sensor.getName() + "\n");
        }

        sensorSingle = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensorSingle != null) {
            sensorClasses.add(SensorLight.class);
        } else {
            if (txtView != null) txtView.append("No light sensor!\n");
        }

        sensorSingle = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (sensorSingle != null) {
            sensorClasses.add(SensorAmbientTemperature.class);
        } else {
            if (txtView != null) txtView.append("No ambient temperature sensor!\n");
        }

	sensorSingle = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
	if (sensorSingle != null) {
	    sensorClasses.add(SensorTemperature.class);
	} else {
	    if (txtView != null) txtView.append("No device temperature sensor!\n");
	}

        sensorSingle = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (sensorSingle != null) {
            sensorClasses.add(SensorPressure.class);
        } else {
            if (txtView != null) txtView.append("No pressure sensor!\n");
        }

        sensorSingle = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (sensorSingle != null) {
            sensorClasses.add(SensorRelativeHumidity.class);
        } else {
            if (txtView != null) txtView.append("No relative humidity sensor!\n");
        }

    }

    public long getInterval (Context context) {
        // getDefaultSharedPreferences(this); cannot be used since it is private
        // SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sharedPrefs = context.getSharedPreferences(context.getPackageName() + "_preferences",Context.MODE_PRIVATE);

        Integer freqint = Integer.getInteger(sharedPrefs.getString("sync_frequency","15"),15);
        long interval;

        switch(freqint) {
            case 15:
                interval= AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                break;
            case 30:
                interval=AlarmManager.INTERVAL_HALF_HOUR;
                break;
            case 60:
                interval=AlarmManager.INTERVAL_HOUR;
                break;
            case 1440:
                interval=AlarmManager.INTERVAL_DAY;
                break;
            default:
                interval=AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        }
        return(interval);

    }

    public void StartServiceHandlers (Context context) {
        for (Class<?> sensorSingle : sensorClasses) {
            AlarmManager scheduler = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context.getApplicationContext(), sensorSingle);
            PendingIntent scheduledIntent = PendingIntent.getService(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            scheduler.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), getInterval(context), scheduledIntent);
        }
    }

    public void StopServiceHandlers (Context context) {
        for( Class<?> sensorSingle: sensorClasses ) {
            AlarmManager scheduler = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context.getApplicationContext(), sensorSingle);
            PendingIntent scheduledIntent = PendingIntent.getService(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            scheduler.cancel(scheduledIntent);
            context.stopService(intent);
        }
    }
}
