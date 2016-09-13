package com.example.scame.savealifenotifier.firebase;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.data.mappers.DriverMessageMapper;
import com.example.scame.savealifenotifier.data.mappers.HelpMessageMapper;
import com.example.scame.savealifenotifier.data.repository.IUserDataManager;
import com.example.scame.savealifenotifier.data.repository.UserDataManagerImp;
import com.example.scame.savealifenotifier.presentation.activities.DriversHelpMapActivity;
import com.example.scame.savealifenotifier.presentation.activities.GoogleHelpMapActivity;
import com.example.scame.savealifenotifier.presentation.fragments.EndPointFragment;
import com.example.scame.savealifenotifier.presentation.models.DriversMessageModel;
import com.example.scame.savealifenotifier.presentation.models.HelpMessageModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "logMessagingService";

    private static final int COMMON_MSG_ID = 1;
    private static final int DRIVERS_MSG_ID = 2;

    private IUserDataManager userDataManager;

    public MyFirebaseMessagingService() {
        userDataManager = new UserDataManagerImp();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            // path key's available only in driver's type messages
            if (remoteMessage.getData().keySet().contains("path")) {
                // check if driver isn't ambulance driver
                if (!ambulanceModeEnabled()) {
                    DriverMessageMapper driverMessageMapper = new DriverMessageMapper();
                    DriversMessageModel driversMessageModel = driverMessageMapper.convert(remoteMessage.getData());

                    sendDriversNotification(driversMessageModel);
                }
            } else {
                // if there's no path key then it's a help message
                HelpMessageMapper helpMessageMapper = new HelpMessageMapper();
                HelpMessageModel helpMessageModel = helpMessageMapper.convert(remoteMessage.getData());

                sendCommonNotification(helpMessageModel);
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private boolean ambulanceModeEnabled() {
        return userDataManager.getUserMode() == EndPointFragment.AMBULANCE_MODE;
    }

    private void sendCommonNotification(HelpMessageModel helpMessage) {

        Intent intent = new Intent(this, GoogleHelpMapActivity.class);
        intent.putExtra(GoogleHelpMapActivity.class.getCanonicalName(), helpMessage);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                        .setContentTitle("global: someone needs help")
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

    private void sendDriversNotification(DriversMessageModel messageModel) {

        Intent intent = new Intent(this, DriversHelpMapActivity.class);
        intent.putExtra(DriversHelpMapActivity.class.getCanonicalName(), messageModel);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                        .setContentTitle("drivers: someone needs help")
                        .setContentText(messageModel.getMessageBody());

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
