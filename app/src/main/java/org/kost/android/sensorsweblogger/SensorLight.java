package org.kost.android.sensorsweblogger;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

public class SensorLight extends TemplateSensor implements SensorEventListener {
    public SensorLight() {
        sensorType = Sensor.TYPE_LIGHT;
    }

}
