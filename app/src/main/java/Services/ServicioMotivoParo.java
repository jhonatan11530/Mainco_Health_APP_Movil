package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.Mainco.App.cambiarIP;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ServicioMotivoParo extends Service {
    private AsyncHttpClient cliente4;
    public ServicioMotivoParo() {
        cliente4 = new AsyncHttpClient();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);

        ejecutar();

        return START_STICKY;
    }

public void ejecutar(){
    String url = "http://" + cambiarIP.ip + "/validar/motivo.php";
    cliente4.post(url, new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if (statusCode == 200) {
                String filtrardescanso = new String(responseBody);

                Intent intent1 = new Intent("llenarParo");
                intent1.putExtra("filtrardescanso",filtrardescanso);
                sendBroadcast(intent1);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (statusCode > 201) {
                Intent llenarspinner = new Intent(ServicioMotivoParo.this, ServicioMotivoParo.class);
                startService(llenarspinner);
            }
        }
        @Override
        public void onRetry(int retryNo) {
        }
    });

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
