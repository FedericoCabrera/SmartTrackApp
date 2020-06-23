package com.isp.smarttrackapp.model.repository.remote;


import android.app.NotificationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.RemoteMessage;
import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;

import java.util.Random;

public class FMCService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static FMCService instance;

    public FMCService(){
    }

    public static FMCService getInstance(){
        if(instance == null)
            instance = new FMCService();

        return instance;
    }

    @Override
    public void onNewToken(String token) {
        //super.onNewToken(token);
        LocalStorage.getInstance().setValue(token, Config.KEY_FCM_TOKEN);
        Log.d("FCM Token",token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_st_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setOngoing(false)
                .setOnlyAlertOnce(false)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Random random = new Random();
        manager.notify(random.nextInt(9999 - 1000) + 1000, builder.build());

    }

    public void initFCMToken(){
        final StringBuilder token = new StringBuilder();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        token.append(task.getResult().getToken());
                        LocalStorage.getInstance().setValue(token.toString(),Config.KEY_FCM_TOKEN);
                    }
                });

    }
}
