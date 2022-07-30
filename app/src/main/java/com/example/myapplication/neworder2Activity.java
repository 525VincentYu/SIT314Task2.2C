package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.myapplication.ml.ModelUnquant;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class neworder2Activity extends neworderActivity {

    Button furniture, drygoods, food, builing, truck, van, re, mini;
    EditText other1, other2, weight, width, length, height;
    Button create;
    EditText result;
    ImageView photo;
    Button bt;
    int imageSize = 224;

    byte[] img;

    Database4 DB;
    Database3 DB1;

    ArrayList<Db2ModelClass> fullorder;


    public int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        Bundle extras = getIntent().getExtras();
        //get the information from last page
        if (extras != null) {
            String reciever = extras.getString("key");
            String pick = extras.getString("key1");
            double l1 = extras.getDouble("key2");
            double l2 = extras.getDouble("key3");
            double l3 = extras.getDouble("key4");
            double l4 = extras.getDouble("key5");
            String pickuplocation = extras.getString("key6");
            String dropofflocation = extras.getString("key7");
            double distance = extras.getDouble("key8");



            DB = new Database4(this);
            DB1 = new Database3(this);
            setContentView(R.layout.activity_neworder2);
            result = findViewById(R.id.result);
            photo = findViewById(R.id.photo);
            bt = findViewById(R.id.pp);
            furniture = findViewById(R.id.furniture);
            drygoods = findViewById(R.id.dry);
            food = findViewById(R.id.food);
            builing = findViewById(R.id.bulding);
            truck = findViewById(R.id.trunk);
            van = findViewById(R.id.van);
            re = findViewById(R.id.fre);
            mini = findViewById(R.id.mini);
            other1 = findViewById(R.id.result);
            other2 = findViewById(R.id.other2);
            weight = findViewById(R.id.weight);
            width = findViewById(R.id.width);
            length = findViewById(R.id.length);
            height = findViewById(R.id.height);
            create = findViewById(R.id.create);


            //item classification using camera
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, 1);

                        } else {
                            //Request camera permisson if we don't have it
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                        }
                    }

                }
            });

            //good type click

            furniture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    furniture.setBackground(getResources().getDrawable(R.drawable.button_border));
                    neworder.setGoodtype("furniture");
                }
            });
            drygoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drygoods.setBackground(getResources().getDrawable(R.drawable.button_border));
                    neworder.setGoodtype("drygoods");


                }
            });
            food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    food.setBackground(getResources().getDrawable(R.drawable.button_border));
                    neworder.setGoodtype("food");


                }
            });
            builing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builing.setBackground(getResources().getDrawable(R.drawable.button_border));
                    neworder.setGoodtype("bulding material");

                }
            });

            //truck type click
            truck.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    truck.setBackground(getResources().getDrawable(R.drawable.button_border));
                    //fullorder =DB.getAlldata1();
                    neworder.setVehicle(R.drawable.a);
                    flag = 1;


                }


            });
            van.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    van.setBackground(getResources().getDrawable(R.drawable.button_border));
                    neworder.setVehicle(R.drawable.b);
                    order.add(fullorder.get(2));

                    flag = 2;

                }
            });
            re.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    re.setBackground(getResources().getDrawable(R.drawable.button_border));
                    neworder.setVehicle(R.drawable.c);
                    order.add(fullorder.get(3));
                    flag = 3;

                }
            });
            mini.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mini.setBackground(getResources().getDrawable(R.drawable.button_border));
                    neworder.setVehicle(R.drawable.d);
                    order.add(fullorder.get(4));
                    flag = 4;

                }
            });
            //insert all the data to database
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    neworder.setWeight(weight.getText().toString());
                    neworder.setWidth(width.getText().toString());
                    neworder.setLength(length.getText().toString());
                    neworder.setHeight(height.getText().toString());
                    neworder.setGoodtype(result.getText().toString());
                    neworders.add(neworder);


                    if (flag == 1) {

                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArray);
                        img = byteArray.toByteArray();
                        boolean insert = DB.insertData(img, "2011 Kenworth T359 Cab Chassis", "01/2011 Kenworth T359 Rigid Truck; 8x4; ODO Showing: 533,432kms; Engine: ISL 355 Cummins; ");
                        if (insert == true) {
                            Toast.makeText(neworder2Activity.this, "data saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(neworder2Activity.this, "Data notsaved", Toast.LENGTH_SHORT).show();
                        }

                    }

                    if (flag == 2) {

                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b);
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArray);
                        img = byteArray.toByteArray();
                        boolean insert = DB.insertData(img, "2000 Hino FG Table / Tray Top", "05/2000 Hino Tray Body Truck FG1J; 4x2; Colour: White; ODO Showing: 894,119 kms; GVM: 15,000 kgs; ");
                        if (insert == true) {
                            Toast.makeText(neworder2Activity.this, "data saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(neworder2Activity.this, "Data notsaved", Toast.LENGTH_SHORT).show();
                        }

                    }
                    if (flag == 3) {

                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b);
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArray);
                        img = byteArray.toByteArray();
                        boolean insert = DB.insertData(img, "2005 Hino 300 Series 916 Tipper", "2005 Hino Dutro Tip Truck; 4x2; Approx Bin Length: 3.6m; Approx Bin Height: 44cm; Manual Transmission;  ");
                        if (insert == true) {
                            Toast.makeText(neworder2Activity.this, "data saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(neworder2Activity.this, "Data notsaved", Toast.LENGTH_SHORT).show();
                        }

                    }
                    boolean insert1 = DB1.insertData(img, "Ning", pick, reciever, weight.getText().toString(), neworder.getGoodtype(), width.getText().toString(), height.getText().toString(), length.getText().toString(), pickuplocation, dropofflocation, l1, l2, l3, l4, distance);
                    if (insert1) {
                        Toast.makeText(neworder2Activity.this, "DDDDDDDData saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(neworder2Activity.this, "DDDDAta not saved", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), MyorderActivity.class);
                    //intent.putExtra("key",flag);
                    Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);

                    startActivity(intent1);
                    //startActivity(intent);

                }
            });


        }}
    //load photo by camera
        @Override
        public void onActivityResult ( int requestcode, int resultCode, @Nullable Intent data){
            if (requestcode == 1 && resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                photo.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }

            super.onActivityResult(requestcode, resultCode, data);
        }

        ////machine learning model using

        private void classifyImage (Bitmap image){
            try {
                ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                byteBuffer.order(ByteOrder.nativeOrder());
                int[] intValues = new int[imageSize * imageSize];
                image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixel = 0;

                for (int i = 0; i < imageSize; i++) {
                    for (int j = 0; j < imageSize; j++) {
                        int val = intValues[pixel++];//RGB
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                    }
                }
                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                ModelUnquant.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                float[] confidences = outputFeature0.getFloatArray();
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {"Sofa", "Television", "Table", "Desk"};
                result.setText(classes[maxPos]);


                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                // TODO Handle the exception
            }


        }
    }
