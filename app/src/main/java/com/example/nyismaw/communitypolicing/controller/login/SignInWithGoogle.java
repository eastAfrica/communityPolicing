package com.example.nyismaw.communitypolicing.controller.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.screens.SignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.example.nyismaw.communitypolicing.screens.SignInActivity.RC_SIGN_IN;

/**
 * Created by nyismaw on 11/28/2017.
 */

public class SignInWithGoogle extends Activity implements LoginInterface   {

    private GoogleSignInClient mGoogleSignInClient;
    SignInActivity  signInActivity;
    private String TAG = "Main Activity";
    FirebaseAuth mAuth;
    GoogleSignInOptions gso;
    public SignInWithGoogle(SignInActivity  signInActivity) {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(signInActivity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.signInActivity=signInActivity;
        mGoogleSignInClient = GoogleSignIn.getClient(signInActivity, gso);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void signin() {
        Log.e("TAG", "sign in started");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        signInActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void signout() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(signInActivity,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(signInActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(signInActivity, "Signed in as." + user.getDisplayName(),
                                    Toast.LENGTH_SHORT).show();


                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(signInActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void authenticate(Object object) {
        firebaseAuthWithGoogle((GoogleSignInAccount) object);
    }
}
