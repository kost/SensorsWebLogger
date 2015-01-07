package org.kost.android.sensorsweblogger;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

public class SensorPressure extends TemplateSensor implements SensorEventListener {
    public SensorPressure() {
        sensorType = Sensor.TYPE_PRESSURE;
    }
}

