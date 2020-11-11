package Receiver;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.BatteryManager;
import android.widget.TextView;
import android.widget.Toast;


public class BatteryReceiver extends BroadcastReceiver {
    @SuppressWarnings("StatementWithEmptyBody")
    @SuppressLint("WrongConstant")
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            // Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = level * 100 / scale;

            if (25 >= percentage && percentage >= 0) {
                // MUESTRA EL PORCENTAJE DE LA BACTERIA

                Toast toast = Toast.makeText(context.getApplicationContext(), "POR FAVOR CONECTE EL CARGADOR", Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.RED);
                TextView text = toast.getView().findViewById(android.R.id.message);
                text.setTextColor(Color.WHITE);
                text.setTextSize(20);
                toast.show();

            }

        }
    }
}


