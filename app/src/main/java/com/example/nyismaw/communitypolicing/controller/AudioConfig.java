package com.example.nyismaw.communitypolicing.controller;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.screens.ReportingTab;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by nyismaw on 11/30/2017.
 */

public class AudioConfig {

    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
   // final Button buttonStop;
    final Button buttonStopPlayingRecording;
    final Button playButton;
    final Button recordButton;
    String audioFilePath;
    boolean isRecording = false;
    MediaPlayer mediaPlayer;
    final ReportingTab reportingTab;
    static private boolean hasRecorded=false;

    public static boolean isHasRecorded() {
        return hasRecorded;
    }

    public static void setHasRecorded(boolean hasRecorded) {
        AudioConfig.hasRecorded = hasRecorded;
    }

    public AudioConfig(ReportingTab reportingTab1, Button buttonStopPlayingRecording1, Button recordButton1,
                       Button playButton1) {
        //this.buttonStop = buttonStop1;
        this.buttonStopPlayingRecording = buttonStopPlayingRecording1;
        this.playButton = playButton1;
        this.recordButton = recordButton1;
        this.reportingTab = reportingTab1;

        AudioSavePathInDevice =
                // Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                //      CreateRandomAudioFileName(5) + "AudioRecording.3gp";
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "audio/myaudio.3gp";
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!checkPermission()){
                    requestPermission();
                }
                if (checkPermission()) {

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + "/myaudio.3gp";
                    MediaRecorderReady();
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        setHasRecorded(true);
                    } catch (IllegalStateException e) {

                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("tag", "Io exception  " + e.getMessage());

                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    recordButton.setEnabled(false);
                    //buttonStop.setEnabled(true);

                    Toast.makeText(reportingTab.getActivity(), "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }

            }

        });
        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              

                if(mediaRecorder==null){

                    Toast.makeText(reportingTab.getActivity(), R.string.Recordnotstarted,
                            Toast.LENGTH_LONG).show();
                    return;

                }


                mediaRecorder.stop();
                // buttonStop.setEnabled(false);
                playButton.setEnabled(true);
                recordButton.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);

                Toast.makeText(reportingTab.getActivity(), R.string.RecordingComplete,
                        Toast.LENGTH_LONG).show();
            }
        });
        mediaPlayer = new MediaPlayer();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                //buttonStop.setEnabled(false);
                //  recordButton.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);


                try {
                    if (mediaPlayer.isPlaying()) {

                        mediaPlayer.stop();
                        playButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                        return;
                    }

                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    Toast.makeText(reportingTab.getActivity(), R.string.RecordingPlay,
                            Toast.LENGTH_LONG).show();
                    playButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            playButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(reportingTab.getActivity(), "Audio not recorded "+AudioSavePathInDevice,
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        });


    }

    protected boolean hasMicrophone() {
        PackageManager pmanager = reportingTab.getActivity().getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }

    public void recordAudio(View view) throws IOException {
        isRecording = true;
        //buttonStop.setEnabled(true);
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

    public void stopClicked(View view) {

       // buttonStop.setEnabled(false);
        playButton.setEnabled(true);

        if (isRecording) {
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

    public void playAudio(View view) throws IOException {
        playButton.setEnabled(false);
        recordButton.setEnabled(false);
       // buttonStop.setEnabled(true);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public String CreateRandomAudioFileName(int string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();
    }

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(reportingTab.getActivity(),
                WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(reportingTab.getActivity(),
                READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(reportingTab.getActivity(),
                RECORD_AUDIO);
        Log.e("Checking e"," permission is "+(read==PackageManager.PERMISSION_GRANTED));
        return result == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(reportingTab.getActivity(), new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, reportingTab.RequestPermissionCode);
        ActivityCompat.requestPermissions(reportingTab.getActivity(), new
                String[]{READ_EXTERNAL_STORAGE, RECORD_AUDIO}, reportingTab.RequestPermissionCode);
    }

}
