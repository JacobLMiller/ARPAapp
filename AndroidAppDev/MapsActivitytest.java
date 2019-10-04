package com.example.mapstest;

//region packages
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//endregion


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap ARPAmap;

    //Instantiate map
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    //Map parameters
    @Override
    public void onMapReady(GoogleMap googleMap) {
        ARPAmap = googleMap;

        SetWeatherfordPin();

        EnableLocationTracking();

    }

    private void EnableLocationTracking(){
      ARPAmap.setMyLocationEnabled(true);
    }

    private void SetWeatherfordPin(){
      LatLng weatherford = new LatLng(35.5262, -98.7076);
      ARPAmap.addMarker(new MarkerOptions().position(weatherford).title("Marker in Weatherford"));
      ARPAmap.moveCamera(CameraUpdateFactory.newLatLng(weatherford));
    }
}
