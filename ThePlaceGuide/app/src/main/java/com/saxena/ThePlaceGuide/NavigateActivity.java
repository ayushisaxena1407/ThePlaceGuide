package com.saxena.ThePlaceGuide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.saxena.ThePlaceGuide.R.id.map;

public class NavigateActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng latLng;
    String title;
    Double zoomLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        Intent intentNavigate = getIntent();
        latLng=new LatLng(intentNavigate.getDoubleExtra("NAV_LATITUDE",0),intentNavigate.getDoubleExtra("NAV_LONGITUDE",0));
        title=intentNavigate.getStringExtra("TITLE");
        zoomLevel=intentNavigate.getDoubleExtra("NAV_DISTANCE",0);



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
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        // Add a marker...


//        Geocoder geocoder = new Geocoder(NavigateActivity.this, Locale.getDefault());
//        List<Address> addresses = null;
//        String stateName="";
//        try {
//            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//
//            if(addresses!=null) {
//                stateName = addresses.get(0).getAddressLine(2);
//                Log.e("CountryName", "onMapReady: "+stateName );
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Log.e("ZOOM", "onMapReady: "+zoomLevel );
        if(zoomLevel<1.0){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        }else if(zoomLevel>=1&&zoomLevel<10){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        }else if(zoomLevel>=10&&zoomLevel<50){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));
        }else if(zoomLevel>=50&&zoomLevel<100){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
        }else if(zoomLevel>=100&&zoomLevel<500){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
        }else if(zoomLevel>=500&&zoomLevel<1000){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        }else if(zoomLevel>=1000&&zoomLevel<2000){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4));
        }else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 1));
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        Toast.makeText(NavigateActivity.this,"Click on the marker followed by the navigation button at the bottom of the screen to start navigaation!", Toast.LENGTH_LONG).show();;

    }

}
