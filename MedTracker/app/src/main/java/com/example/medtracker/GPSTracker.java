package com.example.medtracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class GPSTracker implements LocationListener {

    Context context;
    public GPSTracker(Context c){
        context = c;
    }

    public Location getLocation(){

        try {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
                Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return l;
            } else {
                Toast.makeText(context, "Please enable GPS", Toast.LENGTH_LONG).show();
            }
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

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
