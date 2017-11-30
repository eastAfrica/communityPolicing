package com.example.nyismaw.communitypolicing.firebaseApi;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nyismaw.communitypolicing.controller.maps.AppLocationListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

/**
 * Created by nyismaw on 11/29/2017.
 */

public class DownloadFile {
    static boolean imageDone = false;
    static boolean audioDone = false;

    private StorageReference mStorageRef;
    private AppLocationListener appLocationListener;

    public DownloadFile(AppLocationListener appLocationListener) {
        this.appLocationListener = appLocationListener;
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://communitypolicing-f24cf.appspot.com");
    }

    public void downLoadImage(final String imageName) {

        mStorageRef.child("images/" + imageName + ".jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                String path = Environment.getExternalStorageDirectory() + "/" + imageName;

                show(bytes, imageName, 0);

            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e("Exception ", "Exception imageeeeeeeee");
                show(null, imageName, 0);
            }
        });
    }

    public void downLoadAudio(final String audioName) {

        Log.e("audio name is ", "audio name is " + audioName);

        mStorageRef.child("audio/" + audioName + ".3gp").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                String path = Environment.getExternalStorageDirectory() + "/" + audioName;
                show(bytes, audioName, 1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Log.e("Exception ", "Exception " + audioName);
                // Handle any errors
                show(null, audioName, 1);
            }
        });

    }

    static byte[] image;
    static byte[] audio;

    private void show(byte[] bytes, String audioName, int type) {
        if (bytes == null)
            return;
        if (type == 0) {
            image = bytes;
            imageDone = true;
        } else {
            audio = bytes;
            audioDone = true;

        }

        if ((imageDone && audioDone) == true) {
            appLocationListener.show(image, audio, audioName);
        }
        Log.e("check boolean", " image " + imageDone + " , audio " + audioDone + " ");

    }

}

