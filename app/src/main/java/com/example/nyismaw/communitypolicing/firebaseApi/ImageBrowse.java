package com.example.nyismaw.communitypolicing.firebaseApi;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * Created by nyismaw on 11/25/2017.
 */

public class ImageBrowse {

    private StorageReference mStorageRef;

    public ImageBrowse(){

        mStorageRef= FirebaseStorage.getInstance().getReferenceFromUrl("gs://communitypolicing-f24cf.appspot.com");
    }

    public void getImageByid(String id){
        File localFile = null;
       try{
            localFile = File.createTempFile("images/"+id, "jpg");
       }
       catch (Exception ex){

       }

        mStorageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

    }
}
