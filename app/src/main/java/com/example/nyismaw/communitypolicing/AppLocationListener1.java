package com.example.nyismaw.communitypolicing;

/**
 * Created by nyismaw on 11/25/2017.
 */


import android.location.Location;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

import Model.CurrentLocation;

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
