package com.example.igulhane73.appnew;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

public class UserSpeedService extends Service implements SensorEventListener{
    private SensorManager       mSensorManager;
    private Sensor              mSensor;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Sensor Name: ");
        sb.append(event.sensor.getName());
        sb.append("\n");
        sb.append("Time: ");
        sb.append(event.timestamp);
        sb.append("\n");
        sb.append("Accuracy: ");
        sb.append(event.accuracy);
        sb.append("\n");

        for (float v : event.values) {
            sb.append(v);
            sb.append("\n");
        }

        Toast toast= Toast.makeText(this,sb.toString(),Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //  Do nothing
    }


}
