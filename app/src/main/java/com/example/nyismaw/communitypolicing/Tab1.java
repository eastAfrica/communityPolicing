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




        OUTPUT_FILE= Environment.getExternalStorageDirectory()+"/VoiceRecorder.3gpp";

        Button starts=(Button)v.findViewById(R.id.button2);
        starts.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                File outfile=new File(OUTPUT_FILE);

                if(outfile.exists()){
                    outfile.delete();
                }
                try {
                    StartRecord();
                    Toast.makeText(getContext(), "Nada",
                            Toast.LENGTH_SHORT).show();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        Button stopb=(Button)v.findViewById(R.id.button4);
        stopb.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "Nada????stop????",
                        Toast.LENGTH_SHORT).show();
                StopRecord();
            }

        });
        Button playb=(Button)v.findViewById(R.id.button3);
        playb.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "Nada????play????",
                        Toast.LENGTH_SHORT).show();
                StopRecord();
            }

        });

        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }


    protected void StopRecord() {
        // TODO Auto-generated method stub
        if(recorder!=null){
            recorder.stop();
        }
    }



    private void StartRecord() throws IllegalStateException, IOException {
        // TODO Auto-generated method stub
        recorder=new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(OUTPUT_FILE);

        recorder.prepare();

        recorder.start();
    }

    private void LiberarMicro() {
        // TODO Auto-generated method stub
        if(recorder!=null){
            recorder.release();
        }

    }

}
