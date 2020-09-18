package Services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ServicioConnecting extends IntentService {
    final WifiConfiguration conf = new WifiConfiguration();
    String networkSSID = "WIFIMainco";
    String networkPass = "A125277935";
    public ServicioConnecting() {
        super("ServicioConnecting");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
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
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();

                break;
            }
        }
    }
}
