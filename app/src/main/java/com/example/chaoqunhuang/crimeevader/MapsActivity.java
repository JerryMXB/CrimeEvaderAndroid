package com.example.chaoqunhuang.crimeevader;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.content.res.Resources;

import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.Geocoder;
import android.location.Address;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import bean.PlaceInfo;
import request.GetGooglePlaceTask;
import view.CustomInfoWindowAdapter;

public class MapsActivity extends FragmentActivity implements OnMyLocationButtonClickListener,
        OnMyLocationClickListener, OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private LocationManager manager;
    private LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("History");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Crime Evader");
        host.addTab(spec);

        TextView tv2 = (TextView) host.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv2.setTextColor(Color.parseColor("#FFFFFF"));

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#000000"));
                    View s1 = findViewById(R.id.tabline1);
                    View s2 = findViewById(R.id.tabline2);
                    s2.setBackgroundColor(Color.parseColor("#000000"));
                    s1.setBackgroundColor(Color.parseColor("#000000"));
                }

                TextView tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                Log.i(TAG, tv.getText().toString());
                tv.setTextColor(Color.parseColor("#FFFFFF"));
                View s2;
                if ("History".equals(tv.getText().toString())) {
                    s2 = findViewById(R.id.tabline1);
                } else {
                    s2 = findViewById(R.id.tabline2);
                }
                s2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });


        //track
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
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
        mMap.setMaxZoomPreference(20f);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

        } catch (Resources.NotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
        } catch(SecurityException se) {
            Log.i(TAG, se.getLocalizedMessage());
        }
        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
        mMap.setInfoWindowAdapter(adapter);
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16L));
        new getPlace().execute(String.valueOf(location.getLatitude()) + "#" + String.valueOf(location.getLongitude()));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private class getPlace extends GetGooglePlaceTask {
        @Override
        protected void onPostExecute(ArrayList<PlaceInfo> results) {
           for (PlaceInfo i : results) {
               Log.i(TAG, i.toString());
               Random random = new Random();
               int r = random.nextInt(3) + 1;
               if(r % 3 == 0) {
                   mMap.addMarker(new MarkerOptions().position(new LatLng(i.getLatitude(), i.getLongitude()))
                           .title(i.getMarkName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.camera)).snippet(i.getMarkName()));
               } else if (r % 3 == 1) {
                   mMap.addMarker(new MarkerOptions().position(new LatLng(i.getLatitude(), i.getLongitude()))
                           .title(i.getMarkName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.polaroid)).snippet(i.getMarkName()));
               } else {
                   mMap.addMarker(new MarkerOptions().position(new LatLng(i.getLatitude(), i.getLongitude()))
                           .title(i.getMarkName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.direction)).snippet(i.getMarkName()));
               }
           }
        }
    }


}