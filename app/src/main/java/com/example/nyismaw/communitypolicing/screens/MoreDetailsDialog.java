package com.example.nyismaw.communitypolicing.screens;

import android.app.Dialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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
import java.util.StringTokenizer;

/**
 * Created by jarigye on 12/4/2017.
 */

public class MoreDetailsDialog {
    ReportingTab reportingTab;

    final Dialog dialog;
    MySpinner spinner_type;
    static ArrayList<String> category;
    static ArrayList<String> severity;
    static ArrayList<String> vt;

    public MoreDetailsDialog(ReportingTab reportingTab, final Dialog dialog) {
        this.reportingTab = reportingTab;
        this.dialog = dialog;
        MoreDetailsDialog.category = new ArrayList<String>();
        category.add(Category.ACCIDENTS.toString());
        category.add(Category.POTHOLES.toString());
        category.add(Category.BLOCKED_ROADS.toString());
        category.add(Category.FALLEN_TREES.toString());
        category.add(Category.OTHER.toString());

        severity = new ArrayList<String>();
        severity.add(Severity.CRITICAL.toString());
        severity.add(Severity.HIGH.toString());
        severity.add(Severity.MEDIUM.toString());
        severity.add(Severity.LOW.toString());

        vt = new ArrayList<String>();
        vt.add("");
        vt.add(VehicleType.SEDAN.toString());
        vt.add(VehicleType.BICYCLE.toString());
        vt.add(VehicleType.FAMILYVAN.toString());
        vt.add(VehicleType.PICKUP.toString());
        vt.add(VehicleType.FOUR_WHEEL_TRUCK.toString());


        configureDialog();

    }

    static String categoryType;
    static List<String> vehiclesInvolved = new ArrayList();
    static String severityOfIssue;

    public void configureDialog() {

        final Spinner spinner_category = (Spinner) dialog.findViewById(R.id.spinner_category);
        final Spinner spinner_severity = (Spinner) dialog.findViewById(R.id.spinner_severity);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(reportingTab.getContext(),
                R.layout.support_simple_spinner_dropdown_item, category);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_category.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(reportingTab.getContext(),
                R.layout.support_simple_spinner_dropdown_item, severity);
        dataAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_severity.setAdapter(dataAdapter1);

        if (categoryType != null) {
            Log.e("not null", "Category type is not null " + categoryType);
            spinner_category.setSelection(category.indexOf(categoryType));
        }
        if (severityOfIssue != null) {
            spinner_severity.setSelection(severity.indexOf(severityOfIssue));
        }
        if (vehiclesInvolved != null) {
            vehicleListset();
        }
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryType = (String) spinner_category.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_severity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                severityOfIssue = (String) spinner_severity.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_type = (MySpinner) dialog.findViewById(R.id.spinner_vt);
        //

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(reportingTab.getContext(),
                R.layout.support_simple_spinner_dropdown_item, vt);
        dataAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_type.setAdapter(dataAdapter2);

        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                final String type = spinner_type.getSelectedItem().toString();
                if (!type.isEmpty()) {
                    add(type);
                    vehicleListset();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    private void vehicleListset() {
        final GridLayout gridLayout = (GridLayout) dialog.findViewById(R.id.description3);
        gridLayout.removeAllViews();
        for (final String type : vehiclesInvolved) {

            TextView description3 = new TextView(reportingTab.getContext());
            description3.setTextColor(Color.RED);
            description3.setText(type);

            final Button button = new Button(reportingTab.getContext());
            button.setWidth(8);
            button.setHeight(8);
            button.setId(vehiclesInvolved.indexOf(type));
            description3.setId(vehiclesInvolved.indexOf(type));
            button.setBackgroundResource(R.drawable.delete);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vehiclesInvolved.remove(type);
                    gridLayout.removeView(dialog.findViewById(button.getId()));
                    gridLayout.removeView(dialog.findViewById(button.getId()));
                }
            });
            gridLayout.addView(description3);
            gridLayout.addView(button);
        }


    }

    private int getCountofElements(String elemnt) {
        int count = 0;

        for (String string : vehiclesInvolved) {
            if (string.contains(elemnt))
                count=1;
        }
        return count;

    }

    private void add(String type) {

        if (getCountofElements(type) == 0) {
            vehiclesInvolved.add(type + "*" + (1));

            return;
        }

        String v1 = "";
        String v2 = "";
        for (String string : vehiclesInvolved) {
            Log.e("Am i created by", string + "," + type);

            StringTokenizer stringTokenizer = new StringTokenizer(string, "*");
            String name = (String) stringTokenizer.nextElement();
            String num = (String) stringTokenizer.nextElement();
            int count = Integer.parseInt(num);
            if (name.contains(type)) {
                v1 = string;
                v2 = type + "*" + (count + 1);
                continue;
            }


        }
        if (v1 != null)
            vehiclesInvolved.remove(v1);
        vehiclesInvolved.add(v2);


    }

}
