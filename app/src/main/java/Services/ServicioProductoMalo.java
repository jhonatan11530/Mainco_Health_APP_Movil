package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import IP.cambiarIP;
import cz.msebera.android.httpclient.Header;

public class ServicioProductoMalo extends Service {

    public ServicioProductoMalo() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        motivofalla();
        return START_STICKY;
    }

    public void motivofalla() {
        AsyncHttpClient service = new AsyncHttpClient();
        String url = "http://" + cambiarIP.ip + "/validar/llenarSpinner/MotivoProductoDefectuoso.php";
        service.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String cargarmotivo = new String(responseBody);

                    Intent intent1 = new Intent("MALO");
                    intent1.putExtra("malo", cargarmotivo);
                    sendBroadcast(intent1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                motivofalla();
            }

            @Override
            public void onRetry(int retryNo) {
                motivofalla();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
