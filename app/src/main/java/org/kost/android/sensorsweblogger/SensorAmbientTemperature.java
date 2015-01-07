package org.kost.android.sensorsweblogger;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

public class SensorAmbientTemperature extends TemplateSensor implements SensorEventListener {
    public SensorAmbientTemperature() {
        sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE;
    }
}

