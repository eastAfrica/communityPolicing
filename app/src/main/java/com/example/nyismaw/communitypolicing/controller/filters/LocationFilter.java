package com.example.nyismaw.communitypolicing.controller.filters;

import android.location.Location;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentLocation;
import com.example.nyismaw.communitypolicing.AppInfo.CurrentUserPreferences;
import com.example.nyismaw.communitypolicing.controller.location.LocationAnalysis;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.model.MyLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 12/9/2017.
 */

public class LocationFilter implements FilterPipeInterface {

    private FilterPipeInterface nextFilter;

    public LocationFilter(FilterPipeInterface nextFilter) {
        this.nextFilter = nextFilter;
    }
    public LocationFilter() {

    }

    @Override
    public List<Issues> filter(List<Issues> issues) {
        double distanceThreshold = CurrentUserPreferences.getNotificationDistance();
        List<Issues> issues1 = FetchedIssues.getIssues();
        List<Issues> filteredIssues = new ArrayList();
        for (Issues currentIssues : issues1) {
            MyLocation myLocation1 = currentIssues.getLocation();
            Location issueLocation = new Location("Location");
            issueLocation.setLatitude(myLocation1.getLatitude());
            issueLocation.setLongitude(myLocation1.getLongtude());
            if (LocationAnalysis.getDistance(CurrentLocation.location, issueLocation) < distanceThreshold) {
                filteredIssues.add(currentIssues);
            }

        }
        if (nextFilter == null)
            return filteredIssues;
        return nextFilter.filter(filteredIssues);
    }

    @Override
    public void setNextPipe(FilterPipeInterface filterPipeInterface) {
        this.nextFilter = filterPipeInterface;
    }
}
