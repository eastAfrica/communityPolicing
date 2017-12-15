package com.example.nyismaw.communitypolicing.controller.notification;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.controller.filters.AccidentFilter;
import com.example.nyismaw.communitypolicing.controller.filters.BlockedRoadsFilter;
import com.example.nyismaw.communitypolicing.controller.filters.FIlterbyUserId;
import com.example.nyismaw.communitypolicing.controller.filters.FallenTressFilter;
import com.example.nyismaw.communitypolicing.controller.filters.FetchedIssues;
import com.example.nyismaw.communitypolicing.controller.filters.FilterChainInterface;
import com.example.nyismaw.communitypolicing.controller.filters.FilterThread;
import com.example.nyismaw.communitypolicing.controller.filters.LocationFilter;
import com.example.nyismaw.communitypolicing.controller.filters.OtherIssuesFilter;
import com.example.nyismaw.communitypolicing.controller.filters.PotHoleFilter;
import com.example.nyismaw.communitypolicing.controller.filters.UnresolvedIssuesFilter;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.model.MyLocation;
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

    public static Issues issuesToBeRemoved;
    public NotificationService() {
        super("LocationService");
    }

    public static double issueNotificationDistance = 100;
    List<String> notifiedIssues = new ArrayList();

    private FilterThread filterThread;

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        Timer mBackGroundTimer = new Timer();
        mBackGroundTimer.schedule(new TimerTask() {
            public void run() {
                try {

                    FilterChainInterface filterChainInterface =

                                    new UnresolvedIssuesFilter(new LocationFilter(
                                            new AccidentFilter(
                                                    new BlockedRoadsFilter(
                                                            new FallenTressFilter(
                                                                    new OtherIssuesFilter(
                                                                            new PotHoleFilter()
                                                                    ))
                                                    ))), true);
                    List<Issues> issues = filterChainInterface.filter(FetchedIssues.getIssues());





                    Log.e("Id filter"," ///////////////////////////,"
                         +issues);

                    for (Issues iss : issues) {
                        String issueUID="";
                        if(iss.getUserid()!=null){
                            issueUID= iss.getUserid().getId();
                        }
                        String currentid="";
                        if(CurrentUser.user!=null)
                        currentid =CurrentUser.user.getId();
                        boolean checker= issueUID.equals(currentid);

                        MyLocation myLocation1 = iss.getLocation();
                        Location issueLocation = new Location("Location");
                        issueLocation.setLatitude(myLocation1.getLatitude());
                        if(checker)
                            continue;
                        issueLocation.setLongitude(myLocation1.getLongtude());

                        if (!iss.isNotificationIsSent() ) {
                            NotificationInterface notificationInterface = new PushNotifications(getApplicationContext());
                            notificationInterface.sendNotification("Issue has been reported in your area", iss.getDetails());
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("issues");
                            if (myRef != null)
                                myRef.child(iss.getId()).child("notificationIsSent").setValue(true);
                            iss.setNotificationIsSent(true);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 1000, 1000);

    }
}
