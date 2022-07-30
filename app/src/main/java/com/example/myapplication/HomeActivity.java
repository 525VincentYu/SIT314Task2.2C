package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ImageButton menu;
    ImageButton addOrder;
    Button cla;
    Database2 DB;
    RecyclerView recyclerView;

    ArrayList<Db2ModelClass> db2ModelClassesArrayList;






























    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        DB =new Database2(this);
        db2ModelClassesArrayList = new ArrayList<>();


        menu = findViewById(R.id.menubtn1);
        cla = findViewById(R.id.classi);
        addOrder = findViewById(R.id.addOrderbtn1);

        recyclerView = findViewById(R.id.re);
        db2ModelClassesArrayList=DB.getAlldata();
        MyAdapter myAdapter = new MyAdapter(db2ModelClassesArrayList);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
 //turn to add order page
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),neworderActivity.class);
                startActivity(intent);


            }
        });
//turn to item classification page
        cla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ImageActivity.class);
                startActivity(intent);

            }
        });































    }






    public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
        addOrder.setVisibility(View.INVISIBLE);
        //addOrder.setClickable(false);
        menu.setVisibility(View.INVISIBLE);




       // menu.setClickable(false);


    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item1:

                addOrder.setVisibility(View.VISIBLE);
                menu.setVisibility(View.VISIBLE);

                return true;
            case R.id.item2:

                return true;

            case R.id.item3:
                Intent intent = new Intent(getApplicationContext(),MyorderActivity.class);
                startActivity(intent);



                return true;

            default:
                return false;

        }
    }



}