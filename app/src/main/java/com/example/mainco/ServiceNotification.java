package com.example.mainco;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ServiceNotification extends Service {
    public static final int NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    public ServiceNotification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle("usted acaba de iniciar sesión");
        builder.setContentText("el usuario ha entrado al sistema exitosamente");
        builder.setSmallIcon(R.drawable.mainco);
        Notification notification = builder.build();

        NotificationManagerCompat notificationManagerCompat;
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);

        builder.setVisibility( NotificationCompat. VISIBILITY_PUBLIC ) ;


        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }
}
