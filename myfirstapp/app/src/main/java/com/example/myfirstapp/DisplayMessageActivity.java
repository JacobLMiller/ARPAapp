package com.example.myfirstapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class DisplayMessageActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        //Tutorial I followed forced me to bring a TextView string over. If I remove it, it crashes the app.
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);



        //CALLS FORTH THE POWER OF THE GOOGLE MAPS!!
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //Here's where all the junk starts...
    @Override
    public void onMapReady(GoogleMap googleMap) {
        int x = 55;

        //Makes it where the google maps api doesn't start by viewing the entire world.
        float zoomLevel = 18.0f;
        //Initializes map, gives it a name.
        map = googleMap;


        //Creates a circle marker with a an inputted radius at given coordinates.
        Circle circle;

        //Adds a Circle Radius marker
        circle = googleMap.addCircle(new CircleOptions().center(new LatLng(35.535700, -98.707086)).radius(42)
                .strokeWidth(10).strokeColor(Color.BLUE).fillColor(Color.argb(128, 255, 0, 0)).clickable(true));


        googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener()
        {
            @Override
            public void onCircleClick(Circle circle){
                showAddItemDialog(DisplayMessageActivity.this, "Stafford Building");
            }
        });


        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        double currentLatitude = 35.535700;
        double currentLongitude = -98.707086;
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        //Takes coordinates found from google maps and puts them in as a marker
        LatLng Stafford = new LatLng(35.535700, -98.707086);
        siteLocationCreator(Stafford, "Stafford Building");

        //Stafford Building Polyline. TO DO: Make this code easier to edit and multipurpose.
        Polyline staffordPolyline = googleMap.addPolyline((new PolylineOptions()).clickable(true).add(new LatLng(35.53583, -98.706700),new LatLng(35.53557, -98.706700),new LatLng(35.53557, -98.707520), new LatLng(35.53583, -98.707520),
                new LatLng(35.53583, -98.706700)));
        staffordPolyline.setTag("A");
        staffordPolyline.setColor(Color.RED);

        //Add onClick
        googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener()
        {
            @Override
            public void onPolylineClick(Polyline polyline)
            {
                //Make a Pop Up that asks the user if he/she wants to fill out a site form);
                showAddItemDialog(DisplayMessageActivity.this, "Stafford Building");
            }
        });


        LatLng FineArts = new LatLng(35.537242, -98.707669);
        siteLocationCreator(FineArts, "Fine Arts Building");

    }
    private void showAddItemDialog(Context c, final String siteFormName) {
        //the R.style.style lets you choose how the dialog box looks
        AlertDialog dialog = new AlertDialog.Builder(c, R.style.MyAlertDialogStyle)
                .setTitle("Would you like to visit this site?")
                .setMessage("Visit Site and Complete Visit Form?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(DisplayMessageActivity.this, ARPAFormActivity.class);
                        intent.putExtra("message",siteFormName);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("NO", null)
                .create();
        dialog.show();
    }

    public void siteLocationCreator(LatLng x, String y){

        //Creates marker at the given coordinates and names it based on given string
        map.addMarker(new MarkerOptions().position(x).title(y));

        //Moves Camera when Marker is selected to given coordinates
        map.moveCamera(CameraUpdateFactory.newLatLng(x));

    }

}
