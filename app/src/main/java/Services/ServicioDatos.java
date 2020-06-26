package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;




public class ServicioDatos extends Service {
    private Thread workerThread = null;
    public ServicioDatos() {
        startService(new Intent(this, ServicioDatos.class));

    }

    @Override
    public void onCreate() {
        super.onCreate();
       //
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
