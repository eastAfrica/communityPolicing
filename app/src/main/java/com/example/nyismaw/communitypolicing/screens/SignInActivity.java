package com.example.nyismaw.communitypolicing.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.controller.login.SignInFactory;
import com.example.nyismaw.communitypolicing.controller.login.SignInInterface;
import com.example.nyismaw.communitypolicing.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.example.nyismaw.communitypolicing.model.User;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static final int RC_SIGN_IN = 9001;
    private static final int CAMERA_REQUEST = 1888;
    private String TAG = "Main Activity";
    private GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    SignInInterface signInInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SignInActivity signInActivity = this;
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInInterface = SignInFactory.getLogin(signInActivity, "signInWithGoogle");
                signInInterface.signin();

            }
        });

        findViewById(R.id.sign_in_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInInterface = SignInFactory.getLogin(signInActivity, "signInAnonumus");
                signInInterface.signin();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                signInInterface.authenticate(account);
                User user = new User();
                user.setUsername(account.getDisplayName());
                user.setEmail(account.getEmail());
                CurrentUser.user = user;
                startMainActivity();
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainTabActivity.class);
        startActivity(intent);
    }
}
