package FirebaseApi;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import Model.Accident;
import Model.Issues;
import Model.CurrentLocation;
import Model.User;
import Model.myLocation;

/**
 * Created by nyismaw on 11/24/2017.
 */

public class Create {

    FirebaseDatabase database;
    DatabaseReference myRef;

    public Create() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("issues");
    }

    public void createObject(Object object,String description) {

        String id = myRef.child("Issues").push().getKey();
        Issues issues = new Issues();
        issues.setDetails(description);
        Accident accident = new Accident();
        accident.setVehicles("vehicle1, vehicle2");
        accident.setSeverity("severe");
        accident.setId(id);

        new UploadFile().UploadImage((Bitmap) object,id);
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
        Log.e("tag","get object is called");
        final List<Object> objects = new ArrayList<>();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                // Log.e("asdf","it gets ehre ");
                Log.e("tag","on child added");

                Issues issues = dataSnapshot.getValue(Issues.class);
              //  if ((issues.getLocation().getLatitude()) > 10) {
                    objects.add(issues);
                    Log.e("Firebase ", "Abc " + dataSnapshot.getKey() + " was " + issues.getLocation().getLatitude() + " ");

               // }
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
