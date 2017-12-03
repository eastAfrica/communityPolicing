package com.example.nyismaw.communitypolicing.controller.notification;

/**
 * Created by nyismaw on 12/3/2017.
 */
/*
On my honor, as a Carnegie-Mellon Rwanda student, I have neither given nor received unauthorized assistance on this work.

 */

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.example.nyismaw.communitypolicing.R;

/**
 * Created by nyismaw on 10/29/2017.
 */

public class PushNotifications implements NotificationInterface{
    Context context;

    public PushNotifications(Context context) {
        this.context = context;
        this.vibrate=true;
        this.sound=true;
    }
    private boolean vibrate;
    private boolean sound;

    public void sendNotification(String Title, String content ) {
        NotificationCompat.Builder mBuilder;

        if (vibrate == true && sound == true) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder =
                    new NotificationCompat.Builder(context).setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setContentTitle(Title)
                            .setContentText(content).setSound(uri);

        } else if (vibrate == true) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder =
                    new NotificationCompat.Builder(context).setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setContentTitle("Activity notification")
                            .setContentText("You have been sitting for too long! Better you exercise");
        } else if (sound == true) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder =
                    new NotificationCompat.Builder(context).setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setContentTitle("Activity notification")
                            .setContentText("You have been sitting for too long! Better you exercise").setSound(uri);


        } else {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder =
                    new NotificationCompat.Builder(context).setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setContentTitle("Activity notification")
                            .setContentText("You have been sitting for too long! Better you exercise");

        }
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());


    }
}
