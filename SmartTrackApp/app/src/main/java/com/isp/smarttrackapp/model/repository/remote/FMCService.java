package com.isp.smarttrackapp.model.repository.remote;


import android.app.NotificationManager;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.RemoteMessage;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;

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
        LocalStorage.getInstance().setValue(token,"fcm_token");
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
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1002, builder.build());

    }

    public static String getFCMToken(){
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
                    }
                });

        return token.toString();
    }
}
