package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderdetailsActivity extends MyorderActivity {
    ImageView imageView;
    TextView sender,pick,receiver,drop,weight,width,height,length,quantity,type;
    Database3 DB;
    String username = "Ning";
    Button estimate;
    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        DB = new Database3(this);

        //get item click position and show specific item

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("key");
        }




        //neworder = (Neworder) getIntent().getSerializableExtra("KEY1");

        imageView = findViewById(R.id.imageView3);
        sender = findViewById(R.id.sender);
        pick = findViewById(R.id.pickuptime1);
        receiver = findViewById(R.id.receiver1);
        drop = findViewById(R.id.dropofftime);
        weight = findViewById(R.id.weight1);
        width = findViewById(R.id.width1);
        height = findViewById(R.id.height1);
        length = findViewById(R.id.length1);
        quantity = findViewById(R.id.quantity);
        type = findViewById(R.id.type1);
        estimate = findViewById(R.id.estimate);
        estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MmapsActivity.class);
                intent.putExtra("key",position);
                startActivity(intent);

            }
        });

        Bitmap cursor0 = DB.getimage(position);
        String cursor1 = DB.getsender(position);
        String cursor2 = DB.getpicktime(position);
        String cursor3 = DB.getreciver(position);
        String cursor4 = DB.getweight(position);
        String cursor5 = DB.gettype(position);
        String cursor6 = DB.getwidth(position);
        String cursor7 = DB.getheight(position);
        String cursor8 = DB.getlength(position);

        imageView.setImageBitmap(cursor0);



        sender.setText(cursor1);
        pick.setText(cursor2);
        receiver.setText(cursor3);
        drop.setText(" 1 May 20222");
        type.setText("Weight:\n" + cursor4);
        weight.setText("Type:\n" + cursor5);
        width.setText("Width:\n" + cursor6);
        height.setText("Height:\n" + cursor7);
        length.setText("Length:\n"+cursor8);
        quantity.setText("Quantity:\n1");


    }
}