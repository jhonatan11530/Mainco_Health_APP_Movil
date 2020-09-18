package Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.Nullable;

import com.Mainco.App.OperadorActivity;
import com.Mainco.App.cambiarIP;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class ServicioMotivoParo extends IntentService {
    private AsyncHttpClient cliente4;
    public ServicioMotivoParo() {
        super("ServicioMotivoParo");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        cliente4 = new AsyncHttpClient();
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
}
