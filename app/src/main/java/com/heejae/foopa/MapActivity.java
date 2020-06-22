package com.heejae.foopa;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        gMap = googleMap;
        myApp = (MyApplication) this.getApplication();

        // Default location
        LatLng location_here = new LatLng(37.588967, 126.9920299);
        double loc_x = myApp.getlocationX();
        double loc_y = myApp.getlocationY();
        if (loc_x != 0.0 && loc_y != 0.0){
            location_here = new LatLng(loc_x, loc_y);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("My location");
        markerOptions.position(location_here);
        gMap.addMarker(markerOptions);
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location_here, 16));
    }
}
