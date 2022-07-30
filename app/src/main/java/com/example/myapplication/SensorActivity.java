package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
//this activity is just for test

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_sensor);



        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        if(sensorManager!=null){
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        }else {
            Toast.makeText(this, "Sensor not detected.", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            ((TextView)findViewById(R.id.txtValues)).setText("Vaules: "+ sensorEvent.values[0]);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}