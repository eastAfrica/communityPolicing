package com.example.nyismaw.communitypolicing.screens;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nyismaw.communitypolicing.ApiWrapper.FireBaseAPI;
import com.example.nyismaw.communitypolicing.ApiWrapper.ReprotedIssuesInterface;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.controller.AudioConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jarigye on 11/25/2017.
 */

public class ReportingTab extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    static Button buttonStop;
    static Button playButton;
    static Button recordButton;
    static Button moredetails;
    private ImageView imageView;

    public static final int RequestPermissionCode = 1;
    View v;
    AudioConfig audioConfig;
    MainActivity mainTabActivity;
    private Dialog  dialog ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.tab_1, container, false);

        this.imageView = (ImageView) v.findViewById(R.id.imageView1);
        Button photoButton = (Button) v.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        moredetails = (Button) v.findViewById(R.id.button6);
        moredetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                dialog = new Dialog(ReportingTab.this.getContext());
                dialog.setContentView(R.layout.popuptab1);
                MoreDetailsDialog x=  new MoreDetailsDialog(ReportingTab.this,dialog);
                x.dialog.setTitle("Please fill in the issue details");
                dialog.show();
            }
        });


        recordButton = (Button) v.findViewById(R.id.button2);
        playButton = (Button) v.findViewById(R.id.button3);
        buttonStop = (Button) v.findViewById(R.id.button5);

        Button submit= v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                EditText editText = v.findViewById(R.id.plain_text_input);
                String description= editText.getText().toString();
                ReprotedIssuesInterface manageReportedIssues = new FireBaseAPI();
                manageReportedIssues.createObject(bitmap,description);
                manageReportedIssues.getReportedIssues();
                Toast.makeText(getContext(), "Issue reported",
                        Toast.LENGTH_SHORT).show();
            }
        });

        audioConfig= new AudioConfig(this,buttonStop,recordButton,playButton);

        return v;
    }
    private Bitmap bitmap;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            bitmap=photo;
        }
    }

}
