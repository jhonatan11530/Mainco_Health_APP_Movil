package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.App.HttpRequest;
import com.example.App.cambiarIP;


public class ServicioRegistroSalida extends Service {
    private Thread workerThread = null;

    public ServicioRegistroSalida() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        final String resuldato3 = intent.getStringExtra("resuldato3");
        final String nombretarea = intent.getStringExtra("tarea");
        final String items = intent.getStringExtra("items");
        final String end = intent.getStringExtra("end");
        final String id = intent.getStringExtra("id");
        final String volumen = intent.getStringExtra("volumen");
        final String fechas = intent.getStringExtra("fechas");
        final String horas = intent.getStringExtra("horas");
        final String error = intent.getStringExtra("error");
        final String falla = intent.getStringExtra("falla");
        if (workerThread == null || !workerThread.isAlive()) {
            workerThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    HttpRequest.get("http://" + cambiarIP.ip + "/validar/canbiarAucOP.php?op=" + items.toString() + "&item=" + resuldato3.toString() + "&cantidad=" + end).body();

                    HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidadmodifi.php?op=" + resuldato3.toString() + "&tarea=" + nombretarea + "&totales=" + end).body();

                    HttpRequest.get("http://" + cambiarIP.ip + "/validar/actualizaSalida.php?id=" + id.toString() + "&cantidad=" + volumen + "&Ffinal=" + fechas + "&Hfinal=" + horas + "&motivo=" + error + "&conforme=" + falla + "&tarea=" + nombretarea + "&op=" + items.toString()).body();

                    HttpRequest.get("http://" + cambiarIP.ip + "/validar/nuevoRegistro.php?id=" + id.toString()).body();


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
