package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {
    EditText username, password;
    Button btnLoign, btnSignUp,btnSensor;
    Database1 DB;
    Database database;
    public String user;
    public ArrayList<Db2ModelClass> order;
    public ArrayList<Neworder> neworders;
    int flag = 0;
    ImageView logo;
    View layout;




    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //keep the day mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);

        order = new ArrayList<>();
        layout = findViewById(R.id.cons);
        neworders = new ArrayList<>();

        logo = findViewById(R.id.logo2);

        username = findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        btnLoign = (Button) findViewById(R.id.buttonLogin);
        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        btnSensor = (Button)findViewById(R.id.sensor);
        DB = new Database1(this);
        database = new Database(this);
        btnSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SensorActivity.class);
                startActivity(intent);

            }
        });


        //define sensor manager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        //check sensor
        if(sensorManager!=null){
            Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if(lightSensor != null) {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }

        }else {
            Toast.makeText(this, "Sensor not detected.", Toast.LENGTH_SHORT).show();
        }




















        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLoign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();


                //check identity
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkusernampassword(user,pass);
                    if(checkuserpass == true){
                        user = username.getText().toString();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);



                        Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



    }

    // capturen light sensor change
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){

            if(sensorEvent.values[0]>0){
                Boolean insert = database.insertData(Float.valueOf(sensorEvent.values[0]));
                if(insert == true){
                    Toast.makeText(LoginActivity.this, "Light_value store successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"Light_value storeP failed", Toast.LENGTH_SHORT).show();
                }

            }
            else {

            }


            //when light sensor value more than 10000, turn to night mode
            if(sensorEvent.values[0]>10000){
                btnLoign.setBackground(getResources().getDrawable(R.drawable.button_border1));
                btnSignUp.setBackground(getResources().getDrawable(R.drawable.button_border1));
                btnSensor.setBackground(getResources().getDrawable(R.drawable.button_border1));
                logo.setImageResource(R.drawable.logo1);
                layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkcolor));

            }
            else {
                btnLoign.setBackground(getResources().getDrawable(R.drawable.button_border));
                btnSignUp.setBackground(getResources().getDrawable(R.drawable.button_border));
                btnSensor.setBackground(getResources().getDrawable(R.drawable.button_border));
                logo.setImageResource(R.drawable.logo);
                layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkcolor1));

            }


        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}