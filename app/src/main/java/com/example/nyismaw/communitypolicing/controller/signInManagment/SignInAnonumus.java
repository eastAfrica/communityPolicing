package com.example.nyismaw.communitypolicing.controller.signInManagment;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.model.User;
import com.example.nyismaw.communitypolicing.screens.SignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by nyismaw on 11/28/2017.
 */

public class SignInAnonumus implements SignInInterface {

    private GoogleSignInClient mGoogleSignInClient;
    SignInActivity  signInActivity;
    private String TAG = "Main Activity";
    FirebaseAuth mAuth;

    public SignInAnonumus(SignInActivity signInActivity) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(signInActivity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.signInActivity=signInActivity;
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void signin() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(signInActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser account = mAuth.getCurrentUser();
                            User user = new User();
                            user.setUsername(" Anonymous ");
                            user.setEmail(account.getEmail());
                            CurrentUser.user = user;
                            Toast.makeText(signInActivity, "Signed in as "+user.getUsername(),
                                    Toast.LENGTH_SHORT).show();
                            signInActivity.startMainActivity();

                        } else {
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(signInActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }



    @Override
    public void authenticate(Object obj,boolean startactivity) {

    }
}
