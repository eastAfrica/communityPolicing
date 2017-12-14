package com.example.nyismaw.communitypolicing.screens;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.controller.AudioConfig;
import com.example.nyismaw.communitypolicing.controller.issueConfig.IssueConfigProxy;
import com.example.nyismaw.communitypolicing.model.Enums.Category;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


/**
 * Created by jarigye on 11/25/2017.
 */

public class ReportingTab extends Fragment {

    public static final int RequestPermissionCode = 1;
    private static final int CAMERA_REQUEST = 1888;
    static Button buttonStop;
    static Button playButton;
    static Button recordButton;
    static Button moredetails;
    View v;
    AudioConfig audioConfig;

    MainActivity mainTabActivity;
    EditText editText;
    private ImageView imageView;
    private Dialog dialog;
    private Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.tab_1, container, false);

        editText = v.findViewById(R.id.plain_text_input);
        this.imageView = (ImageView) v.findViewById(R.id.imageView1);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_black_24dp));
        if (savedInstanceState != null) {

            byte[] byteArray = savedInstanceState.getByteArray("BitmapImage");
            if (byteArray != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200,
                        200, false));
            }
            if (editText.getText().length() != 0) {
                editText.setText(savedInstanceState.getInt("editText"));
            }
        }

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
                dialog.setTitle("Please fill in the issue details");


                MoreDetailsDialog moreDetailsDialog = new MoreDetailsDialog(ReportingTab.this, dialog);
                moreDetailsDialog.dialog.setTitle("Please fill in the issue details");
                dialog.show();
            }
        });

        recordButton = (Button) v.findViewById(R.id.button2);
        playButton = (Button) v.findViewById(R.id.button3);
        buttonStop = (Button) v.findViewById(R.id.button5);

        audioConfig = new AudioConfig(this, buttonStop, recordButton, playButton);


        Button submit = v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {

                String description = editText.getText().toString();
                IssueConfigProxy manageReportedIssues = new IssueConfigProxy();
                Log.e("Reporting failure", bitmap + ", " + description + "," + AudioConfig.isHasRecorded());
                if (bitmap == null && description.isEmpty()) {
                    if (!AudioConfig.isHasRecorded()) {
                        Toast.makeText(getContext(), "Please, at least provide one set of information",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                InputStream stream=null;
                try  {



                    stream = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/myaudio.3gp"));
                    Log.e("12","niigniginiiginiiginiiginiiginiiginiigini  "+stream);
                } catch (Exception e) {

                    Log.e("12","asdfasdf12123asdfasdfasdfasdfasdf");
                }

                    if (MoreDetailsDialog.categoryType == null) {
                        MoreDetailsDialog.categoryType = Category.ACCIDENTS.toString();
                    }
                    manageReportedIssues.reportIssue(bitmap, description, MoreDetailsDialog.categoryType,
                            MoreDetailsDialog.severityOfIssue, MoreDetailsDialog.vehiclesInvolved,stream);

                    manageReportedIssues.getReportedIssues();
                    Toast.makeText(getContext(), "Issue reported",
                            Toast.LENGTH_SHORT).show();
                    imageView.setImageBitmap(null);
                    editText.setText(null);

                    MoreDetailsDialog.categoryType = null;
                    MoreDetailsDialog.severityOfIssue = null;
                    MoreDetailsDialog.vt = null;


                }
            });

        return v;
        }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            bitmap = photo;

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            outState.putByteArray("BitmapImage", byteArray);
            outState.putString("editText", editText.getText().toString());
        }
    }


}
