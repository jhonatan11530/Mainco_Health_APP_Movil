package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.App.OperadorActivity;

public class ServicioLogin extends Service {
    private Thread workerThread = null;

    public ServicioLogin() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("SE CREO EL SERVICIO");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (workerThread == null || !workerThread.isAlive()) {
            workerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent e = new Intent(getApplicationContext(), OperadorActivity.class);
                    startActivity(e);


                }
            });
            workerThread.start();
        }
        return START_STICKY;

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the servi  ce.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
