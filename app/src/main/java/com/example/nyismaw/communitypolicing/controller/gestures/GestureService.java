package com.example.nyismaw.communitypolicing.controller.gestures;

import android.app.IntentService;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("Gesture service", "Gesture performed ");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        LinearLayout ll;
        WindowManager.LayoutParams ll_lp;

        ll_lp = new WindowManager.LayoutParams();
        ll_lp.format = PixelFormat.TRANSLUCENT;
        ll_lp.height = WindowManager.LayoutParams.FILL_PARENT;
        ll_lp.width = WindowManager.LayoutParams.FILL_PARENT;
        ll_lp.gravity = Gravity.CLIP_HORIZONTAL | Gravity.TOP;

        ll_lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        ll_lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        ll_lp.flags = ll_lp.flags | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        ll = new LinearLayout(this);
        ll.setBackgroundColor(android.graphics.Color.argb(0, 0, 0, 0));
        ll.setHapticFeedbackEnabled(true);
        wm.addView(ll, ll_lp);
        TextView textView = new TextView(this);
        textView.setText("NEba overlay");
        textView.setTextColor(Color.WHITE);
        wm.addView(textView, ll_lp);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Mannnnnn", "Gesure worked dddddddddddddddddddddd");

            }
        });
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {


    }
}
