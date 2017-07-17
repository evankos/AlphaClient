package com.startapp.vaggelis.alphaclient.listeners;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Vaggelis on 6/29/2017.
 */

public class MyLocationListener implements LocationListener {
    private double latitude = 51.8125626;
    private double longitude = 5.8372264; //TODO make private and synchronize access with getters
    private Activity contextActivity;

    public synchronized double getLong(){
        return longitude;
    }

    public synchronized double getLat(){
        return latitude;
    }

    public MyLocationListener(LocationManager locationManager, Activity context){
        contextActivity = context;
        if (ContextCompat.checkSelfPermission(contextActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
//            Toast.makeText(contextActivity, "Need permission.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(contextActivity, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION}, 102
            );
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude =  (location.getLatitude());
        longitude =  (location.getLongitude());
        Log.d(toString(),"UPDATED LOCATION");
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
}