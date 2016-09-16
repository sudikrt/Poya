package com.geekyint.instaadmin;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.geekyint.instaadmin.Model.LatLong;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Object> mObjectArrayList;
    private PolylineOptions mPolylineOptions;
    private Polyline mPolyline;
    private ArrayList<LatLng> points;

    private static final String TAG = "RoundMap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        mObjectArrayList = (ArrayList<Object>) intent.getSerializableExtra("mapData");

        points = new ArrayList<LatLng>();

        mPolylineOptions = new PolylineOptions().width(2).color(Color.RED).geodesic(true);
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

        LatLng mLatLng = new LatLng(13.1818, 74.9413);

        Log.e (TAG, String.valueOf(mObjectArrayList.size()));

        if (mObjectArrayList != null) {
            Map<String, Double> map = null;
            for (int index = 0; index < mObjectArrayList.size(); index++) {
                Object object = mObjectArrayList.get(index);
                map = (Map<String, Double>) object;
                double latitude = Double.parseDouble(String.valueOf(map.get("latitude")));
                double longitude = Double.parseDouble(String.valueOf(map.get("longitude")));
                Log.e (TAG, String.valueOf(latitude + " " + longitude));
                points.add(new LatLng(latitude, longitude));
            }
            mPolylineOptions.addAll(points);
            mPolyline = mMap.addPolyline(mPolylineOptions);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 10));
        }

    }
}
