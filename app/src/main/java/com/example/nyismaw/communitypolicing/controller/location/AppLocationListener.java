/*
On my honor, as a Carnegie-Mellon Rwanda student, I have neither given nor received unauthorized assistance on this work.

 */
package com.example.nyismaw.communitypolicing.controller.location;


import android.app.Dialog;
import android.location.Location;
import android.widget.TextView;

import com.example.nyismaw.communitypolicing.controller.maps.*;
import com.example.nyismaw.communitypolicing.ApiWrapper.FireBaseAPI;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.ApiWrapper.ReprotedIssuesInterface;
import com.example.nyismaw.communitypolicing.AppInfo.CurrentLocation;
import com.example.nyismaw.communitypolicing.model.Accident;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.model.User;
import com.example.nyismaw.communitypolicing.screens.MainTabActivity;
import com.google.android.gms.location.LocationListener;

/**
 * Created by nyismaw on 11/7/2017.
 */

public class AppLocationListener implements LocationListener {


    private MainTabActivity mainTabActivity;

    public AppLocationListener(MainTabActivity mainTabActivity) {
        this.mainTabActivity = mainTabActivity;


    }

    public AppLocationListener() {
        this.mainTabActivity = mainTabActivity;


    }

    Dialog dialog;

    public static String currentIssueId;

    public void removeLocationUpdates() {

    }

    @Override
    public void onLocationChanged(Location location) {
        ReprotedIssuesInterface reprotedIssuesInterface = new FireBaseAPI();
        reprotedIssuesInterface.getReportedIssues();
        new CurrentLocation().setLocation(location);
        new MapUpdate(mainTabActivity,location).updateMapBasedOnLocation();


    }

    public void show(byte[] bytes, final byte[] audio, String imageName) {
        new MapDialog().showDialog(mainTabActivity, bytes, audio, dialog);
    }

    public MainTabActivity getMainTabActivity() {
        return mainTabActivity;
    }

    public void setMainTabActivity(MainTabActivity mainTabActivity) {
        this.mainTabActivity = mainTabActivity;
    }

    private void setItemsToDialog(Dialog dialog, Issues issues) {

        TextView description = dialog.findViewById(R.id.description);
        description.setText(issues.getDetails());

        TextView category = dialog.findViewById(R.id.category);
        if (category != null) {
            category.setText(issues.getCategoryOfIssues());
        }
        Accident accident = issues.getAccident();
        if (accident != null) {
            TextView severity = dialog.findViewById(R.id.severity);
            if (severity != null) {
                severity.setText(accident.getSeverity());
            }

            TextView vechiles = dialog.findViewById(R.id.vehiclInvovled);
            if (vechiles != null & accident.getVehicles() != null) {
                vechiles.setText(accident.getVehicles().toString());
            }

        } else {
            TextView vechiles = dialog.findViewById(R.id.vehiclInvovled);


        }

        User user = issues.getUserid();
        if (user != null) {
            TextView reportedBy = dialog.findViewById(R.id.reportedBy);
            if (reportedBy != null) {
                reportedBy.setText(user.getUsername());
            }
        }


    }
}
