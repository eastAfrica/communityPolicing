package com.example.nyismaw.communitypolicing.screens;

import android.app.Dialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.model.Enums.Category;
import com.example.nyismaw.communitypolicing.model.Enums.Severity;
import com.example.nyismaw.communitypolicing.model.Enums.VehicleType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jarigye on 12/4/2017.
 */

public class MoreDetailsDialog implements AdapterView.OnItemSelectedListener {
    ReportingTab reportingTab;

    final Dialog dialog;
    final Spinner spinner_type;
    public MoreDetailsDialog(ReportingTab reportingTab, final Dialog dialog) {
        this.reportingTab = reportingTab;
        this.dialog = dialog;


        Spinner spinner_category = (Spinner) dialog.findViewById(R.id.spinner_category);
        //use enumerations
        ArrayList<String> category = new ArrayList<String>();
        category.add(0, "None");
        category.add(1, Category.ACCIDENTS.toString());
        category.add(2, Category.POTHOLES.toString());
        category.add(3, Category.BLOCKED_ROADS.toString());
        category.add(4, Category.FALLEN_TREES.toString());


         // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(reportingTab.getContext(), R.layout.support_simple_spinner_dropdown_item, category);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_category.setAdapter(dataAdapter);

        if (!category.isEmpty()) {
          //  spinner_category.setSelection(0);
            spinner_category.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        }

        Spinner spinner_severity = (Spinner) dialog.findViewById(R.id.spinner_severity);
        //
        ArrayList<String> severity = new ArrayList<String>();
        severity.add(0, "None");
        severity.add(1, Severity.CRITICAL.toString());
        severity.add(2, Severity.HIGH.toString());
        severity.add(3, Severity.MEDIUM.toString());
        severity.add(4, Severity.LOW.toString());
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(reportingTab.getContext(), R.layout.support_simple_spinner_dropdown_item, severity);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_severity.setAdapter(dataAdapter1);

        if (!severity.isEmpty()) {
          //  spinner_severity.setSelection();
            spinner_severity.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        }
        spinner_type = (Spinner) dialog.findViewById(R.id.spinner_vt);
        //
        ArrayList<String> vt = new ArrayList<String>();
        vt.add(0, "None");
        vt.add(1, VehicleType.SEDAN.toString());
        vt.add(2, VehicleType.BICYCLE.toString());
        vt.add(3, VehicleType.FAMILYVAN.toString());
        vt.add(4, VehicleType.PICKUP.toString());
        vt.add(5, VehicleType.FOUR_WHEEL_TRUCK.toString());
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(reportingTab.getContext(), R.layout.support_simple_spinner_dropdown_item, vt);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_type.setAdapter(dataAdapter2);

        if (!vt.isEmpty()) {
           // spinner_type.setSelection(0);
            spinner_type.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        }
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String type = spinner_type.getSelectedItem().toString();
                TextView description3 = (TextView) dialog.findViewById(R.id.description3);
                description3.setTextColor(Color.RED);
                description3.append(type+"\n");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
