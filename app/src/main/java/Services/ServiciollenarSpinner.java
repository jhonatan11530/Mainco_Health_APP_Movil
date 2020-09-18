package Services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Path;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.Mainco.App.OperadorActivity;
import com.Mainco.App.cambiarIP;
import com.Mainco.App.cantidades;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ServiciollenarSpinner extends IntentService {
    private AsyncHttpClient cliente3;
    public ServiciollenarSpinner() {
        super("ServiciollenarSpinner");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        cliente3 = new AsyncHttpClient();

        String url = "http://" + cambiarIP.ip + "/validar/cantidad.php";
        cliente3.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String cargarSpinner = new String(responseBody);

                    Intent intent1 = new Intent("llenarSpinner");
                    intent1.putExtra("hola",cargarSpinner);
                    sendBroadcast(intent1);
                }
                if (statusCode > 201) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
            @Override
            public void onRetry(int retryNo) {

            }
        });

    }

}
