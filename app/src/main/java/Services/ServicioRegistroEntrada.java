package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.App.MainActivity;


public class ServicioRegistroEntrada extends Service {
    private Thread workerThread = null;
    public ServicioRegistroEntrada() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // startService(new Intent(MainActivity.this, ServicioRegistroEntrada.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        if(workerThread == null || !workerThread.isAlive()){
            workerThread = new Thread(new Runnable() {
                @Override
                public void run() {



                }
            });
            workerThread.start();
        }
        return START_STICKY;

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
