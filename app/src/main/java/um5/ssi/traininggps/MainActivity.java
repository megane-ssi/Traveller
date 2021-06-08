package um5.ssi.traininggps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private static final int PERMISSION_FINE_LOCATION = 99;
    // Google's API for location services;
    FusedLocationProviderClient fusedLocationProviderClient;

    // variable to remember if we are tracking location or not;
    boolean updateOn = false;

    // Location request is a config file for all settings related to FusedLocationProviderClient
    LocationRequest locationRequest;

    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address, tv_countOfWayPoints;
    Switch sw_locationsupdates, sw_gps;
    Button btn_newWayPoint, btn_showWayPointList, btn_showWayPointMap;

    // Current location
    Location currentLocation;

    // List of saved locations
    List<Location> savedLocations;
//______________________________________________________________________

    LocationCallback locationCallBack;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_address = findViewById(R.id.tv_address);
        tv_updates = findViewById(R.id.tv_updates);
        tv_countOfWayPoints =findViewById(R.id.tv_countOfWayPoints);

        sw_locationsupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);

        btn_newWayPoint = findViewById(R.id.btn_newWayPoint);
        btn_showWayPointList = findViewById(R.id.btn_showWayPointMap);
        btn_showWayPointMap = findViewById(R.id.btn_showWayPointMap);


        // set all properties of LocationRequest
        locationRequest = new LocationRequest();

        // How often does the default location check occur?
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);

        // How often does the location check occur when set to the most frequent update?
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // this is triggered when ever the update interval is met
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // save the location
                updateUIValues(locationResult.getLastLocation());
            }
        };



        btn_newWayPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the GPS location lv_wayPoints

                // Add the location to the global list of WayPoint
                MyApplication myApplication = (MyApplication)getApplicationContext();
                savedLocations = myApplication.getMyLocations();
                savedLocations.add(currentLocation);
            }
        });


        btn_showWayPointList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowSavedLocationList.class);
                startActivity(i);}
        });

        btn_showWayPointMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });



        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_gps.isChecked()) {
                    //most accurate _ use GPS
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("Using GPS sensors");
                } else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("Using Cell Towers + Wifi");
                }
            }
        });


        sw_locationsupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_locationsupdates.isChecked()) {
                    //turn on location tracking
                    startLocationUpdates();
                } else {
                    //turn off location tracking
                    stopLocationUpdates();
                }
            }
        });

        updateGPS();


    } //end onCreate method

    private void stopLocationUpdates() {
        tv_updates.setText("Location is not being tracked");
        tv_lat.setText("NOT tracking location");
        tv_speed.setText("NOT tracking location");
        tv_altitude.setText("NOT tracking location");
        tv_accuracy.setText("NOT tracking location");
        tv_lon.setText("NOT tracking location");
        tv_address.setText("NOT tracking location");
        tv_sensor.setText("NOT tracking location");

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);

    }

    private void startLocationUpdates() {
        tv_updates.setText("Location is being tracked");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        updateGPS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
             case PERMISSION_FINE_LOCATION:
             if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                 updateGPS();
             }
             else {
                 Toast.makeText(this, "This App requires permissions to be granted in order to work properly", Toast.LENGTH_SHORT).show();
                 finish();
             }
            break;
        }
    }

    private void updateGPS(){
        // Get permission from user to track GPS
        // get the current location from the fused client
        // update the UI - ie set all properties in their associated text view items.

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            // user provides permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>(){
                @Override
                public void onSuccess(Location location) {
                    // permission granted. Put the values of location into UI components
                    
                    if (location  != null) {
                        updateUIValues(location);
                        currentLocation = location;
                    }
                    else{
                        tv_speed.setText("Not available");
                        tv_altitude.setText("Not available");
                        tv_lat.setText("Not available");
                        tv_lon.setText("Not available");
                        tv_accuracy.setText("Not available");
                        tv_address.setText("Not available");
                    }

                }
            });
        }
        else {
            // permission not granted yet

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_FINE_LOCATION);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateUIValues(Location location) {
        //update all the text view objects with a new location.

        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));

        if (location.hasAltitude()){
            tv_altitude.setText(String.valueOf(location.getAltitude()));
        }
        else tv_altitude.setText("Not available");

        if (location.hasSpeed()){
            tv_speed.setText(String.valueOf(location.getSpeed()));
        }
        else tv_speed.setText("Not available");

        Geocoder geocoder = new Geocoder(MainActivity.this);

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            tv_address.setText(addresses.get(0).getAddressLine(0));
        }
        catch (Exception e){
            tv_address.setText("enable to get address");
        }

        MyApplication myApplication = (MyApplication)getApplicationContext();
        savedLocations = myApplication.getMyLocations();

        // Show the number of waypoints saved
        tv_countOfWayPoints.setText(Integer.toString(savedLocations.size()));

    }
}