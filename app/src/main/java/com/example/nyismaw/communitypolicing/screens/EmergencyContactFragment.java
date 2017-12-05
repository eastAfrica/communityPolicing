package com.example.nyismaw.communitypolicing.screens;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nyismaw.communitypolicing.ApiWrapper.FireBaseAPI;
import com.example.nyismaw.communitypolicing.ApiWrapper.ReprotedIssuesInterface;
import com.example.nyismaw.communitypolicing.Manifest;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.controller.AudioConfig;

/**
 * Created by nyismaw on 12/5/2017.
 */

public class EmergencyContactFragment extends Fragment {

    static Button call911;
    static Button calltraf;
    static Button callfire;

    public static final int RequestPermissionCode = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.emergencycontacts, container, false);
        call911 = v.findViewById(R.id.call911);
       final  Intent callIntent = new Intent(Intent.ACTION_DIAL);
        call911.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vw) {
                callIntent.setData(Uri.parse("tel:999"));

                if (checkPermission()) {
                    startActivity(callIntent);
                }
                else{
                    requestPermission();
                }

            }
        });
        calltraf = v.findViewById(R.id.calltraf);

        calltraf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vw) {
               callIntent.setData(Uri.parse("tel:113"));

                if (checkPermission()) {
                    startActivity(callIntent);
                }
                else{
                    requestPermission();
                }

            }
        });
        callfire = v.findViewById(R.id.callfire);

        callfire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vw) {
                callIntent.setData(Uri.parse("tel:111"));

                if (checkPermission()) {
                    startActivity(callIntent);
                }
                else{
                    requestPermission();
                }

            }
        });
       return v;

    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.CALL_PHONE);
         return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{ android.Manifest.permission.CALL_PHONE}, this.RequestPermissionCode);
    }

}
