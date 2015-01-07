package org.kost.android.sensorsweblogger;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

public class SensorRelativeHumidity extends TemplateSensor implements SensorEventListener {
    public SensorRelativeHumidity() {
        sensorType = Sensor.TYPE_RELATIVE_HUMIDITY;
    }
}
