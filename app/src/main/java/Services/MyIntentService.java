package Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.Nullable;

import com.Mainco.App.cambiarIP;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MyIntentService extends IntentService {
    private AsyncHttpClient cliente3;
    public MyIntentService() {
        super("ServicioActividad");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("ESTO SE EJECUTA");
        cliente3 = new AsyncHttpClient();
        while(true){
        try {

            Thread.sleep(1000);


            // LLENA EL SPINNER DE ACTIVIDAD
            String url = "http://" + cambiarIP.ip + "/validar/cantidad.php";
            cliente3.post(url, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    // Initiated the request
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        System.out.println("EL CODIGO ES : "+statusCode);
                        String llenarSpinner = new String(responseBody);

                        Intent intent1 = new Intent("llenarSpinner");
                        intent1.putExtra("llenarSpinner",llenarSpinner);
                        sendBroadcast(intent1);
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (statusCode > 401) {

                    }
                }
                @Override
                public void onRetry(int retryNo) {

                }
            });
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
        }
    }
}
