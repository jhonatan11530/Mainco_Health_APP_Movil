package com.Mainco.App;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TSS textToSpeech = new TSS();
        textToSpeech.init(context);

        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            // Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = level * 100 / scale;
            // MUESTRA EL PORCENTAJE DE LA BACTERIA
            Toast.makeText(context.getApplicationContext(), "el porcentaje es "+percentage, Toast.LENGTH_SHORT).show();
           // percentageLabel.setText(percentage + "%");


            // Image
            Resources res = context.getResources();

            if (percentage >= 90) {
                textToSpeech.speak("LA BATERIA ESTA AL 90 PORCIENTO");
               // batteryImage.setImageDrawable(res.getDrawable(R.drawable.b100));

            } else if (90 > percentage && percentage >= 65) {
                textToSpeech.speak("LA BATERIA ESTA AL 90 PORCIENTO");
               // batteryImage.setImageDrawable(res.getDrawable(R.drawable.b75));

            } else if (65 > percentage && percentage >= 40) {
                textToSpeech.speak("LA BATERIA ESTA AL 90 PORCIENTO");
               // batteryImage.setImageDrawable(res.getDrawable(R.drawable.b50));

            } else if (40 > percentage && percentage >= 15) {
                textToSpeech.speak("LA BATERIA ESTA AL 15 PORCIENTO");
               // batteryImage.setImageDrawable(res.getDrawable(R.drawable.b25));

            } else {
               // batteryImage.setImageDrawable(res.getDrawable(R.drawable.b0));

            }

        }
    }
}
