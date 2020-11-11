package com.Mainco.App;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import Receiver.BatteryReceiver;
import Receiver.ConnectingReceiver;
import Receiver.StartAppOnBoot;

public class MainActivity extends AppCompatActivity {

    // BOOT
    private StartAppOnBoot mBaootReceiver = new StartAppOnBoot();
    private IntentFilter mIntentFilterBoot = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);

    // BATERY
    private BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    private IntentFilter mIntentFilterBatery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    // CONNECTING
    private final ConnectingReceiver connectingReceiver = new ConnectingReceiver();
    private final IntentFilter mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(mBaootReceiver, mIntentFilterBoot);
        registerReceiver(mBatteryReceiver, mIntentFilterBatery);
        registerReceiver(connectingReceiver, mIntentFilter);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int DURACION_SPLASH = 3000;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }, DURACION_SPLASH);
    }


}