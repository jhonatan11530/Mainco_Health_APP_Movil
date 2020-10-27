package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import IP.cambiarIP;
import cz.msebera.android.httpclient.Header;

public class ServicioActividad extends Service {

    public ServicioActividad() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        ejecutar();
        return START_STICKY;
    }

    public void ejecutar() {
        AsyncHttpClient service = new AsyncHttpClient();
        // LLENA EL SPINNER DE ACTIVIDAD
        String url = "http://" + cambiarIP.ip + "/validar/llenarSpinner/tarea.php";
        service.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    System.out.println("EL CODIGO ES : " + statusCode);
                    String llenarSpinner = new String(responseBody);

                    Intent intent1 = new Intent("llenarSpinner");
                    intent1.putExtra("llenarSpinner", llenarSpinner);
                    sendBroadcast(intent1);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode > 401) {
                    ejecutar();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                ejecutar();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
