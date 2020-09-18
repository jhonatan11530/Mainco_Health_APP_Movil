package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.Mainco.App.cambiarIP;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ServicioActividad extends Service {

    public ServicioActividad() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("MENSAJE EL INTENTSERVICE SE ESTA CREANDO");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
