package com.saxena.ThePlaceGuide;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.saxena.ThePlaceGuide.DetailsPojo.Access;
import com.saxena.ThePlaceGuide.DetailsPojo.Details;
import com.saxena.ThePlaceGuide.DetailsPojo.DetailsCategories;
import com.saxena.ThePlaceGuide.DetailsPojo.PhoneD;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailedActivity extends AppCompatActivity {

    TextView text, district, city, state, country, postalCode, phoneNo, category, accessibleBy, distance, website;
    FloatingActionButton callButton, openLink, navigate;
    String phone;

    String CUSTOM_TAB_PACKAGE_NAME="com.android.chrome";

    CustomTabsClient customTabsClient;
    CustomTabsSession customTabsSession;
    CustomTabsServiceConnection customTabsServiceConnection;
    CustomTabsIntent customTabsIntent;
    DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        decimalFormat = new DecimalFormat("#.000");

        text= (TextView) findViewById(R.id.placeAddressText);
        district= (TextView) findViewById(R.id.placeAddressDistrict);
        city= (TextView) findViewById(R.id.placeAddressCity);
        state= (TextView) findViewById(R.id.placeAddressState);
        country= (TextView) findViewById(R.id.placeAddressCountry);
        postalCode= (TextView) findViewById(R.id.placeAddressPostalCode);
        phoneNo= (TextView) findViewById(R.id.placePhoneDetails);
        accessibleBy= (TextView) findViewById(R.id.placeAddressAccessibleByMeans);
        category= (TextView) findViewById(R.id.placeCategoryType);
        distance= (TextView) findViewById(R.id.distanceLength);
        callButton= (FloatingActionButton) findViewById(R.id.call);
        openLink= (FloatingActionButton) findViewById(R.id.openLink);
        navigate= (FloatingActionButton) findViewById(R.id.navigate);
        website= (TextView) findViewById(R.id.placeWebsiteDetails);
        AdView adView = (AdView) findViewById(R.id.adView);


        customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                customTabsClient=client;
                customTabsClient.warmup(0L);
                customTabsSession=customTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                customTabsClient=null;
            }
        };

        CustomTabsClient.bindCustomTabsService(this,CUSTOM_TAB_PACKAGE_NAME,customTabsServiceConnection);
        customTabsIntent = new CustomTabsIntent.Builder(customTabsSession).setShowTitle(true).build();

        callButton.setVisibility(View.GONE);
        openLink.setVisibility(View.GONE);


        final AdRequest adRequest = new AdRequest.Builder().build();


        adView.loadAd(adRequest);

        final InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Toast.makeText(DetailedActivity.this, "Thank you for watching this ad!",Toast.LENGTH_SHORT).show();
            }
        });





        OkHttpClient okHttpClient = new OkHttpClient();

        setTitle("");

        final Intent intent = getIntent();
        String url = intent.getStringExtra("KEY");
        int adCount = intent.getIntExtra("AD_COUNT", 0);
        if(adCount==3){
            interstitialAd.loadAd(adRequest);
        }

        final Double latitude = intent.getDoubleExtra("LATITUDE",0);
        final Double longitude = intent.getDoubleExtra("LONGITUDE",0);
        final Double distanceRec = intent.getDoubleExtra("DISTANCE",0);
        final Double distanceReceived = distanceRec/1000;

        final ProgressDialog progressDialog = new ProgressDialog(DetailedActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading details...");
        progressDialog.show();
try {
    final Request request = new Request.Builder()
            .url(url)
            .build();
    okHttpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("TAG", "onFailure: " + e.getLocalizedMessage());
            Log.e("TAG", "onFailure: " + call.request().url());
        }

        @Override
        public void onResponse(final Call call, Response response) throws IOException {

            final String result = response.body().string();

            Gson gson = new Gson();

            final Details details = gson.fromJson(result, Details.class);

            DetailedActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.hide();
                    setTitle("" + details.getName());
                    text.setText(details.getName() + ", " + intent.getStringExtra("ADDRESS"));
                    if (details.getLocation().getAddress().getDistrict() != null) {
                        district.setText("DISTRICT : " + details.getLocation().getAddress().getDistrict());
                    } else {
                        district.setVisibility(View.GONE);
                    }
                    if (details.getLocation().getAddress().getCity() != null) {
                        city.setText("CITY : " + details.getLocation().getAddress().getCity());
                    } else {
                        city.setVisibility(View.GONE);
                    }
                    if (details.getLocation().getAddress().getPostalCode() != null) {
                        postalCode.setText("POSTAL CODE : " + details.getLocation().getAddress().getPostalCode());
                    } else {
                        postalCode.setVisibility(View.GONE);
                    }
                    if (details.getLocation().getAddress().getState() != null) {
                        state.setText("STATE : " + details.getLocation().getAddress().getState());
                    } else {
                        state.setVisibility(View.GONE);
                    }
                    if (details.getLocation().getAddress().getCountry() != null) {
                        country.setText("COUNTRY : " + details.getLocation().getAddress().getCountry());
                    } else {
                        country.setVisibility(View.GONE);
                    }

                    if ( details.getContacts()!= null) {
                        final ArrayList<PhoneD> phoneDs = details.getContacts().getPhone();
                        if (details.getContacts().getPhone() != null) {
                            if (phoneDs.size() != 0) {
                                for (int i = 0; i < phoneDs.size(); i++) {
                                    phoneNo.append(phoneDs.get(i).getValue());
                                    if ((i + 1) != phoneDs.size()) {
                                        phoneNo.append(" , ");
                                    }
                                }
                                phone = phoneDs.get(0).getValue();
                                callButton.setVisibility(View.VISIBLE);
                                callButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Log.e("DetailedActivityTag", "onClick: ");

                                        int permissionResult = ActivityCompat.checkSelfPermission(DetailedActivity.this, Manifest.permission.CALL_PHONE);
                                        if (permissionResult == PackageManager.PERMISSION_GRANTED) {
                                            Intent intent1 = new Intent();
                                            intent1.setAction(Intent.ACTION_VIEW);
                                            intent1.setData(Uri.parse("tel:" + phone));
                                            Log.e("DetailedActivityTag", "onClick: selfpermission123");
                                            startActivity(intent1);

                                        } else {
                                            Log.e("DetailedActivityTag", "onClick: selfpermission");
                                            ActivityCompat.requestPermissions(DetailedActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);

                                        }
                                    }
                                });
                            } else {
                                phoneNo.setText("Not available");
                                callButton.setVisibility(View.GONE);
                            }
                        }else {
                            phoneNo.setText("Not available");
                            callButton.setVisibility(View.GONE);
                        }} else {
                            phoneNo.setText("Not available");
                            callButton.setVisibility(View.GONE);
                        }


                    if ( details.getContacts()!= null) {
                        final ArrayList<PhoneD> websites = details.getContacts().getWebsite();
                        if (details.getContacts().getWebsite() != null) {
                            if (websites.size() != 0) {
                                Log.e("Link", "run: "+websites.get(0).getValue() +" size "+websites.size());
                                website.setText("");
                                for (int i = 0; i < websites.size(); i++) {
                                    website.append(websites.get(i).getValue());
                                    if ((i + 1) != websites.size()) {
                                        website.append(" , ");
                                    }
                                }
                                openLink.setVisibility(View.VISIBLE);
                                openLink.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customTabsIntent.launchUrl(DetailedActivity.this,Uri.parse(websites.get(0).getValue()));
//                                        Intent intentWeb = new Intent();
//                                        intentWeb.setAction(Intent.ACTION_VIEW);
//                                        intentWeb.setData(Uri.parse(websites.get(0).getValue()));
//                                        startActivity(intentWeb);
                                    }
                                });

                            } else {
                                openLink.setVisibility(View.GONE);
                                website.setText("Not available");
                            }
                        }else {
                            openLink.setVisibility(View.GONE);
                            website.setText("Not available");
                        }} else {
                        openLink.setVisibility(View.GONE);
                        website.setText("Not available");
                    }




                    ArrayList<DetailsCategories> detailsCategories = details.getCategories();
                    if (detailsCategories.size() != 0) {
                        for (int i = 0; i < detailsCategories.size(); i++) {
                            category.append(detailsCategories.get(i).getTitle());
                            if ((i + 1) != detailsCategories.size()) {
                                category.append(", ");
                            }
                        }
                    } else {
                        category.setText("-");
                    }

                if(details.getLocation().getAccess()!=null) {
                    ArrayList<Access> accesses = details.getLocation().getAccess();
                    if (accesses.size() != 0) {
                        for (int i = 0; i < accesses.size(); i++) {
                            accessibleBy.append(accesses.get(i).getAccessType());
                            if ((i + 1) != accesses.size()) {
                                accessibleBy.append(", ");
                            }
                        }
                    } else {
                        accessibleBy.setText("-");
                    }}else {

                        accessibleBy.setText("-");
                    }


                    if (distanceReceived != 0) {
                        if (distanceReceived < 1) {
                            distance.setText("" + decimalFormat.format(distanceReceived*1000.0*1.4));
                            distance.append("m");
                        } else {
                            distance.setText("" + decimalFormat.format(distanceReceived*1.4));
                            distance.append("km");
                        }
                    } else {
                        distance.setText("Not available");
                    }

                    navigate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentNav = new Intent(DetailedActivity.this,NavigateActivity.class);
                            intentNav.putExtra("NAV_LATITUDE",latitude);
                            intentNav.putExtra("NAV_LONGITUDE",longitude);
                            intentNav.putExtra("TITLE",text.getText().toString());
                            intentNav.putExtra("NAV_DISTANCE",distanceReceived*1.4);
                            startActivity(intentNav);
                        }
                    });

                }
            });

        }
    });
}catch (Exception e){
    Toast.makeText(DetailedActivity.this, "Error loading! Please try again!", Toast.LENGTH_LONG).show();
}

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==123){
            Log.e("DetailedActivityTag", "onClick: reqpermission" );

            if(permissions[0].equals(Manifest.permission.CALL_PHONE)&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("tel:"+phone));
                Log.e("DetailedActivityTag", "onClick: permissiongranted" );
                startActivity(intent1);
            }else if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                Toast.makeText(DetailedActivity.this, "Call permission has been denied!", Toast.LENGTH_SHORT).show();;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
