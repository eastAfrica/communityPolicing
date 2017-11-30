package com.example.nyismaw.communitypolicing.firebaseApi;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.example.nyismaw.communitypolicing.controller.maps.AppLocationListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by nyismaw on 11/29/2017.
 */

public class DownloadFile {

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

                appLocationListener.show(bytes,imageName);
//                try {
//                    File file = new File(path);
//                    if (!file.exists()) {
//                        file.createNewFile();
//                    }
//                    FileOutputStream fos = new FileOutputStream(file);
//                    Log.e("download", "image name is ----------------------------" + imageName);
//
//                    fos.write(bytes);
//                    fos.close();
//
//                    appLocationListener.show(bytes,imageName);
//
//
//                } catch (FileNotFoundException e) {
//                    Log.e("download", "image name is ----------------------------" + imageName);
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

}

