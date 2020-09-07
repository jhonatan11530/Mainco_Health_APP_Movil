package Services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.widget.Toast;

import com.Mainco.App.LoginActivity;

import java.util.List;

public class ServicioConnecting extends Service {
    final WifiConfiguration conf = new WifiConfiguration();
    String networkSSID = "WIFIMainco";
    String networkPass = "A125277935";
    public ServicioConnecting() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);

        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\"" + networkPass + "\"";


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if ((!wifiManager.isWifiEnabled())) {
            Toast.makeText(ServicioConnecting.this, "Conectando a Mainco.", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);

        }

        wifiManager.addNetwork(conf);
        @SuppressLint("MissingPermission") List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {

                wifiManager.enableNetwork(i.networkId, true);


                break;
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
