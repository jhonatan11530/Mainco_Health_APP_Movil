package Services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@SuppressWarnings("BusyWait")
public class ServicioContador extends Service {
    private int i, minuto, hora;
    private boolean running;

    public ServicioContador() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        running = true;
        Thread hilo = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    i = 0;
                    minuto = 0;
                    hora = 0;


                    while (running) {
                        i++;
                        Thread.sleep(1000);

                        if (i == 60) {
                            i = 0;
                            minuto++;

                        }

                        if (minuto == 59) {
                            minuto = 0;
                            hora++;
                        }

                        if (hora == 12) {
                            hora = 0;

                        }

                        NumberFormat formatter = new DecimalFormat("00");
                        final String segund = formatter.format(i); // ----> 01

                        NumberFormat formatterMinutes = new DecimalFormat("00");
                        final String minute = formatterMinutes.format(minuto); // ----> 01

                        NumberFormat formatterHoues = new DecimalFormat("00");
                        final String hours = formatterHoues.format(hora); // ----> 01


                        Intent intent1 = new Intent("ServicioContador");
                        intent1.putExtra("hours", hours);
                        intent1.putExtra("minute", minute);
                        intent1.putExtra("segund", segund);
                        sendBroadcast(intent1);


                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();


                }


            }


        });
        hilo.start();
        hilo.setPriority(Thread.NORM_PRIORITY);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        running = false;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}