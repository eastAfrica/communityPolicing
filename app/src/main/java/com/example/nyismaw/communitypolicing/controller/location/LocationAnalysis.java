package com.example.nyismaw.communitypolicing.controller.location;

import android.location.Location;

/**
 * Created by nyismaw on 12/2/2017.
 */

public class LocationAnalysis {
    public static double getDistance(Location startLocation, Location finalLocation) {
        return startLocation.distanceTo(finalLocation);
    }
}
