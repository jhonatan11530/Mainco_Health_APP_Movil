package Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.Mainco.App.MainActivity;

public class StartAppOnBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            System.out.println("SE BOOTEA");
        }
    }
}