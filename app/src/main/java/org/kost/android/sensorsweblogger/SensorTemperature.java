package org.kost.android.sensorsweblogger;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

public class SensorTemperature extends TemplateSensor implements SensorEventListener {
    public SensorTemperature() {
        sensorType = Sensor.TYPE_TEMPERATURE;
    }
}

