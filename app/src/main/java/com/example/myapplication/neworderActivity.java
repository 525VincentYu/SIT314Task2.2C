package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class neworderActivity extends LoginActivity {

    TextView newordername,pickuotime,pickuplocation, dropofflocation;
    CalendarView calendarView;
    Button button;
    public Neworder neworder;
    double l1,l2,l3,l4,distance;
    String sType;

    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    //auto complete place to chose the destination and origin then insert the location data to database
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == RESULT_OK) {
                Toast.makeText(neworderActivity.this, "11111", Toast.LENGTH_SHORT).show();
                if (result.getData() != null) {
                    Toast.makeText(neworderActivity.this, "2222", Toast.LENGTH_SHORT).show();
                    Place place = Autocomplete.getPlaceFromIntent(result.getData());
                    pickuplocation.setText(place.getAddress());

                    l1=place.getLatLng().latitude;
                    l2 = place.getLatLng().longitude;

                }
            }
        }
    });
    ActivityResultLauncher<Intent> startForResult1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == RESULT_OK) {
                Toast.makeText(neworderActivity.this, "11111", Toast.LENGTH_SHORT).show();
                if (result.getData() != null) {
                    Toast.makeText(neworderActivity.this, "2222", Toast.LENGTH_SHORT).show();
                    Place place = Autocomplete.getPlaceFromIntent(result.getData());
                    dropofflocation.setText(place.getAddress());
                    l3 = place.getLatLng().latitude;
                    l4 = place.getLatLng().longitude;

                    //double lg=place.getLatLng().latitude;

                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder);
        neworder = new Neworder();
        dropofflocation = findViewById(R.id.dropofflocation);

        newordername = findViewById(R.id.newodername);
        pickuotime = findViewById(R.id.pickuptime);
        pickuplocation = findViewById(R.id.pickuplocation);
        button = findViewById(R.id.next);
        calendarView = findViewById(R.id.calendarView);
        Places.initialize(getApplicationContext(), "AIzaSyDOoEC0olIAHCMYSBI2TPt5gGzx43LVB-s");

        pickuplocation.setFocusable(false);
        pickuplocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sType = "source";
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(neworderActivity.this);
                startForResult.launch(intent);

            }
        });
        dropofflocation.setFocusable(false);
        dropofflocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sType = "destination";
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(neworderActivity.this);
                startForResult1.launch(intent);

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                distance(l1,l2,l3,l4);

                neworder.setReciver(newordername.getText().toString());
                neworder.setPickuptime(pickuotime.getText().toString());
                neworder.setPickuolocation(pickuplocation.getText().toString());
                neworder.setCalendarView(calendarView);

                Intent intent = new Intent(getApplicationContext(),neworder2Activity.class);
                intent.putExtra("key",newordername.getText().toString());
                intent.putExtra("key1",pickuotime.getText().toString());
                intent.putExtra("key2",l1);
                intent.putExtra("key3",l2);
                intent.putExtra("key4",l3);
                intent.putExtra("key5",l4);
                intent.putExtra("key6",pickuplocation.getText().toString());
                intent.putExtra("key7",dropofflocation.getText().toString());
                intent.putExtra("key8",distance);



                startActivity(intent);



            }
        });

    }
    //calculate the distance by data

    private void distance(double l1, double l2, double l3, double l4) {

        double longdiff = l2 - l4;
        distance = Math.sin(deg2rad(l1))
                * Math.sin(deg2rad(l3))
                + Math.cos(deg2rad(l1))
                * Math.cos(deg2rad(l3))
                * Math.cos(deg2rad(longdiff));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;


    }

    private double rad2deg(double distance) {
        return (distance * 180.0/Math.PI);
    }

    private double deg2rad(double l1) {
        return (l1*Math.PI/180.0);

    }
}