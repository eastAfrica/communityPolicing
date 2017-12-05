package com.example.nyismaw.communitypolicing.ApiWrapper;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.controller.AudioConfig;
import com.example.nyismaw.communitypolicing.controller.filters.FetchedIssues;
import com.example.nyismaw.communitypolicing.controller.location.AppLocationListener;
import com.example.nyismaw.communitypolicing.model.Accident;
import com.example.nyismaw.communitypolicing.AppInfo.CurrentLocation;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.model.MyLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 12/2/2017.
 */

public class FireBaseProxy {
    static boolean imageDone = false;
    static boolean audioDone = false;

    private StorageReference mStorageRef;
    private AppLocationListener appLocationListener;
    FirebaseDatabase database;
    DatabaseReference myRef;

    public FireBaseProxy() {
        this.appLocationListener = appLocationListener;
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://communitypolicing-f24cf.appspot.com");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("issues");
    }

    public FireBaseProxy(AppLocationListener appLocationListener) {
        this.appLocationListener = appLocationListener;
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://communitypolicing-f24cf.appspot.com");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("issues");
    }

    public void fireBasePoliceId() {
        //  Log.e("tag","get object is called");
        final List<Object> objects = new ArrayList<>();
        DatabaseReference myRef2 = database.getReference("police");
        myRef2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                String policeId = (String) dataSnapshot.getValue();
                FetchedIssues.getPoliceId().add(policeId);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            // ...
        });
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


        try {
            Object o = mStorageRef.child("audio/" + audioName + ".3gp").getDownloadUrl().getResult();
            Log.e("audio name is ", "audio name download url is  " + o);

        } catch (Exception e) {

            Log.e("Exception audio name", "audio name is not found in storage" + audioName);
        }

        mStorageRef.child("audio/" + audioName + ".3gp").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                String path = Environment.getExternalStorageDirectory() + "/" + audioName;
                show(bytes, audioName, 1);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Log.e("Exception ", "Exception  this" + audioName);
                // Handle any errors
                show(null, audioName, 1);
            }
        });

    }

    static byte[] image;
    static byte[] audio;

    private void show(byte[] bytes, String audioName, int type) {

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

    public void getImageByid(String id) {
        File localFile = null;
        try {
            localFile = File.createTempFile("images/" + id, "jpg");
        } catch (Exception ex) {

        }
        if (localFile != null) {
            mStorageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        }


    }

    public void createObject(Object object, String description) {

        String id = myRef.child("Issues").push().getKey();
        Issues issues = new Issues();
        issues.setDetails(description);
        Accident accident = new Accident();
        accident.setVehicles("vehicle1, vehicle2");
        accident.setSeverity("severe");
        accident.setId(id);

        if (object != null) {
            UploadFileInterface fileInterface = new FireBaseAPI();
            fileInterface.UploadImage((Bitmap) object, id);


        }
        if (AudioConfig.isHasRecorded() == true) {
            UploadFileInterface fileInterface = new FireBaseAPI();
            fileInterface.uploadAudio(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/myaudio.3gp", id);
            AudioConfig.setHasRecorded(true);

        }
        issues.setId(id);
        MyLocation mylocation = new MyLocation();
        mylocation.setLatitude(new CurrentLocation().getLocation().getLatitude());
        mylocation.setLongtude(new CurrentLocation().getLocation().getLongitude());
        issues.setLocation(mylocation);
        issues.setTxt(description);
        issues.setUserid(CurrentUser.user);
        myRef.child(id).setValue(issues);

    }

    public List<Object> getReportedIssues() {
        //  Log.e("tag","get object is called");
        final List<Object> objects = new ArrayList<>();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Issues issues = dataSnapshot.getValue(Issues.class);
                FetchedIssues.addIssue(issues);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            // ...
        });
        return objects;

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
