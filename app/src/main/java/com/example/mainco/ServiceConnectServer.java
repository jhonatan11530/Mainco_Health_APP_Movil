package com.example.mainco;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ServiceConnectServer extends Service {
    Thread hilo = null;
    public ServiceConnectServer() {
    }

    @Override
    public IBinder onBind(Intent intencion) {

        return null;

    }

    @Override
    public void onCreate() {


        Toast.makeText(this,"SESIÒN INICIADA",
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {

        if(hilo == null || !hilo.isAlive()) {
            hilo = new Thread( new Runnable() {
                @Override
                public void run() {
                    Intent e = new Intent( getApplicationContext(), OperadorActivity.class );
                    startActivity( e );

                }
            } );
            hilo.start();
        }

        return START_STICKY;

    }

    @Override

    public void onDestroy() {


    }
}
