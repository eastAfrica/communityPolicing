/*
On my honor, as a Carnegie-Mellon Rwanda student, I have neither given nor received unauthorized assistance on this work.

 */
package com.example.nyismaw.communitypolicing.controller.maps;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nyismaw.communitypolicing.controller.filter.FetchedIssues;
import com.example.nyismaw.communitypolicing.controller.filter.FilterIssues;
import com.example.nyismaw.communitypolicing.firebaseApi.CreateEntities;
import com.example.nyismaw.communitypolicing.firebaseApi.DownloadFile;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.model.CurrentLocation;
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


    MainTabActivity mainTabActivity;

    public AppLocationListener(MainTabActivity mainTabActivity) {
        this.mainTabActivity = mainTabActivity;


    }

    Dialog dialog;

    @Override
    public void onLocationChanged(Location location) {
        new CurrentLocation().setLocation(location);
        //   Log.e(" after that *****", "**********************    "+location.getLatitude()+" , "+location.getLongitude());

        MapFragment.mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (!marker.getTitle().equals("My Location")) {
                    marker.hideInfoWindow();
                    Log.e(" marker clicked  *****", "**********************    " + marker.getTitle() + "");
                    dialog = new Dialog(mainTabActivity);
                    dialog.setContentView(R.layout.popup);
                    dialog.setTitle("Hello");


                    TextView textViewUser = dialog.findViewById(R.id.description);
                    Issues issues = new FilterIssues().filterIssueById(marker.getTitle());
                    new DownloadFile(AppLocationListener.this).downLoadImage(issues.getId());
                    textViewUser.setText(issues.getDetails());
                    //  dialog.show(issues.getId());
                    return true;
                }
                return false;
            }
        });

        CreateEntities entities = new CreateEntities();
        entities.getObject();

        for (int i = 0; i < MapFragment.markers.size(); i++) {
            MapFragment.markers.get(i).remove();
            MapFragment.markers.remove(i);
        }
      //  Log.e(" size that *****", " after removing size is ------------    " + +MapFragment.markers.size());

        if (MapFragment.mMap != null) {
            Marker marker = MapFragment.mMap.addMarker(new MarkerOptions().position(new
                    LatLng(location.getLatitude(), location.getLongitude())).title("My Location"));

            MapFragment.markers.add(marker);
            List<Issues> issues = FetchedIssues.getIssues();
            if (issues != null) {
                for (Object obj : issues) {
                    Issues issue = (Issues) obj;

                    if (issue.getLocation().getLatitude() != null || issue.getLocation().getLongtude() != null) {

                        LatLng location1 = new LatLng(issue.getLocation()
                                .getLatitude(), issue.getLocation().getLongtude());

                        Marker marker2 = MapFragment.mMap.addMarker(new MarkerOptions().position(location1));
                        //marker2.setSnippet();
                        marker2.setTitle(issue.getId());
                        // marker2.hideInfoWindow();
                        MapFragment.markers.add(marker2);

                    }


                }
//                Log.e(" after that *****", "**********************    " + MapFragment.markers.size()
//                        + " , " + issues.size());

            }
        }


    }

    public void show(byte[] bytes, String imageName) {
        Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ImageView myImage = (ImageView) dialog.findViewById(R.id.imageViewForMap);

        DisplayMetrics metrics = mainTabActivity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((6 * width)/8, (2 * height)/6);

        myImage.setLayoutParams(layoutParams);
        myImage.setImageBitmap(myBitmap);

     //   Log.e("diaglog", "showing dialog here 888888888888888888888888888888888888");
        dialog.getWindow().setLayout((6 * width)/7, (4 * height)/5);
        dialog.show();
    }

}
