package Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.Mainco.App.HttpRequest;
import com.Mainco.App.cambiarIP;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ServicioCantidad extends IntentService {

    public ServicioCantidad() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final  String resuldato3 = intent.getStringExtra("OP");
        final  String resuldato = intent.getStringExtra("tarea");
        new Thread(new Runnable() {
            @Override
            public void run() {


                System.out.println("MENSAJE EL SERVICIO CANTIDAD SE ESTA CREANDO "+resuldato3.toString()+" "+resuldato.toString());
                try {
                    String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?op=" + resuldato3.toString() + "&tarea=" + resuldato.toString()).body();
                    JSONArray array = new JSONArray(response);

                    String VaribleTOTA = array.getString(0);

                    Intent intent1 = new Intent("MostrarCantidadReal");
                    intent1.putExtra("MostrarCantidadReal", VaribleTOTA);
                    sendBroadcast(intent1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
