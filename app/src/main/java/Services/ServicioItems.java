package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import IP.cambiarIP;

public class ServicioItems extends Service {

    public ServicioItems() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        ejecutar();
        return START_STICKY;
    }

    public void ejecutar() {
        AsyncHttpClient service = new AsyncHttpClient();

        // CONFIGURACION HTTP SERVICE
        service.setConnectTimeout(28800000);
        service.setResponseTimeout(10000);

        // LLENA EL SPINNER DE ACTIVIDAD
        String url = "http://" + cambiarIP.ip + "/validar/llenarSpinner/NumeroItem.php";
        service.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String llenarOps = new String(responseBody);

                    Intent intent1 = new Intent("llenarItem");
                    intent1.putExtra("llenarOps", llenarOps);
                    sendBroadcast(intent1);

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode > 201) {
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
