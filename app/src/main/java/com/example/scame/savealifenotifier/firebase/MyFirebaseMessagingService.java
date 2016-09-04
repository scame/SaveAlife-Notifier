package com.example.scame.savealifenotifier.firebase;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.activities.DriversHelpMapActivity;
import com.example.scame.savealifenotifier.presentation.activities.GoogleHelpMapActivity;
import com.example.scame.savealifenotifier.presentation.models.DriversMessageModel;
import com.example.scame.savealifenotifier.presentation.models.HelpMessageModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "logMessagingService";

    private static final int COMMON_MSG_ID = 1;
    private static final int DRIVERS_MSG_ID = 2;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
            // TODO: determine message type & display a notification
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            // TODO: determine message type & display a notification
        }
    }


    private void sendCommonNotification(String messageBody) {
        Gson gson = new GsonBuilder().create();
        HelpMessageModel helpMessage = gson.fromJson(messageBody, HelpMessageModel.class);

        Intent intent = new Intent(this, GoogleHelpMapActivity.class);
        intent.putExtra(GoogleHelpMapActivity.class.getCanonicalName(), helpMessage);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                        .setContentTitle("Someone needs help")
                        .setContentText(helpMessage.getMessage());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(GoogleHelpMapActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(COMMON_MSG_ID, builder.build());
    }

    private void sendDriversNotification(String messageBody) {
        Gson gson = new GsonBuilder().create();
        DriversMessageModel messageModel = gson.fromJson(messageBody, DriversMessageModel.class);

        Intent intent = new Intent(this, DriversHelpMapActivity.class);
        intent.putExtra(DriversHelpMapActivity.class.getCanonicalName(), messageBody);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                        .setContentTitle("Driver, someone needs help")
                        .setContentText(messageModel.getMessage());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DriversHelpMapActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(DRIVERS_MSG_ID, builder.build());
    }
}
