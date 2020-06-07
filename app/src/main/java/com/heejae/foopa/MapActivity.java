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

        LatLng seoul = new LatLng(37.588227, 126.9914173);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Seoul");
        markerOptions.position(seoul);
        gMap.addMarker(markerOptions);
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));
    }
}
