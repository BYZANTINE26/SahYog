package com.example.sahyog;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Location locationSet;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng latLong;
    String addressLine;
    String addressLine2beStored;
    Intent intent2main;
    Intent intent2RecyclerView;
    LatLng latLngToBeStored;
    ParseObject provider;
    Intent FormCallbackIntent;
    Button buttonAddPos;
    String pro_username,pro_service, pro_peraddress, pro_curloc;
    String pro_range,pro_maxweight;
    Intent mapActivityIntent;
    String Service_Date;
    String Service_Time;
    String phoneNumber;



    public void AddPosition(View view){

        Log.i("locoInfo :" , String.valueOf(locationSet));
// Toast.makeText(MapsActivity.this, pro_service + pro_range + pro_maxweight + pro_username , Toast.LENGTH_SHORT).show();
Log.i("parseINfo " , pro_service + pro_range + pro_maxweight + pro_username);



        provider.put("username",pro_username);
        provider.put("service",pro_service);
       provider.put("MaximumWeight", 9 );
        provider.put("Time",Service_Time);
        provider.put("Date",Service_Date);
        provider.put("ConfirmStatus",0);
        provider.put("phone",phoneNumber);
       provider.put("LocationLONG", latLngToBeStored.longitude );
        provider.put("LocationLAT", latLngToBeStored.latitude );


        provider.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e==null ){
                    Toast.makeText(MapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    Log.i("Submit ","Successful");
                }else{
                    Log.i("Submit ","unSuccessful :" + e.getMessage());
                }
            }
        });

        Log.i("parseLOcInfo" , String.valueOf(latLong) );


        startActivity(intent2RecyclerView);

    }



    public void ToastMaker(String string){
        Toast.makeText(MapsActivity.this, string , Toast.LENGTH_SHORT).show();
    }




    public void UpdateLocationChangeInfo(Location location) {
            buttonAddPos.setVisibility(View.VISIBLE);

            latLong = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLong));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position((latLng)));
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                        addressLine2beStored = addressList.get(0).getAddressLine(0);
                        ToastMaker(addressLine2beStored);
                        latLngToBeStored = latLng;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        Log.i("info", location.toString());
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            addressLine = addressList.get(0).getAddressLine(0);
            ToastMaker(addressLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, locationListener);

            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapActivityIntent = getIntent();

        buttonAddPos = findViewById(R.id.buttonAddPosition);

        provider= new ParseObject("ServiceProvider");


        pro_service = mapActivityIntent.getStringExtra("pro_service");
       // pro_range = mapActivityIntent.getStringExtra("pro_range");
        pro_maxweight = mapActivityIntent.getStringExtra("pro_maxweight");
        pro_username= String.valueOf(ParseUser.getCurrentUser().getUsername());
        Service_Date = mapActivityIntent.getStringExtra("Date");
        Service_Time = mapActivityIntent.getStringExtra("Time");
        phoneNumber = mapActivityIntent.getStringExtra("phoneNumber");

        FormCallbackIntent = new Intent(getApplicationContext(),ProvideService.class);



        intent2main = new Intent(getApplicationContext(),MainActivity.class);
        intent2RecyclerView = new Intent(getApplicationContext(),MainActNavDrawer.class);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                UpdateLocationChangeInfo(location);
                Log.i("info :", "location");
                locationSet = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


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

        // Add a marker in Sydney and move the camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 0 , 1000 ,locationListener );
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng mylocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).position(mylocation).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15 ));
            Toast.makeText(MapsActivity.this, "Updating Location........", Toast.LENGTH_LONG).show();

        }

    }



}
