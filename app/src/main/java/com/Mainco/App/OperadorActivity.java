package com.Mainco.App;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Services.MyIntentService;
import Services.ServicioItems;
import Services.ServicioActividad;
import Services.ServicioMotivoParo;
import Services.ServicioRegistroSalida;
import cz.msebera.android.httpclient.Header;

@SuppressWarnings("ALL")
public class OperadorActivity extends AppCompatActivity implements LifecycleObserver {

    private EditText id, cantidad, paro, fallas, op, codemotivo;
    private String falla, error, VaribleTOTA, NOMBRE;
    private TextView motivo, MOSTRAR, texto, resultados;
    public static Spinner resuldato, resuldato2, resuldato4, resuldato3;
    private Button go,stop,desbloquear,positivo,neutrar,registroTIME,salidaTIME,validarinfo,cantidadund,Validar;
    private int minuto, segundo, horas, i, hora, cantidadpro,cantidadotra, volumencan, total, volumen;
    private final ArrayList<cantidadfallas> dato4 = new ArrayList<>();
    private ArrayList<cantidades> dato3 = new ArrayList<>();
    private ArrayList<motivoparo> dato2 = new ArrayList<>();
    private ArrayList<OPS> dato = new ArrayList<>();

    EditText edit, digito;
    View tiempo1, adelanto;
    AlertDialog.Builder registros;
    Date date;
    SimpleDateFormat hourFormat;
    SimpleDateFormat dateFormat;
    RelativeLayout production;
    private AsyncHttpClient cliente, cliente1, cliente2, cliente3, cliente4, cliente5, cliente6;
    public Thread hilo;
    private Thread workerThread = null;
    TSS textToSpeech=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operador);

        // CONEXIONES TOTALES
        cliente = new AsyncHttpClient();
        cliente1 = new AsyncHttpClient();
        cliente2 = new AsyncHttpClient();
        cliente3 = new AsyncHttpClient();
        cliente4 = new AsyncHttpClient();
        cliente5 = new AsyncHttpClient();
        cliente6 = new AsyncHttpClient();



       // llenarOps();
        IntentFilter llenarSpinner = new IntentFilter();
        llenarSpinner.addAction("llenarSpinner");
        registerReceiver(LlenarSpinner,llenarSpinner);

        IntentFilter llenarItem = new IntentFilter();
        llenarItem.addAction("llenarItem");
        registerReceiver(LlenarItem,llenarItem);

        IntentFilter llenarParo = new IntentFilter();
        llenarParo.addAction("llenarParo");
        registerReceiver(LlenarMotivoParo,llenarParo);

        Intent llenarspinner = new Intent(OperadorActivity.this, MyIntentService.class);
        startService(llenarspinner);

        Intent llenaritem = new Intent(OperadorActivity.this, ServicioItems.class);
        startService(llenaritem);



        textToSpeech = new TSS();
        textToSpeech.init(this);

        if(savedInstanceState!=null){
            id.setText(savedInstanceState.getInt("ID"));
            op.setText(savedInstanceState.getString("OP"));
        }


        production = findViewById(R.id.principal);

        resuldato = findViewById(R.id.spinner1);

        resuldato2 = findViewById(R.id.spinner2);

        resuldato3 = findViewById(R.id.spinner);

        op = findViewById(R.id.OP);

        Validar = findViewById(R.id.Validar);

        cantidadund = findViewById(R.id.aplazar);

        desbloquear = findViewById(R.id.tiempo);

        registroTIME = findViewById(R.id.insertar);

        salidaTIME = findViewById(R.id.salida);

        id = findViewById(R.id.operador);

        resultados = findViewById(R.id.listar_operador);

       getLifecycle().addObserver(new Observardor());

        desbloquear.setEnabled(false);
        registroTIME.setEnabled(false);
        salidaTIME.setEnabled(false);
        cantidadund.setEnabled(false);

        Validar.setEnabled(false);

        op.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence limpio, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence escribio, int start, int before, int count) {
                if (op.getText().toString() != null) {
                    FiltrarOps();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() < 1) {

                    Intent actividad = new Intent(OperadorActivity.this, ServicioActividad.class);
                    startService(actividad);
                    //llama el intentService de item
                    Intent item = new Intent(OperadorActivity.this, ServicioItems.class);
                    startService(item);
                    Validar.setEnabled(false);
                }
            }
        });

    }
    private BroadcastReceiver LlenarSpinner = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // LLENA SPINNER ACTIVIDAD
            String cargarSpinner = intent.getStringExtra("llenarSpinner");
            cargarSpinner(new String(cargarSpinner));
            System.out.println("MENSAJE "+cargarSpinner);


        }
    };
    private BroadcastReceiver LlenarItem = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //LLENA SPINNER ITEM
            String llenarOps = intent.getStringExtra("llenarOps");
            cargarops(new String(llenarOps));
            System.out.println("MENSAJE "+llenarOps);
        }
    };
    private BroadcastReceiver LlenarMotivoParo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //LLENA SPINNER ITEM
            String filtrardescanso = intent.getStringExtra("filtrardescanso");
            filtrardescanso(new String(filtrardescanso));
            System.out.println("MENSAJE "+filtrardescanso);
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle ID_OP) {
        super.onSaveInstanceState(ID_OP);

        ID_OP.putString("ID",id.getText().toString());
        ID_OP.putString("OP",op.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id.setText(savedInstanceState.getInt("ID"));
        op.setText(savedInstanceState.getString("OP"));
    }

    public class Observardor implements LifecycleObserver {

        private String LOG_TAG = "Observardor";

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            Log.i(LOG_TAG, "onResume");

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            Log.i(LOG_TAG, "onPause");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
            Log.i(LOG_TAG, "onCreate");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            Log.i(LOG_TAG, "onStart");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop() {
            Log.i(LOG_TAG, "onStop");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {

            // imprime fecha
             dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
             date = new Date();
            //imprime hora
              hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            final String fecha = dateFormat.format(date);
            final String hora = hourFormat.format(date);
            // se obtiene el name-model,ip,fecha,hora
            Log.i(LOG_TAG, "onDestroy "+id.getText().toString()+" "+op.getText().toString()+" "+obtenerNombreDeDispositivo()+" "+getIP()+" "+hora+" "+fecha);
            new Thread(new Runnable() {
                @Override
                public void run() {
                HttpRequest.get("http://" + cambiarIP.ip + "/validar/RegistroERROR.php?id="+id.getText().toString()+"&op="+op.getText().toString()+"&nombre="+obtenerNombreDeDispositivo()+"&ip="+getIP()+"&hora="+hora.toString()+"&fecha="+fecha.toString()).body();
                }
            }).start();
            Thread.interrupted();

            finish();
            Intent e = new Intent(getApplicationContext(), OperadorActivity.class);
            startActivity(e);

        }

    }

    public static String getIP(){
        List<InetAddress> addrs;
        String address = "";
        try{
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for(NetworkInterface intf : interfaces){
                addrs = Collections.list(intf.getInetAddresses());
                for(InetAddress addr : addrs){
                    if(!addr.isLoopbackAddress() && addr instanceof Inet4Address){
                        address = addr.getHostAddress().toUpperCase(new Locale("es", "MX"));
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Ex getting IP value " + e.getMessage());

        }
        return address;
    }

    public String obtenerNombreDeDispositivo() {
        String fabricante = Build.MANUFACTURER;
        String modelo = Build.MODEL;
        if (modelo.startsWith(fabricante)) {
            return primeraLetraMayuscula(modelo);
        } else {
            return primeraLetraMayuscula(fabricante) + " " + modelo;
        }
    }

    private String primeraLetraMayuscula(String cadena) {
        if (cadena == null || cadena.length() == 0) {
            return "";
        }
        char primeraLetra = cadena.charAt(0);
        if (Character.isUpperCase(primeraLetra)) {
            return cadena;
        } else {
            return Character.toUpperCase(primeraLetra) + cadena.substring(1);
        }
    }

    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.atras:

                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);

                String titleText = "SALIR";

                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#58B4E8"));

                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                builder.setTitle(ssBuilder);

                builder.setMessage("¿ ESTAS SEGURO QUE DESEA CERRAR SESIÒN ?");

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        ProgressDialog pd = new ProgressDialog(OperadorActivity.this);

                        pd.setTitle("CERRANDO SESION");

                        pd.setMessage("Porfavor espere");
                        pd.setCanceledOnTouchOutside(false);

                        pd.show();


                        Intent e = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(e);


                    }
                });

                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "LA SESIÒN SE MANTENDRA ACTIVA", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
                break;

            case R.id.ayuda:
                Intent e = new Intent(getApplicationContext(), options.class);
                startActivity(e);

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void filtraritem() {

        String url = "http://" + cambiarIP.ip + "/validar/cantidadfiltre.php?op=" + resuldato3.getSelectedItem().toString(); // SE DEBE CAMBIAR
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    itemfiltre(new String(responseBody));

                }
                if (statusCode > 201) {
                    filtraritem();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                filtraritem();
            }

            @Override
            public void onRetry(int retryNo) {
                filtraritem();
            }
        });
    }

    public void itemfiltre(String itemfiltre) {
        ArrayList<cantidades> dato3 = new ArrayList<>();
        try {
            JSONArray objecto = new JSONArray(itemfiltre);
            for (int i = 0; i < objecto.length(); i++) {
                cantidades a = new cantidades();
                a.setTarea(objecto.getJSONObject(i).getString("tarea"));
                dato3.add(a);
            }
            ArrayAdapter<cantidades> a = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dato3);
            resuldato.setAdapter(a);
            Validar.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void cargarops(String cargarops) {
        ArrayList<OPS> dato = new ArrayList<>();
        try {
            JSONArray objecto = new JSONArray(cargarops);
            for (int i = 0; i < objecto.length(); i++) {
                OPS a = new OPS();

                a.setOps(objecto.getJSONObject(i).getString("cod_producto"));
                dato.add(a);
            }
            ArrayAdapter<OPS> a = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dato);
            resuldato3.setAdapter(a);


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void FiltrarOps() {

        String url = "http://" + cambiarIP.ip + "/validar/FiltroOPS.php?op=" + op.getText().toString();
        cliente2.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    filtroOPS(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Intent llenarspinner = new Intent(OperadorActivity.this, ServicioActividad.class);
                startService(llenarspinner);
            }

            @Override
            public void onRetry(int retryNo) {
                Intent llenarspinner = new Intent(OperadorActivity.this, ServicioActividad.class);
                startService(llenarspinner);
            }
        });
    }

    public void filtroOPS(String filtroOPS) {
        ArrayList<OPS> dato = new ArrayList<>();
        try {
            JSONArray objecto = new JSONArray(filtroOPS);
            for (int i = 0; i < objecto.length(); i++) {
                OPS a = new OPS();

                a.setOps(objecto.getJSONObject(i).getString("cod_producto"));
                dato.add(a);
            }
            ArrayAdapter<OPS> a = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dato);
            resuldato3.setAdapter(a);

            filtraritem();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void cargarSpinner(String cargarSpinner) {
        ArrayList<cantidades> dato3 = new ArrayList<>();
        try {
            JSONArray objecto = new JSONArray(cargarSpinner);
            for (int i = 0; i < objecto.length(); i++) {
                cantidades a = new cantidades();
                a.setTarea(objecto.getJSONObject(i).getString("tarea"));
                dato3.add(a);
            }
            ArrayAdapter<cantidades> a = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dato3);
            resuldato.setAdapter(a);


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void llenardescanso() {

    }

    public void filtrardescanso(String filtrardescanso) {
        ArrayList<motivoparo> dato2 = new ArrayList<>();
        try {
            JSONArray objecto = new JSONArray(filtrardescanso);
            for (int i = 0; i < objecto.length(); i++) {
                motivoparo b = new motivoparo();
                b.setParo(objecto.getJSONObject(i).getString("paro"));
                dato2.add(b);
            }
            ArrayAdapter<motivoparo> a = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dato2);
            resuldato2.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void llenardescansoMotivo() {
        String url = "http://" + cambiarIP.ip + "/validar/motivofiltro.php?motivo=" + codemotivo.getText().toString();
        cliente5.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    filtrardescansoMotivo(new String(responseBody));
                }
                if (statusCode > 201) {
                    llenardescansoMotivo();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                llenardescansoMotivo();
            }
            @Override
            public void onRetry(int retryNo) {
                llenardescansoMotivo();
            }
        });

    }

    public void filtrardescansoMotivo(String filtrardescansoMotivo) {
        ArrayList<motivoparo> dato2 = new ArrayList<>();
        try {
            JSONArray objecto = new JSONArray(filtrardescansoMotivo);
            for (int i = 0; i < objecto.length(); i++) {
                motivoparo b = new motivoparo();
                b.setParo(objecto.getJSONObject(i).getString("paro"));
                dato2.add(b);
            }
            ArrayAdapter<motivoparo> a = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dato2);
            resuldato2.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void motivofalla() {
        String url = "http://" + cambiarIP.ip + "/validar/motivocantidad.php";
        cliente6.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarmotivo(new String(responseBody));
                }
                if (statusCode > 201) {
                    motivofalla();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                motivofalla();
            }

            @Override
            public void onRetry(int retryNo) {
                motivofalla();
            }
        });
    }

    public void cargarmotivo(String cargarmotivo) {
        ArrayList<cantidadfallas> dato4 = new ArrayList<>();
        try {
            JSONArray objecto = new JSONArray(cargarmotivo);
            for (int i = 0; i < objecto.length(); i++) {
                cantidadfallas c = new cantidadfallas();
                c.setFallas(objecto.getJSONObject(i).getString("fallas"));
                dato4.add(c);
            }

            ArrayAdapter<cantidadfallas> a = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dato4);
            resuldato4.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void validaractividad(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    String responses = HttpRequest.get("http://" + cambiarIP.ip + "/validar/validarcantidad.php?numero=" + resuldato3.getSelectedItem().toString()+"&tarea="+resuldato.getSelectedItem().toString()).body();
                    try {
                        JSONArray objecto = new JSONArray(responses);
                        int variable = Integer.parseInt(objecto.getString(0));

                        if (variable == 0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    desbloquear.setEnabled(false);
                                    registroTIME.setEnabled(false);
                                    salidaTIME.setEnabled(false);
                                    cantidadund.setEnabled(false);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void validar(){
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);

            String responses = HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidadedits.php?cod=" + resuldato3.getSelectedItem().toString()+"&op="+op.getText().toString()).body();
        try {
            JSONArray objecto = new JSONArray(responses);
            int variable = Integer.parseInt(objecto.getString(0));

            if (variable == 0){

                textToSpeech.speak("LA ORDEN DE PRODU SION ESTA CERRADA");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                builder.setTitle("LA ORDEN DE PRODUCCION SE CERRO");
                builder.setIcon(R.drawable.finish_op);
                builder.setMessage("NO PODRA REGISTRAR ACTIVIDADES O CANTIDADES");

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpRequest.get("http://" + cambiarIP.ip + "/validar/consolidado.php?op=" + op.getText().toString()).body();
                                HttpRequest.get("http://" + cambiarIP.ip + "/validar/limpiar.php?id=" + resuldato3.getSelectedItem().toString()).body();

                            }
                        }).start();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);

                        desbloquear.setEnabled(false);
                        registroTIME.setEnabled(false);
                        salidaTIME.setEnabled(false);
                        cantidadund.setEnabled(false);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }).start();
    }
    
    public void operador(View v) {

        if (id.getText().toString().length() == 0 && op.getText().toString().length() == 0) {

            id.setError("ID ES REQUERIDO !");
            op.setError("O.P ES REQUERIDO !");
            textToSpeech.speak("DEBE INGRESAR SU CODIGO Y EL NUMERO DE O P");

        } else if (id.getText().toString().length() > 0 && op.getText().toString().length() == 0) {

            op.setError("O.P ES REQUERIDO !");

        } else if (id.getText().toString().length() == 0 && op.getText().toString().length() > 0) {

            id.setError("ID ES REQUERIDO !");
        } else {
            validar();
            new Task().execute();


            hilo = new Thread(new Runnable() {
                @Override
                public void run() {




                    try {


                        String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/operador.php?id=" + id.getText().toString()).body();
                        JSONArray objecto = new JSONArray(response);


                        if (response.length() > 0) {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    registroTIME.setEnabled(true);
                                    registroTIME.setBackgroundColor(Color.parseColor("#B2C900"));

                                    salidaTIME.setEnabled(false);
                                    cantidadund.setEnabled(false);
                                    desbloquear.setEnabled(false);

                                }
                            });
                            NOMBRE = objecto.getString(0);

                            resultados.setText("OPERADOR : " + objecto.getString(0));


                        }
                        if (response.length() == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                    builder.setTitle("EL OPERADOR NO EXISTE");
                                    builder.setIcon(R.drawable.informacion);
                                    builder.setMessage("EL OPERARIO NO ESTA REGISTRADO ");

                                    builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                    alert.setCanceledOnTouchOutside(false);
                                }
                            });
                        }

                    } catch (Exception e) {
                        // TODO: handle exception

                    }
                }
            });
            hilo.start();


        }

    }


    public void verificar() {
        final String Nitem = resuldato3.getSelectedItem().toString();
        final String nombretarea = resuldato.getSelectedItem().toString();
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                String cero = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?op=" + Nitem.toString() + "&tarea=" + nombretarea).body();
                try {
                    JSONArray nada = new JSONArray(cero);
                    int vacio = Integer.parseInt(nada.getString(0));

                    if (vacio > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                builder.setIcon(R.drawable.informacion);
                                builder.setTitle("REGISTRO LA ACTIVIDAD O TAREA");
                                builder.setMessage("USTED REGISTRO SU PRODUCCIDO");

                                builder.setPositiveButton("CONTINUAR ACTIVIDAD", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        registroTIME.setBackgroundColor(Color.parseColor("#B2C900"));
                                        registroTIME.setEnabled(true);
                                        hilo = new Thread(new Runnable() {
                                            @Override
                                            public void run() {

                                                HttpRequest.get("http://" + cambiarIP.ip + "/validar/nuevoRegistro.php?id=" + id.getText().toString()).body();
                                            }
                                        });
                                        hilo.start();
                                    }
                                });
                                builder.create();
                                AlertDialog alert = builder.create();
                                alert.show();
                                alert.setCanceledOnTouchOutside(false);

                            }
                        });


                    }

                    if (vacio == 0) {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ((TextView) resuldato.getSelectedView()).setTextColor(getResources().getColor(R.color.RED));

                                registroTIME.setEnabled(false);
                                salidaTIME.setEnabled(false);
                                cantidadund.setEnabled(false);
                                desbloquear.setEnabled(false);



                                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                builder.setIcon(R.drawable.finish_op);
                                builder.setTitle("FINALIZO LA ACTIVIDAD O TAREA");
                                builder.setMessage("YA TERMINO LA TAREA DEBERA REALIZAR OTRA TAREA");

                                builder.setNegativeButton("CONTINUAR CON OTRA ACTIVIDAD", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getApplicationContext(), "LA ACTIVIDAD FINALIZO", Toast.LENGTH_SHORT).show();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                registroTIME.setEnabled(true);
                                                registroTIME.setBackgroundColor(Color.parseColor("#B2C900"));
                                            }
                                        });
                                        hilo = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                HttpRequest.get("http://" + cambiarIP.ip + "/validar/nuevoRegistro.php?id=" + id.getText().toString()).body();
                                            }
                                        });
                                        hilo.start();
                                    }
                                });

                                builder.create();
                                AlertDialog alert = builder.create();
                                alert.show();
                                alert.setCanceledOnTouchOutside(false);


                            }
                        });


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        hilo.start();
        hilo.interrupted();

    }

    public void hora(View v) {

        registros = new AlertDialog.Builder(OperadorActivity.this);
        tiempo1 = getLayoutInflater().inflate(R.layout.motivo_paro, null);

        Intent actividad = new Intent(OperadorActivity.this, ServicioMotivoParo.class);
        startService(actividad);

        id = findViewById(R.id.operador);
        paro = tiempo1.findViewById(R.id.paro);
        MOSTRAR = tiempo1.findViewById(R.id.MOSTRAR);
        texto = tiempo1.findViewById(R.id.textos);
        stop = tiempo1.findViewById(R.id.stop);
        go = tiempo1.findViewById(R.id.go);
        validarinfo = tiempo1.findViewById(R.id.validarinfo);
        codemotivo = tiempo1.findViewById(R.id.codemotivo);

        motivo = tiempo1.findViewById(R.id.MOTIVO);
        resuldato2 = tiempo1.findViewById(R.id.spinner2);


        validarinfo.setEnabled(false);
        codemotivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (codemotivo.getText().toString() != null) {
                    llenardescansoMotivo();
                    validarinfo.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() < 1) {
                    Intent actividad = new Intent(OperadorActivity.this, ServicioActividad.class);
                    startService(actividad);

                    validarinfo.setEnabled(false);
                }
            }
        });
        //botones

        stop.setEnabled(false);
        go.setEnabled(false);

        //TEXTVIEW Y SPINNER
        motivo.setVisibility(View.VISIBLE);
        resuldato2.setVisibility(View.VISIBLE);
  validarinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarinfo.setEnabled(false);
                    resuldato2.setEnabled(false);
                    codemotivo.setEnabled(false);

                     go.setEnabled(true);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String mostrardatos = resuldato2.getSelectedItem().toString();
                            Toast.makeText(getApplicationContext(), "USTED SELECCIÓNO " + mostrardatos, Toast.LENGTH_SHORT).show();
                        }
                    });
                    textToSpeech.speak(resuldato2.getSelectedItem().toString());

            }
        });



        registros.setPositiveButton("REGISTRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });



        registros.setView(tiempo1);
        registros.create();
        final AlertDialog alert = registros.create();
        alert.show();

        alert.setCanceledOnTouchOutside(false);


        desbloquear = alert.getButton(AlertDialog.BUTTON_POSITIVE);


        desbloquear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                desbloquear.setEnabled(false);

                final String prueba = resuldato2.getSelectedItem().toString();

                // imprime fecha
                dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                date = new Date();

                //imprime hora
                hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                final String horas = hourFormat.format(date);
                final String fechas = dateFormat.format(date);

                hilo = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        HttpRequest.get("http://" + cambiarIP.ip + "/validar/RegistrarMotivo.php?op=" + op.getText().toString() + "&id=" + id.getText().toString() + "&paro=" + paro.getText().toString() + "&motivo=" + prueba + "&code=" + codemotivo.getText().toString() + "&fecha=" + fechas + "&hora=" + horas + "&tarea=" + resuldato.getSelectedItem().toString()).body();

                    }
                });
                hilo.start();
                hilo.interrupted();
                minuto = 0;
                hora = 0;

                Toast.makeText(getApplicationContext(), "LOS DATOS FUERON GUARDADOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();

                alert.dismiss();
                paro.setText("");
                MOSTRAR.setText("00:00:00");
            }
        });
        desbloquear.setEnabled(false);
    }
    public void go(View v) {


        go.setEnabled(false);

        hilo = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    i = 0;
                    minuto = 0;
                    hora = 0;


                    for (i = 0; i <= 60; i++) {
                        System.out.println(i);

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

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                MOSTRAR.setText(hours + ":" + minute + ":" + segund);

                            }
                        });

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();


                }


            }


        });
        hilo.start();
        hilo.setPriority(Thread.NORM_PRIORITY);
        if (hilo != null) {
            stop.setEnabled(true);


        }

    }

    public void stop(View v) {
        stop.setEnabled(false);
        go.setEnabled(false);
        desbloquear.setEnabled(true);
        if (paro != null) {

            motivo.setVisibility(View.VISIBLE);
            resuldato2.setVisibility(View.VISIBLE);
            hilo.interrupt();

        }

        paro.setText(MOSTRAR.getText().toString());

    }

    public void registrar(View v) {

        id = findViewById(R.id.operador);


        // imprime fecha
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date = new Date();

        //imprime hora
        hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        //almacena los datos en una cadena
        final String hora = hourFormat.format(date);

        final String fecha = dateFormat.format(date);

        AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
        builder.setTitle("DATOS INSERTADOS");
        builder.setMessage("PORFAVOR EMPIECE SU LABOR");
        edit = new EditText(this);
        edit.setEnabled(false);
        edit.setText(fecha);
        textToSpeech.speak("PUEDE EMPEZAR A REALIZAR SU LABOR");

        final String fechas = edit.getText().toString();
        final String Nop = resuldato3.getSelectedItem().toString();
        final String tarea = resuldato.getSelectedItem().toString();
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // TODO Auto-generated method stub
                hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            final String nombretarea = resuldato.getSelectedItem().toString();
                            String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?op=" + resuldato3.getSelectedItem().toString() + "&tarea=" + nombretarea).body();
                            JSONArray validar = new JSONArray(response);
                            int validator = Integer.parseInt(validar.getString(0));
                            System.out.println("EL VALOR " + validator);

                            if (validator == 0) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        salidaTIME.setEnabled(true);
                                        cantidadund.setEnabled(true);
                                        desbloquear.setEnabled(true);

                                        desbloquear.setBackgroundColor(Color.parseColor("#B2C900"));
                                        cantidadund.setBackgroundColor(Color.parseColor("#2196F3"));
                                        salidaTIME.setBackgroundColor(Color.parseColor("#2196F3"));
                                    }
                                });

                                System.out.println("ESTO SUCEDIO");
                                String responses = HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidadedits.php?cod=" + resuldato3.getSelectedItem().toString()+"&op="+op.getText().toString()).body();

                                JSONArray RESTARCANTIDAD = new JSONArray(responses);
                                HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidadmodifi.php?op=" + resuldato3.getSelectedItem().toString() + "&totales=" + RESTARCANTIDAD.getString(0)).body();

                                new Task().execute();


                            }
                            if (validator > 0) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        salidaTIME.setEnabled(true);
                                        cantidadund.setEnabled(true);
                                        desbloquear.setEnabled(true);

                                        desbloquear.setBackgroundColor(Color.parseColor("#B2C900"));
                                        cantidadund.setBackgroundColor(Color.parseColor("#2196F3"));
                                        salidaTIME.setBackgroundColor(Color.parseColor("#2196F3"));
                                    }
                                });

                                new Task().execute();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                        builder.setTitle("HAY CANTIDADES PENDIENTES");
                                        builder.setIcon(R.drawable.informacion);
                                        builder.setMessage("DEBE TERMINAR LAS CANTIDADES PENDIENTES");

                                        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.setCanceledOnTouchOutside(false);
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        HttpRequest.get("http://" + cambiarIP.ip + "/validar/actualizaEntrada.php?id=" + id.getText().toString() + "&Finicial=" + fechas + "&Hinicial=" + hora + "&op=" + op.getText().toString()).body();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                registroTIME.setEnabled(false);
                                registroTIME.setBackgroundColor(Color.parseColor("#919191"));
                            }
                        });


                    }
                });
                hilo.start();
                hilo.setPriority(Thread.MIN_PRIORITY);
                Thread.interrupted();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);

    }


    class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?op=" + resuldato3.getSelectedItem().toString() + "&tarea=" + resuldato.getSelectedItem().toString()).body();
                JSONArray array = new JSONArray(response);
                VaribleTOTA = array.getString(0);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return VaribleTOTA;

        }

        @Override
        protected void onPostExecute(final String VaribleTOTA) {
            super.onPostExecute(VaribleTOTA);
            int VaribleTOTAL = Integer.parseInt(VaribleTOTA);
            TextView MostrarCantidad = findViewById(R.id.MostrarCantidad);

            MostrarCantidad.setText("CANTIDAD EN O.P : " + VaribleTOTAL);

        }

    }

    public void salida(View v) {


        motivofalla();
        id = findViewById(R.id.operador);
        View view = getLayoutInflater().inflate(R.layout.cantidad_produccidas, null);
        resuldato4 = view.findViewById(R.id.spinner2);
        fallas = view.findViewById(R.id.fallas);
        cantidad = view.findViewById(R.id.digicantidad);


        final String tarea = resuldato.getSelectedItem().toString(); //**

        // imprime fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();

        //imprime hora
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        //almacena los datos en una cadena
        final String horafinal = hourFormat.format(date);
        final String fechafinal = dateFormat.format(date);

        final AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);


        EditText edit = new EditText(this);
        edit.setEnabled(false);
        edit.setText(fechafinal);

        EditText editt = new EditText(this);
        editt.setEnabled(false);
        editt.setText(horafinal);

        final String fechas = edit.getText().toString();
        final String horas = editt.getText().toString();
        builder.setPositiveButton("VERIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (fallas.getText().toString().length() == 0 && cantidad.getText().toString().length() == 0) {
                    fallas.setError("DEBE LLENARSE");
                    cantidad.setError("DEBE LLENARSE");
                } else {


                    volumen = Integer.parseInt(cantidad.getText().toString());
                    final String volumens = String.valueOf(volumen);
                    falla = fallas.getText().toString();
                    error = resuldato4.getSelectedItem().toString();


                    final String nombretarea = resuldato.getSelectedItem().toString();


                    hilo = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?op=" + resuldato3.getSelectedItem().toString() + "&tarea=" + nombretarea).body();

                            try {

                                JSONArray RESTARCANTIDAD = new JSONArray(response);

                                total = Integer.parseInt(falla);
                                volumencan = Integer.parseInt(cantidad.getText().toString());
                                cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));

                                String respuesta = HttpRequest.get("http://" + cambiarIP.ip + "/validar/SobranteAct.php?op=" + resuldato3.getSelectedItem().toString() + "&tarea=" + nombretarea).body();
                                JSONArray OTRACANTIDAD = new JSONArray(respuesta);

                                cantidadotra = Integer.parseInt(OTRACANTIDAD.getString(0));

                                int sumatoria = volumencan + total;
                                int ends = cantidadpro - sumatoria; // BIEN
                                int sqlmala = cantidadotra - total;
                                String malo = String.valueOf(sqlmala);
                                String end = String.valueOf(ends);
                                int tool = cantidadpro - sumatoria;

                                System.out.println("LA CANTIDAD BUENAS " + volumencan);
                                System.out.println("LA CANTIDAD MALAS " + total);
                                System.out.println("LA CANTIDAD BUENAS + MALAS " + sumatoria);
                                System.out.println("LA CANTIDAD EN SQL " + cantidadpro);
                                System.out.println("LA CANTIDAD EN SQL RESTADA " + tool);
                                System.out.println("LA CANTIDAD EN SQL REAL " + ends);

                                if (end.length() >= 0) {
                                    if (tool >= 0) {
                                        if (sumatoria <= cantidadpro) {

                                            Intent Componente = new Intent(OperadorActivity.this, ServicioRegistroSalida.class);
                                            Componente.putExtra("resuldato3", resuldato3.getSelectedItem().toString());
                                            Componente.putExtra("tarea", nombretarea);
                                            Componente.putExtra("items", op.getText().toString());
                                            Componente.putExtra("mala", malo);
                                            Componente.putExtra("restado", end.toString());
                                            Componente.putExtra("id", id.getText().toString());
                                            Componente.putExtra("volumen", volumens.toString());
                                            Componente.putExtra("fechas", fechas);
                                            Componente.putExtra("horas", horas);
                                            Componente.putExtra("error", error);
                                            Componente.putExtra("falla", falla);
                                            startService(Componente);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(2000);
                                                        new Task().execute();
                                                        verificar();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                            textToSpeech.speak("SE REGISTRO LO PRODUCIDO");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(), "DATOS VERIFICADOS", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        } else {
                                            EXEDIO();
                                        }
                                    } else {
                                        EXEDIO();
                                    }
                                } else {
                                    EXEDIO();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                    hilo.start();
                    hilo.interrupted();

                }
            }
        });


        builder.setView(view);
        builder.create();
        final AlertDialog alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);

        Button Verificar = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Verificar.setEnabled(false);

        resuldato4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(resuldato4.getSelectedItem().toString().length()!=0){
                    System.out.println("EL SPINNER NO ESTA VACIO");
                    desbloquear = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                    desbloquear.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void EXEDIO() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String titleText = "EXEDIO LA CANTIDAD DE PRODUCCION AUTORIZADA";
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#E82F2E"));
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                builder.setTitle(ssBuilder);
                builder.setIcon(R.drawable.peligro);
                builder.setMessage("USTED EXEDIO LA CANTIDAD PERMITIDA POR LA O.P");
                builder.setNegativeButton("VOLVER A REGISTRAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


                AlertDialog alert = builder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);


            }
        });
    }

    public void aplazo(View v) {


        motivofalla();
        id = findViewById(R.id.operador);
        View view = getLayoutInflater().inflate(R.layout.aplazar_produccion, null);
        resuldato4 = view.findViewById(R.id.spinner2);
        fallas = view.findViewById(R.id.fallas);
        cantidad = view.findViewById(R.id.digicantidad);


        final String tarea = resuldato.getSelectedItem().toString(); //**

        // imprime fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();

        //imprime hora
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        //almacena los datos en una cadena
        final String horafinal = hourFormat.format(date);
        final String fechafinal = dateFormat.format(date);

        final AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);


        EditText edit = new EditText(this);
        edit.setEnabled(false);
        edit.setText(fechafinal);

        EditText editt = new EditText(this);
        editt.setEnabled(false);
        editt.setText(horafinal);

        final String fechas = edit.getText().toString();
        final String horas = editt.getText().toString();

        builder.setPositiveButton("VERIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (fallas.getText().toString().length() == 0 && cantidad.getText().toString().length() == 0) {
                    fallas.setError("DEBE LLENARSE");
                    cantidad.setError("DEBE LLENARSE");
                } else {


                    volumen = Integer.parseInt(cantidad.getText().toString());
                    final String volumens = String.valueOf(volumen);
                    falla = fallas.getText().toString();
                    error = resuldato4.getSelectedItem().toString();


                    final String nombretarea = resuldato.getSelectedItem().toString();


                    hilo = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?op=" + resuldato3.getSelectedItem().toString() + "&tarea=" + nombretarea).body();

                            try {

                                JSONArray RESTARCANTIDAD = new JSONArray(response);

                                total = Integer.parseInt(falla);
                                volumencan = Integer.parseInt(cantidad.getText().toString());
                                cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));

                                String respuesta = HttpRequest.get("http://" + cambiarIP.ip + "/validar/SobranteAct.php?op=" + resuldato3.getSelectedItem().toString() + "&tarea=" + nombretarea).body();
                                JSONArray OTRACANTIDAD = new JSONArray(respuesta);

                                cantidadotra = Integer.parseInt(OTRACANTIDAD.getString(0));

                                int sumatoria = volumencan + total;
                                int ends = cantidadpro - sumatoria; // BIEN
                                int sqlmala = cantidadotra - total;
                                String malo = String.valueOf(sqlmala);
                                String end = String.valueOf(ends);
                                int tool = cantidadpro - sumatoria;

                                System.out.println("LA CANTIDAD BUENAS " + volumencan);
                                System.out.println("LA CANTIDAD MALAS " + total);
                                System.out.println("LA CANTIDAD BUENAS + MALAS " + sumatoria);
                                System.out.println("LA CANTIDAD EN SQL " + cantidadpro);
                                System.out.println("LA CANTIDAD EN SQL RESTADA " + tool);
                                System.out.println("LA CANTIDAD EN SQL REAL " + ends);

                                if (end.length() >= 0) {
                                    if (tool >= 0) {
                                        if (sumatoria <= cantidadpro) {

                                            Intent Componente = new Intent(OperadorActivity.this, ServicioRegistroSalida.class);
                                            Componente.putExtra("resuldato3", resuldato3.getSelectedItem().toString());
                                            Componente.putExtra("tarea", nombretarea);
                                            Componente.putExtra("items", op.getText().toString());
                                            Componente.putExtra("mala", malo);
                                            Componente.putExtra("restado", end.toString());
                                            Componente.putExtra("id", id.getText().toString());
                                            Componente.putExtra("volumen", volumens.toString());
                                            Componente.putExtra("fechas", fechas);
                                            Componente.putExtra("horas", horas);
                                            Componente.putExtra("error", error);
                                            Componente.putExtra("falla", falla);
                                            startService(Componente);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(2000);
                                                        new Task().execute();
                                                        verificar();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                            textToSpeech.speak("SE REGISTRO LO PRODUCIDO");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(), "DATOS VERIFICADOS", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        } else {
                                            EXEDIO();
                                        }
                                    } else {
                                        EXEDIO();
                                    }
                                } else {
                                    EXEDIO();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                    hilo.start();
                    hilo.interrupted();

                }
            }
        });
        builder.setView(view);
        builder.create();
        final AlertDialog alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);

        Button Verificar = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Verificar.setEnabled(false);

        resuldato4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(resuldato4.getSelectedItem().toString().length()!=0){
                    System.out.println("EL SPINNER NO ESTA VACIO");
                    desbloquear = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                    desbloquear.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}

