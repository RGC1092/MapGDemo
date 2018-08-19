package com.example.quagnitia.mapgdemo;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,LocationListener {
    //OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener
    GoogleMap googleMap;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Fragment from xml file and call getmapasynch for getting map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // After getting Map onMapReady will call
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // You can customize map here
        this.googleMap = googleMap;
 /*      // set marker at kondhwa location with google markers icon
        LatLng kondhwa=new LatLng(18.4771,73.8907);
        googleMap.addMarker(new MarkerOptions().position(kondhwa).title("KONDHWA").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        //set marker at Warje location with custom marker icon
        LatLng warje=new LatLng(18.4865,73.7968);
        googleMap.addMarker(new MarkerOptions().position(warje).title("Warje").icon(BitmapDescriptorFactory.fromResource(R.drawable.user)));


        // set camera on particular location and zooming on map
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
               kondhwa).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
*/
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        //Set CallBack listeners and Connect to GoogleApiClient
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //Build Location Request
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // Send Loacation request for location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.



            return;
        }
        else {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        double lat=location.getLatitude();
        double lng=location.getLongitude();
        LatLng currentLocation=new LatLng(lat,lng);

        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }
}
