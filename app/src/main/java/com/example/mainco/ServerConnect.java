package com.example.mainco;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServerConnect extends Service {


    public ServerConnect() {
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



       Intent e = new Intent(getApplicationContext(), OperadorActivity.class);
       startActivity(e);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }
}
