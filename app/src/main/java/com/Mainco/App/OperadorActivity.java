package com.Mainco.App;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import GET_SET.OPS;
import GET_SET.cantidades;
import GET_SET.cantidadfallas;
import GET_SET.motivoparo;
import Http_Conexion.HttpRequest;
import IP.cambiarIP;
import Services.ServicioActividad;
import Services.ServicioCantidad;
import Services.ServicioContador;
import Services.ServicioItems;
import Services.ServicioMotivoParo;
import Services.ServicioProductoMalo;
import TSS.TTS;
import cz.msebera.android.httpclient.Header;

@SuppressWarnings("ALL")
public class OperadorActivity extends AppCompatActivity implements LifecycleObserver {

    public static Spinner Actividad, NombreParo, resuldato4, NumeroItem;
    public Thread hilo;
    EditText edit, digito;
    View tiempo1, adelanto;
    AlertDialog.Builder registros;
    Date date;
    SimpleDateFormat hourFormat;
    SimpleDateFormat dateFormat;
    RelativeLayout production;
    TTS textToSpeech = null;

    private EditText id, cantidad, paro, fallas, op, codemotivo;
    private String falla, error, VaribleTOTA, NOMBRE;
    private TextView motivo, MOSTRAR, texto, resultados, MostrarCantidadOP;
    private Button go, stop, BtnParo, positivo, neutrar, BtnIngreso, Btnsalida, validarinfo, Validar, BtnHora;
    private int minuto, segundo, horas, i, hora, cantidadpro, cantidadotra, volumencan, total, volumen;
    private AsyncHttpClient cliente, cliente1, cliente2, validar;
    private Thread workerThread = null;


    private BroadcastReceiver LlenarSpinner = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // LLENA SPINNER ACTIVIDAD
            String cargarSpinner = intent.getStringExtra("llenarSpinner");
            cargarSpinner(new String(cargarSpinner));
            System.out.println("MENSAJE " + cargarSpinner);
        }
    };
    private BroadcastReceiver LlenarItem = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //LLENA SPINNER ITEM
            String llenarOps = intent.getStringExtra("llenarOps");
            cargarops(new String(llenarOps));
            System.out.println("MENSAJE " + llenarOps);

        }
    };
    private BroadcastReceiver LlenarMotivoParo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //LLENA SPINNER DESCANSO
            String filtrardescanso = intent.getStringExtra("filtrardescanso");
            filtrardescanso(new String(filtrardescanso));
            System.out.println("MENSAJE " + filtrardescanso);
        }
    };

    private BroadcastReceiver ProductoMalo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //LLENA SPINNER DESCANSO
            String MALO = intent.getStringExtra("malo");
            cargarmotivo(new String(MALO));
            System.out.println("MENSAJE " + MALO);
        }
    };

    private BroadcastReceiver ServicioCantidad = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //LLENA SPINNER DESCANSO
            String MostrarCantidadTotal = intent.getStringExtra("ServicioCantidad");
            System.out.println("MENSAJE " + MostrarCantidadTotal);
            TextView MostrarPantalla = findViewById(R.id.MostrarCantidad);
            MostrarPantalla.setText("CANTIDAD EN O.P : " + MostrarCantidadTotal);
        }
    };

    private BroadcastReceiver ServicioContador = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //LLENA SPINNER DESCANSO
            final String hours = intent.getStringExtra("hours");
            final String minute = intent.getStringExtra("minute");
            final String segund = intent.getStringExtra("segund");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    MOSTRAR.setText(hours + ":" + minute + ":" + segund);
                }
            });

        }
    };

    public static String getIP() {
        List<InetAddress> addrs;
        String address = "";
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        address = addr.getHostAddress().toUpperCase(new Locale("es", "CO"));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ex getting IP value " + e.getMessage());

        }
        return address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operador);


        // CONEXIONES TOTALES
        cliente = new AsyncHttpClient();
        cliente1 = new AsyncHttpClient();
        cliente2 = new AsyncHttpClient();
        validar = new AsyncHttpClient();

        // CONFIGURACION HTTP CLIENTE
        cliente.setConnectTimeout(28800000);
        cliente.setResponseTimeout(10000);

        // CONFIGURACION HTTP CLIENTE 1
        cliente1.setConnectTimeout(28800000);
        cliente1.setResponseTimeout(10000);

        // CONFIGURACION HTTP CLIENTE 2
        cliente2.setConnectTimeout(28800000);
        cliente2.setResponseTimeout(10000);

        textToSpeech = new TTS();
        textToSpeech.init(this);

        // Registrar ServiciollenarSpinner
        IntentFilter llenarSpinner = new IntentFilter();
        llenarSpinner.addAction("llenarSpinner");
        registerReceiver(LlenarSpinner, llenarSpinner);

        // Registrar ServiciollenarItem
        IntentFilter llenarItem = new IntentFilter();
        llenarItem.addAction("llenarItem");
        registerReceiver(LlenarItem, llenarItem);

        // Registrar ServiciollenarParo
        IntentFilter llenarParo = new IntentFilter();
        llenarParo.addAction("llenarParo");
        registerReceiver(LlenarMotivoParo, llenarParo);

        // Registrar ServicioProductoMalo
        IntentFilter malo = new IntentFilter();
        malo.addAction("MALO");
        registerReceiver(ProductoMalo, malo);

        // Registrar ServicioContador
        IntentFilter cantidadReal = new IntentFilter();
        cantidadReal.addAction("ServicioCantidad");
        registerReceiver(ServicioCantidad, cantidadReal);

        // Registrar ServicioContador
        IntentFilter Serviciocontador = new IntentFilter();
        Serviciocontador.addAction("ServicioContador");
        registerReceiver(ServicioContador, Serviciocontador);

        Intent llenarspinner = new Intent(OperadorActivity.this, ServicioActividad.class);
        startService(llenarspinner);

        Intent llenaritem = new Intent(OperadorActivity.this, ServicioItems.class);
        startService(llenaritem);


        if (savedInstanceState != null) {
            id.setText(savedInstanceState.getInt("ID"));
            op.setText(savedInstanceState.getString("OP"));
        }


        production = findViewById(R.id.principal);

        Actividad = findViewById(R.id.NameActividad);

        NombreParo = findViewById(R.id.NameParo);

        NumeroItem = findViewById(R.id.NItem);

        op = findViewById(R.id.OP);

        Validar = findViewById(R.id.Validar);


        BtnParo = findViewById(R.id.tiempo);

        BtnIngreso = findViewById(R.id.insertar);

        Btnsalida = findViewById(R.id.salida);

        id = findViewById(R.id.operador);

        resultados = findViewById(R.id.listar_operador);

        MostrarCantidadOP = findViewById(R.id.MostrarCantidad);


        getLifecycle().addObserver(new Observardor());

        BtnParo.setEnabled(false);
        BtnIngreso.setEnabled(false);
        Btnsalida.setEnabled(false);
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


    public void Consolidado() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Date date = new Date();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    final String fechafinal = dateFormat.format(date);
                    final EditText editar = new EditText(OperadorActivity.this);
                    editar.setEnabled(false);
                    editar.setText(fechafinal);

                    HttpRequest.get("http://" + cambiarIP.ip + "/validar/consolidado.php?op=" + op.getText().toString() + "&nombre=" + NOMBRE.toString() + "&fecha=" + editar.getText().toString()).body();

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();

                }

            }
        }).start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle ID_OP) {
        super.onSaveInstanceState(ID_OP);

        ID_OP.putString("ID", id.getText().toString());
        ID_OP.putString("OP", op.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id.setText(savedInstanceState.getInt("ID"));
        op.setText(savedInstanceState.getString("OP"));
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

            case R.id.salir:


                textToSpeech.speak("ESTAS SEGURO QUE DESEAS CERRAR LA SE SION");

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

                builder.setMessage("¿ ESTAS SEGURO QUE DESEAS LA CERRAR SESIÒN ?");

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        stopService(new Intent(OperadorActivity.this, ServicioCantidad.class));
                        stopService(new Intent(OperadorActivity.this, ServicioActividad.class));
                        stopService(new Intent(OperadorActivity.this, ServicioMotivoParo.class));
                        stopService(new Intent(OperadorActivity.this, ServicioProductoMalo.class));
                        stopService(new Intent(OperadorActivity.this, ServicioItems.class));

                        textToSpeech.onPause();
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

        String url = "http://" + cambiarIP.ip + "/validar/cantidadfiltre.php?cod=" + NumeroItem.getSelectedItem().toString() + "&op=" + op.getText().toString(); // SE DEBE CAMBIAR
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    itemfiltre(new String(responseBody));

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
            Actividad.setAdapter(a);

            Validar.setEnabled(true);

            Intent cantidad = new Intent(OperadorActivity.this, ServicioCantidad.class);
            cantidad.putExtra("op", op.getText().toString());
            cantidad.putExtra("cod", NumeroItem.getSelectedItem().toString());
            cantidad.putExtra("tarea", Actividad.getSelectedItem().toString());
            startService(cantidad);

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
            NumeroItem.setAdapter(a);


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void FiltrarOps() {

        String url = "http://" + cambiarIP.ip + "/validar/FiltroOPS.php?op=" + op.getText().toString();
        cliente1.post(url, new AsyncHttpResponseHandler() {
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
            NumeroItem.setAdapter(a);

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
            Actividad.setAdapter(a);


        } catch (Exception e) {
            e.printStackTrace();

        }

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
            NombreParo.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void llenardescansoMotivo() {
        String url = "http://" + cambiarIP.ip + "/validar/motivofiltro.php?motivo=" + codemotivo.getText().toString();
        cliente2.post(url, new AsyncHttpResponseHandler() {
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
            NombreParo.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public void validar() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String responses = HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidad_en_op.php?cod=" + NumeroItem.getSelectedItem().toString() + "&op=" + op.getText().toString()).body();
                try {
                    JSONArray objecto = new JSONArray(responses);
                    int variable = Integer.parseInt(objecto.getString(0));

                    if (variable == 0) {

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
                                                HttpRequest.get("http://" + cambiarIP.ip + "/validar/limpiar.php?id=" + NumeroItem.getSelectedItem().toString() + "&op=" + op.getText().toString()).body();

                                            }
                                        }).start();

                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                                alert.setCanceledOnTouchOutside(false);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        BtnParo.setEnabled(false);
                                        BtnIngreso.setEnabled(false);
                                        Btnsalida.setEnabled(false);
                                    }
                                });


                            }
                        });

                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                builder.setTitle("LA ORDEN DE PRODUCCION EXISTE");
                                builder.setIcon(R.drawable.checker);
                                builder.setMessage("EXISTEN CANTIDADES DE LA ORDEN DE PRODUCCION");

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
                                    BtnIngreso.setEnabled(true);
                                    BtnIngreso.setBackgroundColor(Color.parseColor("#2196F3"));

                                    Btnsalida.setEnabled(false);
                                    BtnParo.setEnabled(false);

                                }
                            });
                            NOMBRE = objecto.getString(0);

                            resultados.setText("OPERADOR : " + NOMBRE.toString());

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
                    }


                }
            });
            hilo.start();


        }

    }

    public void verificar() {

        final String Nitem = NumeroItem.getSelectedItem().toString();
        final String nombretarea = Actividad.getSelectedItem().toString();
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    hilo.sleep(1000);

                    String cero = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?cod=" + Nitem.toString() + "&tarea=" + nombretarea + "&op=" + op.getText().toString()).body();
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
                                            BtnIngreso.setBackgroundColor(Color.parseColor("#2196F3"));
                                            BtnIngreso.setEnabled(true);
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

                                    Btnsalida.setEnabled(false);
                                    BtnParo.setEnabled(false);

                                }
                            });


                        }

                        if (vacio == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ((TextView) Actividad.getSelectedView()).setTextColor(getResources().getColor(R.color.RED));

                                    BtnIngreso.setEnabled(false);
                                    Btnsalida.setEnabled(false);
                                    BtnParo.setEnabled(false);


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
                                                    BtnIngreso.setEnabled(true);

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

                } catch (InterruptedException e) {
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
        NombreParo = tiempo1.findViewById(R.id.NameParo);


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
        NombreParo.setVisibility(View.VISIBLE);
        validarinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarinfo.setEnabled(false);
                NombreParo.setEnabled(false);
                codemotivo.setEnabled(false);

                go.setEnabled(true);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String mostrardatos = NombreParo.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), "USTED SELECCIÓNO " + mostrardatos, Toast.LENGTH_SHORT).show();
                    }
                });
                textToSpeech.speak(NombreParo.getSelectedItem().toString());

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


        BtnHora = alert.getButton(AlertDialog.BUTTON_POSITIVE);


        BtnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String prueba = NombreParo.getSelectedItem().toString();

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

                        HttpRequest.get("http://" + cambiarIP.ip + "/validar/RegistrarMotivo.php?op=" + op.getText().toString() + "&id=" + id.getText().toString() + "&paro=" + paro.getText().toString() + "&motivo=" + prueba + "&code=" + codemotivo.getText().toString() + "&fecha=" + fechas + "&hora=" + horas + "&tarea=" + Actividad.getSelectedItem().toString()).body();

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
        BtnHora.setEnabled(false);


    }

    public void go(View v) {


        go.setEnabled(false);

        Intent cantidad = new Intent(OperadorActivity.this, ServicioContador.class);
        startService(cantidad);

        stop.setEnabled(true);

    }

    public void stop(View v) {
        stopService(new Intent(OperadorActivity.this, ServicioContador.class));

        stop.setEnabled(false);

        go.setEnabled(false);
        BtnHora.setEnabled(true);
        if (paro != null) {

            motivo.setVisibility(View.VISIBLE);
            NombreParo.setVisibility(View.VISIBLE);

        }

        paro.setText(MOSTRAR.getText().toString());

    }

    public void registrar(View v) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String nombretarea = Actividad.getSelectedItem().toString();
                    String Validar = HttpRequest.get("http://" + cambiarIP.ip + "/validar/ValidarRegistro/ValidarRegistro.php?id=" + id.getText().toString()).body();

                    JSONArray ValidarRegistro = new JSONArray(Validar);

                    if (ValidarRegistro.getString(0).length() == 4) {

                        System.out.println("EL VALOR ES NULL");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


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
                                builder.setTitle("HA EMPEZADO A SU ACTIVIDAD");
                                builder.setIcon(R.drawable.checker);
                                builder.setMessage("PORFAVOR EMPIECE HA RELIZAR SU LABOR O ACTIVIDAD ASIGNADA");
                                edit = new EditText(OperadorActivity.this);
                                edit.setEnabled(false);
                                edit.setText(fecha);
                                textToSpeech.speak("PUEDE EMPEZAR A REALIZAR SU LABOR");

                                final String fechas = edit.getText().toString();
                                final String Nop = NumeroItem.getSelectedItem().toString();
                                final String tarea = Actividad.getSelectedItem().toString();
                                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // TODO Auto-generated method stub
                                        hilo = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                final String nombretarea = Actividad.getSelectedItem().toString();
                                                try {

                                                    String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?cod=" + NumeroItem.getSelectedItem().toString() + "&tarea=" + nombretarea + "&op=" + op.getText().toString()).body();
                                                    JSONArray validar = new JSONArray(response);
                                                    int validator = Integer.parseInt(validar.getString(0));

                                                    if (validator == 0) {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Btnsalida.setEnabled(true);
                                                                BtnParo.setEnabled(true);
                                                            }
                                                        });


                                                        String respuesta = HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidad_en_op.php?cod=" + NumeroItem.getSelectedItem().toString() + "&op=" + op.getText().toString()).body();
                                                        JSONArray validartor = new JSONArray(respuesta);
                                                        int real = Integer.parseInt(validartor.getString(0));
                                                        HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidadmodifi.php?cod=" + NumeroItem.getSelectedItem().toString() + "&totales=" + real + "&op=" + op.getText().toString()).body();

                                                        Intent cantidad = new Intent(OperadorActivity.this, ServicioCantidad.class);
                                                        cantidad.putExtra("op", op.getText().toString());
                                                        cantidad.putExtra("cod", NumeroItem.getSelectedItem().toString());
                                                        cantidad.putExtra("tarea", Actividad.getSelectedItem().toString());
                                                        startService(cantidad);

                                                    }
                                                    if (validator > 0) {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Btnsalida.setEnabled(true);
                                                                BtnParo.setEnabled(true);
                                                            }
                                                        });

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

                                                        Intent cantidad = new Intent(OperadorActivity.this, ServicioCantidad.class);
                                                        cantidad.putExtra("op", op.getText().toString());
                                                        cantidad.putExtra("cod", NumeroItem.getSelectedItem().toString());
                                                        cantidad.putExtra("tarea", Actividad.getSelectedItem().toString());
                                                        startService(cantidad);

                                                    }

                                                    HttpRequest.get("http://" + cambiarIP.ip + "/validar/actualizaEntrada.php?id=" + id.getText().toString() + "&Finicial=" + fechas + "&Hinicial=" + hora + "&op=" + op.getText().toString()).body();

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            BtnIngreso.setEnabled(false);
                                                            BtnIngreso.setBackgroundColor(Color.parseColor("#919191"));
                                                        }
                                                    });


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


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
                        });

                    }
                    if (ValidarRegistro.getString(0).length() == 10) {
                        System.out.println("EL VALOR NO ES NULL " + ValidarRegistro.getString(0));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BtnIngreso.setEnabled(false);
                                BtnIngreso.setBackgroundColor(Color.parseColor("#919191"));
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }

    public void salida(View v) {

        Intent llenaritem = new Intent(OperadorActivity.this, ServicioProductoMalo.class);
        startService(llenaritem);

        id = findViewById(R.id.operador);
        View view = getLayoutInflater().inflate(R.layout.cantidad_produccidas, null);
        resuldato4 = view.findViewById(R.id.spinner2);
        fallas = view.findViewById(R.id.fallas);
        cantidad = view.findViewById(R.id.digicantidad);


        final String tarea = Actividad.getSelectedItem().toString(); //**

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


                    final String nombretarea = Actividad.getSelectedItem().toString();


                    hilo = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/Sobrante.php?cod=" + NumeroItem.getSelectedItem().toString() + "&tarea=" + nombretarea + "&op=" + op.getText().toString()).body();

                            try {

                                JSONArray RESTARCANTIDAD = new JSONArray(response);

                                total = Integer.parseInt(falla);
                                volumencan = Integer.parseInt(cantidad.getText().toString());
                                cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));

                                String respuesta = HttpRequest.get("http://" + cambiarIP.ip + "/validar/SobranteOP.php?op=" + op.getText().toString() + "&cod=" + NumeroItem.getSelectedItem().toString()).body();
                                JSONArray OTRACANTIDAD = new JSONArray(respuesta);

                                cantidadotra = Integer.parseInt(OTRACANTIDAD.getString(0));

                                int sumatoria = volumencan + total;
                                int ends = cantidadpro - sumatoria; // BIEN
                                int sqlmala = cantidadotra - total; // TOTAL EN OP - MALAS

                                int tool = cantidadpro - sumatoria;

                                System.out.println("LA CANTIDAD BUENAS " + volumencan);
                                System.out.println("LA CANTIDAD MALAS " + total);
                                System.out.println("LA CANTIDAD BUENAS + MALAS " + sumatoria);
                                System.out.println("LA CANTIDAD EN SQL " + cantidadpro);
                                System.out.println("LA CANTIDAD EN SQL RESTADA " + tool);
                                System.out.println("LA CANTIDAD EN SQL REAL " + ends);
                                System.out.println("LA CANTIDAD MALO " + sqlmala);

                                if (ends >= 0) {
                                    if (tool >= 0) {
                                        if (sumatoria <= cantidadpro) {

                                            HttpRequest.get("http://" + cambiarIP.ip + "/validar/actualizarop.php?op=" + op.getText().toString() + "&totales=" + sqlmala + "&codigo=" + NumeroItem.getSelectedItem().toString()).body();

                                            HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidadupdate.php?cod=" + NumeroItem.getSelectedItem().toString() + "&tarea=" + nombretarea.toString() + "&totales=" + ends + "&op=" + op.getText().toString()).body();

                                            HttpRequest.get("http://" + cambiarIP.ip + "/validar/cantidadmodificar.php?op=" + NumeroItem.getSelectedItem().toString() + "&tarea=" + nombretarea.toString() + "&totales=" + sqlmala + "&codigo=" + op.getText().toString()).body();

                                            HttpRequest.get("http://" + cambiarIP.ip + "/validar/actualizaSalida.php?id=" + id.getText().toString() + "&cantidad=" + volumen + "&Ffinal=" + fechas.toString() + "&Hfinal=" + horas.toString() + "&motivo=" + error.toString() + "&conforme=" + falla.toString() + "&tarea=" + nombretarea.toString() + "&op=" + op.getText().toString()).body();

                                            Intent cantidad = new Intent(OperadorActivity.this, ServicioCantidad.class);
                                            cantidad.putExtra("op", op.getText().toString());
                                            cantidad.putExtra("cod", NumeroItem.getSelectedItem().toString());
                                            cantidad.putExtra("tarea", Actividad.getSelectedItem().toString());
                                            startService(cantidad);

                                            verificar();
                                            Consolidado();
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
                if (resuldato4.getSelectedItem().toString().length() != 0) {
                    System.out.println("EL SPINNER NO ESTA VACIO");
                    BtnHora = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                    BtnHora.setEnabled(true);
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
            Log.i(LOG_TAG, "onDestroy " + id.getText().toString() + " " + op.getText().toString() + " " + obtenerNombreDeDispositivo() + " " + getIP() + " " + hora + " " + fecha);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpRequest.get("http://" + cambiarIP.ip + "/validar/RegistroERROR.php?id=" + id.getText().toString() + "&op=" + op.getText().toString() + "&nombre=" + obtenerNombreDeDispositivo() + "&ip=" + getIP() + "&hora=" + hora.toString() + "&fecha=" + fecha.toString()).body();
                }
            }).start();
            Thread.interrupted();

            finish();

        }

    }

}

