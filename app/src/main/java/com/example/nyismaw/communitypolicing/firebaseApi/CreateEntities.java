package com.example.nyismaw.communitypolicing.firebaseApi;

import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.controller.AudioConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.nyismaw.communitypolicing.model.Accident;
import com.example.nyismaw.communitypolicing.model.Issues;
import com.example.nyismaw.communitypolicing.model.CurrentLocation;
import com.example.nyismaw.communitypolicing.model.myLocation;
import com.example.nyismaw.communitypolicing.controller.filter.*;
/**
 * Created by nyismaw on 11/24/2017.
 */

public class CreateEntities {

    FirebaseDatabase database;
    DatabaseReference myRef;

    public CreateEntities() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("issues");
    }

    public void createObject(Object object, String description) {

        String id = myRef.child("Issues").push().getKey();
        Issues issues = new Issues();
        issues.setDetails(description);
        Accident accident = new Accident();
        accident.setVehicles("vehicle1, vehicle2");
        accident.setSeverity("severe");
        accident.setId(id);

        if(object!=null)
        new UploadFile().UploadImage((Bitmap) object, id);
        if(AudioConfig.isHasRecorded()==true){
            new UploadFile().uploadAudio(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/myaudio.3gp",id);
            AudioConfig.setHasRecorded(true);

        }
        issues.setId(id);
        myLocation mylocation = new myLocation();
        mylocation.setLatitude(new CurrentLocation().getLocation().getLatitude());
        mylocation.setLongtude(new CurrentLocation().getLocation().getLongitude());
        issues.setLocation(mylocation);
        issues.setTxt(description);
        issues.setUserid(CurrentUser.user);
        myRef.child(id).setValue(issues);

    }

    public List<Object> getObject() {
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
}
