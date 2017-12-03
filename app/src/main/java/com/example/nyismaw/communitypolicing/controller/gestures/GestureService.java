package com.example.nyismaw.communitypolicing.controller.gestures;

import android.app.IntentService;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureOverlayView;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by nyismaw on 12/3/2017.
 */

public class GestureService extends IntentService implements GestureOverlayView.OnGesturePerformedListener {

    public GestureService(String name) {
        super(name);
    }
    public GestureService() {
        super("GestureService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.setTitle("Load Average");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(new View(this), params);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        Log.e("Gesture service","Gesture performed ");


    }
}
