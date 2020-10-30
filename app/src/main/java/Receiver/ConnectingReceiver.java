package Receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.List;

import TSS.TTS;

public class ConnectingReceiver extends BroadcastReceiver {
    final WifiConfiguration conf = new WifiConfiguration();
    String networkSSID = "WIFIMainco";
    String networkPass = "A125277935";

    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {

            conf.SSID = "\"" + networkSSID + "\"";
            conf.preSharedKey = "\"" + networkPass + "\"";


            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            if ((!wifiManager.isWifiEnabled())) {
                wifiManager.setWifiEnabled(true);
            }

            wifiManager.addNetwork(conf);

            if (isOnline(context)) {
                Toast.makeText(context.getApplicationContext(), "Conectando a Mainco.", Toast.LENGTH_LONG).show();
                System.out.println("Conectivity ");
            } else {

                @SuppressLint("MissingPermission") List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                for (WifiConfiguration i : list) {
                    if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                        wifiManager.enableNetwork(i.networkId, true);
                        wifiManager.reconnect();

                        break;
                    }
                }
                System.out.println("Conectivity Failure !!! ");
                Toast.makeText(context.getApplicationContext(), "Se perdio la conexion. intentando de nuevo", Toast.LENGTH_LONG).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}