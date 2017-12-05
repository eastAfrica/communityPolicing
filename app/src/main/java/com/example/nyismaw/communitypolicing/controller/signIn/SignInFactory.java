package com.example.nyismaw.communitypolicing.controller.signIn;

import com.example.nyismaw.communitypolicing.screens.SignInActivity;

/**
 * Created by nyismaw on 11/28/2017.
 */

public class SignInFactory {
    public static SignInInterface getLogin(SignInActivity signInActivity, String loginType) {
        if (loginType.equals("signInWithGoogle"))
            return new SignInWithGoogle(signInActivity);
        else if (loginType.equals("signInAnonumus"))
            return new SignInAnonumus(signInActivity);
        return null;

    }
}
