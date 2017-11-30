package com.example.nyismaw.communitypolicing.firebaseApi;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by nyismaw on 11/25/2017.
 */

public class UploadFile {
    private StorageReference mStorageRef;

    public UploadFile() {

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://communitypolicing-f24cf.appspot.com");
    }

    public void uploadAudio(String path, String fileName) {

        try {
            InputStream stream = new FileInputStream(new File(path));
            StorageReference riversRef = mStorageRef.child("audio/" + fileName + ".3gp");
            riversRef.putStream(stream)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Log.e("Audio downloaded", " File finished downloading ");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("Audio failed", " Faield ************* downloading ");
                        }
                    });
        } catch (FileNotFoundException ex) {
            Log.e("Audio ", "Upload failed ************* " + ex.getMessage());
        }
    }

    public void UploadImage(Bitmap bitmap, String filename) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference riversRef = mStorageRef.child("images/" + filename + ".jpg");
        riversRef.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.e("File downloaded", "File finished downloading ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
    }
}
