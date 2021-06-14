package um5.ssi.traininggps;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    List<WayPoint> allWayPoints = new ArrayList<>();

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MapsActivity.this);
        allWayPoints = dataBaseHelper.getAll();
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
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        LatLng _default = new LatLng(45, -5);


        LatLng lastLocationPlaced = _default;
        LatLng focusedLocation = _default;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MapsActivity.this);
        allWayPoints = dataBaseHelper.getAll();
        for (WayPoint wayPoint : allWayPoints) {
            lastLocationPlaced = new LatLng(wayPoint.getLat(),wayPoint.getLon());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(lastLocationPlaced);
            markerOptions.title(wayPoint.getDate()+wayPoint.getTitle());
            mMap.addMarker(markerOptions);
            //focus depends on where we have reached MapsActivity either the ShowSaved.. or the MainActivity
            //from the ShowSavedLocationList
            if (wayPoint.getId()==id){
                focusedLocation = new LatLng(wayPoint.getLat(),wayPoint.getLon());
            }
            //from the MainActivity
            else {
                focusedLocation = lastLocationPlaced;
            }
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(focusedLocation,12.0f));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });
    }
}