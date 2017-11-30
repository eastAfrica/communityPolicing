package com.example.nyismaw.communitypolicing.screens;


import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import com.example.nyismaw.communitypolicing.firebaseApi.CreateEntities;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.controller.AudioConfig;

/**
 * Created by jarigye on 11/25/2017.
 */

public class ReportingTab extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    static Button buttonStop;
    static Button buttonStopPlayingRecording;
    static Button playButton;
    static Button recordButton;
    String audioFilePath;
    boolean isRecording = false;
    private ImageView imageView;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    View v;
    AudioConfig audioConfig;
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


        recordButton = (Button) v.findViewById(R.id.button2);
        playButton = (Button) v.findViewById(R.id.button3);
        buttonStop = (Button) v.findViewById(R.id.button4);

        Button submit= v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                EditText editText = v.findViewById(R.id.plain_text_input);
                String description= editText.getText().toString();
                CreateEntities createEntities = new CreateEntities();
                createEntities.createObject(bitmap,description);
                createEntities.getObject();
                Toast.makeText(getContext(), "Issue reported",
                        Toast.LENGTH_SHORT).show();
            }
        });


        buttonStop = (Button) v.findViewById(R.id.button4);
        buttonStopPlayingRecording = (Button)v.findViewById(R.id.button5);
        audioConfig= new AudioConfig(this,buttonStop,buttonStopPlayingRecording,recordButton,playButton);

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
