package Services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.Mainco.App.LoginActivity;

import java.util.List;

public class ServicioError extends Service {
    private Thread workerThread = null;
    public ServicioError() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        if (workerThread == null || !workerThread.isAlive()) {
            workerThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true){
                    try {
                        Thread.sleep(1000);

                    if (Helper.isAppRunning(ServicioError.this, "com.MaincoHealthCare.App")) {
                        System.out.println("SI SE EJECUTA");
                    } else {
                        System.out.println("NO SE EJECUTA");
                    }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    }
                }

            });
            workerThread.start();
        }
        return START_STICKY;
    }

    public static class Helper {

        public static boolean isAppRunning(final Context context, final String packageName) {
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
            for(int i = 0; i < procInfos.size(); i++)
            {
                if (procInfos.get(i).processName.equals(packageName)) {
                        return true;
                }

            }
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("NO SE EJECUTA");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
