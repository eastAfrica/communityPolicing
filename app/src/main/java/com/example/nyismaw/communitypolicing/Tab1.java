package com.example.nyismaw.communitypolicing;


    import android.*;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.graphics.Bitmap;
    import android.media.MediaPlayer;
    import android.media.MediaRecorder;
    import android.os.Environment;
    import android.support.annotation.NonNull;
    import android.support.v4.app.ActivityCompat;
    import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
    import android.util.Log;
    import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.Toast;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;

/**
 * Created by jarigye on 11/25/2017.
 */

public class Tab1 extends Fragment {
    private ImageView imageView;
    private static final int CAMERA_REQUEST = 1888;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    static  Button stopButton;
    static Button playButton;
    static  Button recordButton;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String audioFilePath;

    boolean isRecording = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.tab_1, container, false);

        this.imageView = (ImageView)v.findViewById(R.id.imageView1);
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
        stopButton = (Button) v.findViewById(R.id.button4);

      //  if (!hasMicrophone())
        {
         //   stopButton.setEnabled(false);
         //   playButton.setEnabled(false);
         //   recordButton.setEnabled(false);
       // } else {
            playButton.setEnabled(false);
            stopButton.setEnabled(false);
        }

        audioFilePath =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/myaudio.3gp";

        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    public void recordAudio (View view) throws IOException
    {
        isRecording = true;
        stopButton.setEnabled(true);
        playButton.setEnabled(false);
        recordButton.setEnabled(false);

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    public void stopClicked (View view)
    {

        stopButton.setEnabled(false);
        playButton.setEnabled(true);

        if (isRecording)
        {
            recordButton.setEnabled(false);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            recordButton.setEnabled(true);
        }

    }

    public void playAudio (View view) throws IOException
    {
        playButton.setEnabled(false);
        recordButton.setEnabled(false);
        stopButton.setEnabled(true);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
}
