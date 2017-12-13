package com.example.nyismaw.communitypolicing.controller.maps;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.controller.filters.FetchedIssues;
import com.example.nyismaw.communitypolicing.controller.location.AppLocationListener;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.screens.MainTabActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.nyismaw.communitypolicing.R.drawable.ic_broken_image_black_24dp;

/**
 * Created by nyismaw on 12/2/2017.
 */

public class MapDialog {

    public void showDialog(final MainTabActivity mainTabActivity, final byte[] bytes, final byte[] audio, final Dialog dialog) {
        DisplayMetrics metrics = mainTabActivity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((6 * width) / 8, (2 * height) / 6);

        if (bytes != null) {
            Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageView myImage = (ImageView) dialog.findViewById(R.id.imageViewForMap);

            myImage.setLayoutParams(layoutParams);
            myImage.setImageBitmap(myBitmap);

        } else {

            ImageView myImage = (ImageView) dialog.findViewById(R.id.imageViewForMap);
            myImage.setImageResource(ic_broken_image_black_24dp);

        }

        final Button playButton = dialog.findViewById(R.id.popUpPlay);
        final MediaPlayer mediaPlayer = new MediaPlayer();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audio != null) {
                    try {

                        if (mediaPlayer.isPlaying()) {

                            mediaPlayer.stop();
                            playButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                            return;
                        }

                        playButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                        File tempMp3 = File.createTempFile("Sample", "mp3", mainTabActivity.getCacheDir());
                        tempMp3.deleteOnExit();

                        FileOutputStream fos = new FileOutputStream(tempMp3);
                        fos.write(audio);
                        fos.close();
                        mediaPlayer.reset();

                        FileInputStream fis = new FileInputStream(tempMp3);
                        mediaPlayer.setDataSource(fis.getFD());

                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException ex) {
                        String s = ex.toString();
                        ex.printStackTrace();
                    }

                } else {

                    Toast.makeText(mainTabActivity, "No audio recorded",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        dialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);

        if (CurrentUser.user != null)
            Log.e("Resolve", "current issue11111111 " + CurrentUser.user.isApolice());
        if (CurrentUser.user.isApolice()) {
            Button button = new Button(mainTabActivity);
            button.setText(R.string.Resolve);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -2);
            dialog.addContentView(button, params);
            final String issueId = MapUpdate.getCurrentIssueId();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Issues issueById = FetchedIssues.getIssueById(issueId);
                    if (issueById != null)
                        issueById.setResolved(true);
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("issues");
                    myRef.child(issueId).removeValue();
                    Log.e("Resolve", "current issue " + issueId);
                    Toast.makeText(mainTabActivity, "Issue resolved",
                            Toast.LENGTH_SHORT).show();
                    dialog.hide();
                }
            });


        }
        try {
            dialog.show();
        } catch (Exception e) {


        }
    }
}

