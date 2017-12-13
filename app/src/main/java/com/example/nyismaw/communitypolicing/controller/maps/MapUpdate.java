package com.example.nyismaw.communitypolicing.controller.maps;

import android.app.Dialog;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;

import com.example.nyismaw.communitypolicing.ApiWrapper.DownloadFileInterface;
import com.example.nyismaw.communitypolicing.ApiWrapper.FireBaseAPI;
import com.example.nyismaw.communitypolicing.ApiWrapper.ReprotedIssuesInterface;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.controller.filters.AccidentFilter;
import com.example.nyismaw.communitypolicing.controller.filters.BlockedRoadsFilter;
import com.example.nyismaw.communitypolicing.controller.filters.FallenTressFilter;
import com.example.nyismaw.communitypolicing.controller.filters.FetchedIssues;
import com.example.nyismaw.communitypolicing.controller.filters.FilterChainInterface;
import com.example.nyismaw.communitypolicing.controller.filters.LocationFilter;
import com.example.nyismaw.communitypolicing.controller.filters.OtherIssuesFilter;
import com.example.nyismaw.communitypolicing.controller.filters.PotHoleFilter;
import com.example.nyismaw.communitypolicing.controller.location.AppLocationListener;
import com.example.nyismaw.communitypolicing.model.Accident;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.model.User;
import com.example.nyismaw.communitypolicing.screens.MainTabActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by nyismaw on 12/13/2017.
 */

public class MapUpdate {

    private MainTabActivity mainTabActivity;

    private static String currentIssueId;
    private Location location;

    public MapUpdate(MainTabActivity mainTabActivity, Location location) {
        this.location = location;
        this.mainTabActivity = mainTabActivity;


    }

    public MapUpdate() {
        this.mainTabActivity = mainTabActivity;


    }

    Dialog dialog;

    public void updateMapBasedOnLocation() {


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
                        ;
                        // TextView textViewUser = dialog.findViewById(R.id.description);
                        Issues issues = FetchedIssues.getIssueById(marker.getTitle());
                        setItemsToDialog(dialog, issues);
                        if (issues == null)
                            return false;
                        DownloadFileInterface downloadFileInterface = new FireBaseAPI(MapUpdate.this);
                        downloadFileInterface.downLoadImage(issues.getId());
                        downloadFileInterface.downLoadAudio(issues.getId());
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
        if (MapFragment.markers.size() > 0) {
            MapFragment.getMarkers().get(0).remove();
            MapFragment.getMarkers().remove(0);
        }
        if (MapFragment.getmMap() != null) {
            Marker marker = MapFragment.getmMap().addMarker(new MarkerOptions().position(new
                    LatLng(location.getLatitude(), location.getLongitude())).title("My Location"));

            MapFragment.getMarkers().add(marker);


            FilterChainInterface filterChainInterface =
                    new LocationFilter(
                            new AccidentFilter(
                                    new BlockedRoadsFilter(
                                            new FallenTressFilter(
                                                    new OtherIssuesFilter(
                                                            new PotHoleFilter()
                                                    ))
                                    )));




            List<Issues> issues = filterChainInterface.filter(FetchedIssues.getIssues());

            Log.e("Size after editing", " size of issues is /////////////////  "+FetchedIssues.getIssues()+" , " + issues.size());
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

