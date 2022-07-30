package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import static android.Manifest.permission.CALL_PHONE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;

public class MmapsActivity extends FragmentActivity implements OnMapReadyCallback ,TaskLoadedCallback, PaymentResultListener {

    GoogleMap mMap;
    ActivityMapsBinding binding;
    ArrayList<LatLng> latLngs =new ArrayList<LatLng>();
    ArrayList<LatLng> latLngs1 =new ArrayList<LatLng>();
    Database3 DB;
    Button book,call;
    int position = 1;
    double distance;
    TextView pick,drop,fare,time;
    MarkerOptions place1,place2;
    Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        book = findViewById(R.id.book);
        call = findViewById(R.id.call);
        // set pay Api
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_cW5DCM39mcnXBu");
                JSONObject object = new JSONObject();
                //bill information,did not store in the database so use the default information
                try {
                    object.put("name","Vincent");
                    object.put("description","Test Payment");
                    object.put("currency","INR");
                    object.put("amount",100);
                    object.put("prefill.contact","8172712");
                    object.put("prefill.email","1784035059@qq.com");
                    checkout.open(MmapsActivity.this,object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String phone = "1888266322";
                String s = "tel:" + phone;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                } else {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }

            }
        });

        //get the position of item click
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("key");
        }



        pick = findViewById(R.id.pick);
        drop = findViewById(R.id.drop);
        fare = findViewById(R.id.fare);
        time = findViewById(R.id.time);
        DB = new Database3(this);
        distance = Double.parseDouble(DB.getdistance(position));
        pick.setText("Pick Up Location:"+DB.getpickuplocation(position));
         drop.setText("Drop Off Location:"+DB.getdropofflocation(position));
        double f = distance;
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        fare.setText(Double.toString(f1*5)+" $");
         time.setText(Double.toString(f1*3)+" Minutes");


        latLngs = DB.getAlldata1();
        latLngs1 = DB.getAlldata2();
        place1 = new MarkerOptions().position(latLngs.get(0)).title("pickup location");
        place2 = new MarkerOptions().position(latLngs1.get(0)).title("drop off location");

        String url = getUrl(place1.getPosition(),place2.getPosition(),"driving");
        new FetchURL(MmapsActivity.this).execute(url, "driving");

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        LatLngBounds.Builder builder  =new LatLngBounds.Builder();
        builder.include(latLngs.get(0));
        builder.include(latLngs1.get(0));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),100));

    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline!=null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage("s");
        builder.show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "sdsdsd", Toast.LENGTH_SHORT).show();

    }
}
