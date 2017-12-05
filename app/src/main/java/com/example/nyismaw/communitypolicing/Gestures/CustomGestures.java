package com.example.nyismaw.communitypolicing.Gestures;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.nyismaw.communitypolicing.R;

import static com.example.nyismaw.communitypolicing.R.layout.activity_main_tab;

/**
 * Created by johnmunyi on 12/3/17.
 */

public class CustomGestures extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {

    private GestureLibrary gestureLibrary;

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {

    }
}
