package com.saxena.ThePlaceGuide;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.saxena.ThePlaceGuide.Pojo.Item;
import com.saxena.ThePlaceGuide.Pojo.Places;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.location.LocationManager.NETWORK_PROVIDER;

public class MapsActivity extends FragmentActivity {

    public static final String BASE_URL = "https://places.cit.api.here.com/places/v1/discover/search?at=";
    public static final String END_URL = "&Accept-Language=en-US%2Cen%3Bq%3D0.8&app_id=&app_code=";
    public String stringLatitude = null;//"28.7041";
    public String stringLongitude = null;//"77.1025";

    EditText search;
    Button go;
    RecyclerView recyclerView;
    TextView textView, detailsTextView;
    List<Item> results;
    LocationManager locationManager;
    android.location.LocationListener locationListener;
    int adCount = 0;
    int appOpenCount=0;
    SupportMapFragment mapFragment;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);




        appOpenCount=1;
        AdView adView = (AdView) findViewById(R.id.adView);

        search = (EditText) findViewById(R.id.editText);
        go = (Button) findViewById(R.id.button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        textView = (TextView) findViewById(R.id.textView);

        results = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter(this, results);
        Log.e("tag", "onCreate: beforeadapter");
        recyclerView.setAdapter(searchResultsAdapter);


        final AdRequest adRequest = new AdRequest.Builder().build();

        final InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Toast.makeText(MapsActivity.this, "Thank you for watching this ad!", Toast.LENGTH_SHORT).show();
            }
        });

        adView.loadAd(adRequest);

        final OkHttpClient okHttpClient = new OkHttpClient();


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adCount++;
                if (adCount == 3) {
                    interstitialAd.loadAd(adRequest);
                    adCount = 0;
                }
                try {
                    final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Searching for nearby locations...");
                    progressDialog.show();

                    String find = search.getText().toString();
                    if(find==null){
                        Toast.makeText(MapsActivity.this, "Please enter a place to search and then click on GO!", Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                    }else {

                        if (stringLatitude == null || stringLongitude == null) {
                            Toast.makeText(MapsActivity.this, "Please make sure that your phone is connected to network and then try again!", Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        } else {
                            final Request request = new Request.Builder()
                                    .url(BASE_URL + stringLatitude + "%2C" + stringLongitude + "&q=" + find + END_URL)
                                    .build();

                            okHttpClient.newCall(request).enqueue(new Callback() {
                                int failureCount=0;
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    failureCount++;
                                    Log.e("TAG", "onFailure: " + e.getLocalizedMessage());
                                    Log.e("TAG", "onFailure: " + call.request().url());
                                    if(failureCount==1){
                                        MapsActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.hide();
                                                Toast.makeText(MapsActivity.this,"Please make sure that your device has internet connection and then try again!",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        failureCount=0;
                                    }
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    final String result = response.body().string();

                                    Gson gson = new Gson();

                                    final Places places = gson.fromJson(result, Places.class);

                                    MapsActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.hide();
                                            results.clear();
                                            mMap.clear();
                                            results.addAll(places.getResults().getItems());
//                                    Log.e("Marker", "run: "+results.get(0).getPosition().get(0));
                                            Double distance = -1.0;
                                            for (int i = 0; i < results.size(); i++) {
                                                if ((results.get(i).getDistance()) / 1000 > distance) {
                                                    distance = (results.get(i).getDistance()) / 1000.0;
                                                }
                                                LatLng latLngs = new LatLng(results.get(i).getPosition().get(0), results.get(i).getPosition().get(1));
                                                String placeTitle = results.get(i).getTitle();
                                                addMarker(latLngs, placeTitle);
                                            }

                                            Log.e("distance", "run: " + distance);
                                            if (distance < 1.0) {
                                                setZoomLevel(14);
                                            } else if (distance >= 1 && distance < 10) {
                                                setZoomLevel(12);
                                            } else if (distance >= 10 && distance < 50) {
                                                setZoomLevel(9);
                                            } else if (distance >= 50 && distance < 100) {
                                                setZoomLevel(7);
                                            } else if (distance >= 100 && distance < 500) {
                                                setZoomLevel(6);
                                            } else if (distance >= 500 && distance < 1000) {
                                                setZoomLevel(4);
                                            } else if (distance >= 1000 && distance < 2000) {
                                                setZoomLevel(3);
                                            } else {
                                                setZoomLevel(1);
                                            }
                                            searchResultsAdapter.notifyDataSetChanged();
                                            Log.e("tag", "run: notify");
                                            if (results.size() == 0) {
                                                textView.setText("No Result Found...");
                                            } else {
                                                textView.setText("Search Results...");
                                            }

                                        }
                                    });

                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(MapsActivity.this, "Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction()==KeyEvent.ACTION_DOWN)&&(i==KeyEvent.KEYCODE_ENTER)){
                    Log.e("KEY_CLICK", "onKey: " );
                    adCount++;
                    if (adCount == 3) {
                        interstitialAd.loadAd(adRequest);
                        adCount = 0;
                    }
                    try {
                        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Searching for nearby locations...");
                        progressDialog.show();

                        String find = search.getText().toString();
                        if(find==null){
                            Toast.makeText(MapsActivity.this, "Please enter a place to search and then click on GO!", Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }else {

                            if (stringLatitude == null || stringLongitude == null) {
                                Toast.makeText(MapsActivity.this, "Please make sure that your phone is connected to network and then try again!", Toast.LENGTH_LONG).show();
                                progressDialog.hide();
                            } else {
                                final Request request = new Request.Builder()
                                        .url(BASE_URL + stringLatitude + "%2C" + stringLongitude + "&q=" + find + END_URL)
                                        .build();

                                okHttpClient.newCall(request).enqueue(new Callback() {
                                    int failureCount=0;
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        failureCount++;
                                        Log.e("TAG", "onFailure: " + e.getLocalizedMessage());
                                        Log.e("TAG", "onFailure: " + call.request().url());
                                        if(failureCount==1){
                                            MapsActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialog.hide();
                                                    Toast.makeText(MapsActivity.this,"Please make sure that your device has internet connection and then try again!",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                            failureCount=0;
                                        }
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                        final String result = response.body().string();

                                        Gson gson = new Gson();

                                        final Places places = gson.fromJson(result, Places.class);

                                        MapsActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.hide();
                                                results.clear();
                                                mMap.clear();
                                                results.addAll(places.getResults().getItems());
//                                    Log.e("Marker", "run: "+results.get(0).getPosition().get(0));
                                                Double distance = -1.0;
                                                for (int i = 0; i < results.size(); i++) {
                                                    if ((results.get(i).getDistance()) / 1000 > distance) {
                                                        distance = (results.get(i).getDistance()) / 1000.0;
                                                    }
                                                    LatLng latLngs = new LatLng(results.get(i).getPosition().get(0), results.get(i).getPosition().get(1));
                                                    String placeTitle = results.get(i).getTitle();
                                                    addMarker(latLngs, placeTitle);
                                                }

                                                Log.e("distance", "run: " + distance);
                                                if (distance < 1.0) {
                                                    setZoomLevel(14);
                                                } else if (distance >= 1 && distance < 10) {
                                                    setZoomLevel(12);
                                                } else if (distance >= 10 && distance < 50) {
                                                    setZoomLevel(9);
                                                } else if (distance >= 50 && distance < 100) {
                                                    setZoomLevel(7);
                                                } else if (distance >= 100 && distance < 500) {
                                                    setZoomLevel(6);
                                                } else if (distance >= 500 && distance < 1000) {
                                                    setZoomLevel(4);
                                                } else if (distance >= 1000 && distance < 2000) {
                                                    setZoomLevel(3);
                                                } else {
                                                    setZoomLevel(1);
                                                }
                                                searchResultsAdapter.notifyDataSetChanged();
                                                Log.e("tag", "run: notify");
                                                if (results.size() == 0) {
                                                    textView.setText("No Result Found...");
                                                } else {
                                                    textView.setText("Search Results...");
                                                }

                                            }
                                        });

                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(MapsActivity.this, "Please try again!", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });


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
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setTiltGesturesEnabled(true);
//
//        // Add a marker in Sydney and move the camera
////        LatLng sydney = new LatLng(34, 151);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
    @Override
    protected void onResume() {
        super.onResume();
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

// Define a listener that responds to location updates

        locationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);
                Log.e("TAG", "onLocationChanged: " + location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
                Toast.makeText(MapsActivity.this, "Enable location services to continue !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
//                finish();
            }
        };
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(NETWORK_PROVIDER, 0, 0, locationListener);

        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting current location...");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss();// when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 5000);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

//                LatLng sydney = new LatLng(-34, 151);
//                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });
    }

    @Override
    protected void onStop() {
        locationManager.removeUpdates(locationListener);
        super.onStop();
    }

    private void makeUseOfNewLocation(Location location) {
        stringLongitude= String.valueOf(location.getLongitude());
        stringLatitude= String.valueOf(location.getLatitude());
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng currentLocation = new LatLng(Double.parseDouble(stringLatitude), Double.parseDouble(stringLongitude));
                if(appOpenCount==1) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                    appOpenCount=0;
                }
            }
        });
    }

    private void addMarker(final LatLng latLng, final String placeTitle){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap.addMarker(new MarkerOptions().position(latLng).title(placeTitle));
            }
        });
    }

    private void setZoomLevel(final int zoomLevel){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng currentLocation = new LatLng(Double.parseDouble(stringLatitude), Double.parseDouble(stringLongitude));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));
            }
        });
    }

}
