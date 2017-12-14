package com.example.nyismaw.communitypolicing.controller.signInManagment;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.controller.filters.FetchedIssues;
import com.example.nyismaw.communitypolicing.model.User;
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

import java.util.List;

import static com.example.nyismaw.communitypolicing.screens.SignInActivity.RC_SIGN_IN;

/**
 * Created by nyismaw on 11/28/2017.
 */

public class SignInWithGoogle extends Activity implements SignInInterface {

    private GoogleSignInClient mGoogleSignInClient;
    SignInActivity signInActivity;
    private String TAG = "Main Activity";
    FirebaseAuth mAuth;
    GoogleSignInOptions gso;

    public SignInWithGoogle(SignInActivity signInActivity) {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(signInActivity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.signInActivity = signInActivity;
        mGoogleSignInClient = GoogleSignIn.getClient(signInActivity, gso);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void signin() {
        Log.e("TAG", "sign in started");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void firebaseAuthWithGoogle(final GoogleSignInAccount acct, final boolean startActivity) {
        Log.e(TAG, " firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(signInActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User();
                            user.setUsername(acct.getDisplayName());
                            user.setEmail(acct.getEmail());
                            FirebaseUser userF = mAuth.getCurrentUser();
                            List<String> policeId = FetchedIssues.getPoliceId();
                            String userId = userF.getUid();
                            user.setId(userId);
                            if (policeId != null) {
                                for (String string : policeId) {
                                    if (userId.equals(string)) {
                                        user.setApolice(true);
                                    }
                                }

                            }
                            Log.e("You are ", "Assign users -----------------------------");
                            CurrentUser.user = user;
                            if (startActivity == true)
                                signInActivity.startMainActivity();
                            else {
                                signInActivity.finish();
                            }
                            Toast.makeText(signInActivity, "Signed in as." + user.getUsername(),
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
    public void authenticate(Object object, boolean startActivity) {
        Log.e("Authentication started ", "Authenticate Users -----------------");
        this.firebaseAuthWithGoogle((GoogleSignInAccount) object, startActivity);
        Log.e("Authentication ended ", "Ended  Users -----------------");
    }
}
