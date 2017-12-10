package com.example.nyismaw.communitypolicing.controller.signIn;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.screens.ReportingTab;
import com.example.nyismaw.communitypolicing.screens.SignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by nyismaw on 12/10/2017.
 */

public class SignoutUser implements SignoutInterface{

    ReportingTab reportingTab ;

    public SignoutUser(ReportingTab reportingTab) {
        this.reportingTab = reportingTab;
    }

    @Override
    public void signout() {


        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        SignInActivity.getmGoogleSignInClient().signOut().addOnCompleteListener(reportingTab.getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Log.e("Sign out","Sign out done//////////////////////////");

                    }
                });
    }
}
