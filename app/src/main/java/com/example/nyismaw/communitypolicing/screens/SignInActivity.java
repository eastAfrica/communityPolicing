package com.example.nyismaw.communitypolicing.screens;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nyismaw.communitypolicing.ApiWrapper.FireBaseAPI;
import com.example.nyismaw.communitypolicing.ApiWrapper.ReprotedIssuesInterface;
import com.example.nyismaw.communitypolicing.AppInfo.CurrentLocation;
import com.example.nyismaw.communitypolicing.controller.signInManagment.SignInFactory;
import com.example.nyismaw.communitypolicing.controller.signInManagment.SignInInterface;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.controller.signInManagment.SignInWithGoogle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static final int RC_SIGN_IN = 9001;
    private static final int CAMERA_REQUEST = 1888;
    public static final int RequestPermissionCode = 1;
    private String TAG = "Main Activity";
    private static GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    SignInInterface signInInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {

            SignInInterface signInInterface = new SignInWithGoogle(SignInActivity.this);

            signInInterface.authenticate(account, false);
            Log.e("User account ", "Exists " + account.getDisplayName());
            startMainActivity();

        }
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
                //     signInInterface.authenticate(account);
                SignInInterface signInInterface = new SignInWithGoogle(SignInActivity.this);
                Log.e("Sign in with", "Step 2,step 2");
                signInInterface.authenticate(account, true);
                //  startMainActivity();

            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainTabActivity.class);
        startActivity(intent);
        finish();
    }


    public void checkPermissions() {
        int result = ContextCompat.checkSelfPermission(this,
                WRITE_EXTERNAL_STORAGE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                    String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, this.RequestPermissionCode);
        }


        int read = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE);
        if (read != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new
                    String[]{READ_EXTERNAL_STORAGE, RECORD_AUDIO}, this.RequestPermissionCode);

        }







    }


    public static GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }


    public static void setmGoogleSignInClient(GoogleSignInClient mGoogleSignInClient) {
        SignInActivity.mGoogleSignInClient = mGoogleSignInClient;
    }
}
