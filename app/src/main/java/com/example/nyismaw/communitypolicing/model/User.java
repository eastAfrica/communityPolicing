package com.example.nyismaw.communitypolicing.model;

import android.util.Log;

/**
 * Created by nyismaw on 11/24/2017.
 */

public  class User {

     private String username;
     private String email;
     private boolean isApolice;
     private String id;


    public User() {
        this.isApolice = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isApolice() {
        return isApolice;
    }

    public void setApolice(boolean apolice) {
        this.isApolice = apolice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
