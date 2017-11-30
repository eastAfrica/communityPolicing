package com.example.nyismaw.communitypolicing.model;

import android.location.Location;

/**
 * Created by nyismaw on 11/25/2017.
 */

public class CurrentLocation {
    public static Location location;

    public  Location getLocation() {
        return location;
    }
    public CurrentLocation(){}
    public  void setLocation(Location location) {
        CurrentLocation.location = location;
    }
}
