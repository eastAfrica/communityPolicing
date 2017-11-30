package com.example.nyismaw.communitypolicing.controller.location;

/**
 * Created by nyismaw on 11/25/2017.
 */


import android.location.Location;

import com.google.android.gms.location.LocationListener;

import com.example.nyismaw.communitypolicing.model.CurrentLocation;

/**
 * Created by nyismaw on 11/7/2017.
 */

public class AppLocationListener1 implements LocationListener {

    public AppLocationListener1() {
    }

    @Override
    public void onLocationChanged(Location location) {
     //   Log.e("make use of*****", "inside location changed");
        new CurrentLocation().setLocation(location);

    }

}
