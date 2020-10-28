package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.json.JSONArray;
import org.json.JSONException;

import Http_Conexion.HttpRequest;
import IP.cambiarIP;

public class ServicioCantidad extends Service {
    public ServicioCantidad() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        final String op = intent.getStringExtra("op");
        final String resuldato3 = intent.getStringExtra("cod");
        final String resuldato = intent.getStringExtra("tarea");
        System.out.println("MENSAJE " + resuldato3 + " " + resuldato);
        ejecutar(resuldato3, resuldato,op);

        return START_STICKY;
    }

    public void ejecutar(final String resuldato3, final String resuldato, final String op) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?cod=" + resuldato3 + "&tarea=" + resuldato+"&op="+op).body();
                    JSONArray array = new JSONArray(response);

                    String VaribleTOTA = array.getString(0);

                    Intent intent1 = new Intent("ServicioCantidad");
                    intent1.putExtra("ServicioCantidad", VaribleTOTA);
                    sendBroadcast(intent1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
