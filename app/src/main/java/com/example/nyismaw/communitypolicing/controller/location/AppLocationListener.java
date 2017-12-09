/*
On my honor, as a Carnegie-Mellon Rwanda student, I have neither given nor received unauthorized assistance on this work.

 */
package com.example.nyismaw.communitypolicing.controller.location;


import android.app.Dialog;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;

import com.example.nyismaw.communitypolicing.controller.filters.AccidentFilter;
import com.example.nyismaw.communitypolicing.controller.filters.BlockedRoadsFilter;
import com.example.nyismaw.communitypolicing.controller.filters.FallenTressFilter;
import com.example.nyismaw.communitypolicing.controller.filters.FetchedIssues;
import com.example.nyismaw.communitypolicing.controller.filters.FilterIssues;
import com.example.nyismaw.communitypolicing.controller.filters.FilterPipeInterface;
import com.example.nyismaw.communitypolicing.controller.filters.LocationFilter;
import com.example.nyismaw.communitypolicing.controller.filters.OtherIssuesFilter;
import com.example.nyismaw.communitypolicing.controller.filters.PotHoleFilter;
import com.example.nyismaw.communitypolicing.controller.maps.*;
import com.example.nyismaw.communitypolicing.ApiWrapper.DownloadFileInterface;
import com.example.nyismaw.communitypolicing.ApiWrapper.FireBaseAPI;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.ApiWrapper.ReprotedIssuesInterface;
import com.example.nyismaw.communitypolicing.AppInfo.CurrentLocation;
import com.example.nyismaw.communitypolicing.controller.maps.MapFragment;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.screens.MainTabActivity;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by nyismaw on 11/7/2017.
 */

public class AppLocationListener implements LocationListener {


    private MainTabActivity mainTabActivity;

    public AppLocationListener(MainTabActivity mainTabActivity) {
        this.mainTabActivity = mainTabActivity;


    }

    Dialog dialog;

    public static String currentIssueId;

    @Override
    public void onLocationChanged(Location location) {
        ReprotedIssuesInterface reprotedIssuesInterface = new FireBaseAPI();
        reprotedIssuesInterface.getReportedIssues();
        new CurrentLocation().setLocation(location);
        //   Log.e(" after that *****", "**********************    "+location.getLatitude()+" , "+location.getLongitude());
        if (MapFragment.getmMap() != null) {


            MapFragment.getmMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (!marker.getTitle().equals("My Location")) {
                        marker.hideInfoWindow();
                        Log.e(" marker clicked  *****", "**********************    " + marker.getTitle() + "");
                        dialog = new Dialog(mainTabActivity);
                        dialog.setContentView(R.layout.popup);
                        dialog.setTitle(marker.getTitle());
                        currentIssueId = marker.getTitle();

                        TextView textViewUser = dialog.findViewById(R.id.description);
                        Issues issues = FetchedIssues.getIssueById(marker.getTitle());
                        if (issues == null)
                            return false;
                        DownloadFileInterface downloadFileInterface = new FireBaseAPI(AppLocationListener.this);
                        downloadFileInterface.downLoadImage(issues.getId());
                        downloadFileInterface.downLoadAudio(issues.getId());
                        textViewUser.setText(issues.getDetails());
                        //  dialog.show(issues.getId());
                        return true;
                    }
                    return false;
                }
            });
        }
        ReprotedIssuesInterface entities = new FireBaseAPI();
        entities.getReportedIssues();

        for (int i = 0; i < MapFragment.getMarkers().size(); i++) {
            MapFragment.getMarkers().get(i).remove();
            MapFragment.getMarkers().remove(i);
        }
        //  Log.e(" size that *****", " after removing size is ------------    " + +MapFragment.markers.size());

        if (MapFragment.getmMap() != null) {
            Marker marker = MapFragment.getmMap().addMarker(new MarkerOptions().position(new
                    LatLng(location.getLatitude(), location.getLongitude())).title("My Location"));

            MapFragment.getMarkers().add(marker);



            FilterPipeInterface filterPipeInterface =
                    new LocationFilter(
                            new AccidentFilter(
                                    new BlockedRoadsFilter(
                                            new FallenTressFilter(
                                                    new OtherIssuesFilter(
                                                            new PotHoleFilter()
                                                    ))
                                    )));


            List<Issues> issues = filterPipeInterface.filter(FetchedIssues.getIssues());
            if (issues != null) {
                for (Object obj : issues) {
                    Issues issue = (Issues) obj;

                    if (issue.getLocation().getLatitude() != null || issue.getLocation().getLongtude() != null) {

                        LatLng location1 = new LatLng(issue.getLocation()
                                .getLatitude(), issue.getLocation().getLongtude());

                        Marker marker2 = MapFragment.getmMap().addMarker(new MarkerOptions().position(location1));
                        //marker2.setSnippet();
                        marker2.setTitle(issue.getId());
                        // marker2.hideInfoWindow();
                        MapFragment.getMarkers().add(marker2);

                    }


                }

            }
        }


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
}
