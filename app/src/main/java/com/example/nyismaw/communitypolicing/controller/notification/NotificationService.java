package com.example.nyismaw.communitypolicing.controller.notification;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentLocation;
import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.controller.filters.FetchedIssues;
import com.example.nyismaw.communitypolicing.controller.location.LocationAnalysis;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.model.MyLocation;
import com.example.nyismaw.communitypolicing.screens.MainTabActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nyismaw on 11/28/2017.
 */

public class NotificationService extends IntentService {
    Context context;

    public NotificationService(String name) {
        super(name);
    }

    public NotificationService() {
        super("LocationService");
    }

    public static double issueNotificationDistance = 100;
    List<String> notifiedIssues = new ArrayList();


    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        Timer mBackGroundTimer = new Timer();
        mBackGroundTimer.schedule(new TimerTask() {
            public void run() {
                try {
                    Location currentLocation = new CurrentLocation().getLocation();
                    List<Issues> issues = FetchedIssues.getUnResolvedIssues();
                    for (Issues iss : issues) {

                        MyLocation myLocation1 = iss.getLocation();
                        Location issueLocation = new Location("Location");
                        issueLocation.setLatitude(myLocation1.getLatitude());
                        issueLocation.setLongitude(myLocation1.getLongtude());
                        double distance = LocationAnalysis.getDistance(currentLocation, issueLocation);
                        if (distance < issueNotificationDistance) {
                            if (!iss.isNotificationIsSent() & !(CurrentUser.user.getId().equals(iss.getUserid().getId()))) {
                                NotificationInterface notificationInterface = new PushNotifications(getApplicationContext());
                                notificationInterface.sendNotification("Issue has been reported in your area", iss.getDetails());
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("issues");
                                if (myRef != null)
                                    myRef.child(iss.getId()).child("notificationIsSent").setValue(true);
                            }

                        }
//                        Log.e("Service", "Distance from issues " +
//                                LocationAnalysis.getDistance(currentLocation, issueLocation));

                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 1000, 1000);

    }
}
