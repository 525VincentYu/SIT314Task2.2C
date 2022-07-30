package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

//this Activity never used

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback{

    private GoogleMap mMap;

    private ActivityMapsBinding binding;
    ArrayList<LatLng> latLngs =new ArrayList<LatLng>();
    ArrayList<LatLng> latLngs1 =new ArrayList<LatLng>();
    Database3 DB;
    int position = 1;
    double distance;
    TextView pick,drop,fare,time;
    MarkerOptions place1,place2;
    Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("key");
        }

        pick = findViewById(R.id.pick);
        drop = findViewById(R.id.drop);
        fare = findViewById(R.id.fare);
        time = findViewById(R.id.time);
        DB = new Database3(this);
        //distance = Double.parseDouble(DB.getdistance(1));
        pick.setText("Pick Up Location:");
       // drop.setText("Drop Off Location:"+DB.getdropofflocation(1));
        //fare.setText(Double.toString(distance*5)+" $");
       // time.setText(Double.toString(distance*3)+" Minutes");


        latLngs = DB.getAlldata1();
        latLngs1 = DB.getAlldata2();
        place1 = new MarkerOptions().position(latLngs.get(0)).title("pickup location");
        place2 = new MarkerOptions().position(latLngs1.get(0)).title("drop off location");

        String url = getUrl(place1.getPosition(),place2.getPosition(),"driving");
        new FetchURL(MapsActivity.this).execute(url, "driving");




        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" +parameters + "&key=" +getString(R.string.map_key);
        return url;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        LatLngBounds.Builder builder  =new LatLngBounds.Builder();
        builder.include(latLngs.get(0));
        builder.include(latLngs1.get(0));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),100));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngs1.get(0)));


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
         //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));



    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline!=null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}