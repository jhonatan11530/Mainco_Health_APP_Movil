package com.example.mainco;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class OperadorActivity extends AppCompatActivity {


    private EditText id, cantidad, paro, fallas;
    private String mensaje = "";
    String cargarSpinner;
    private ListView resultados;
    private TextView motivo, MOSTRAR,texto,totalcan,tex;
    private Spinner resuldato, resuldato2, resuldato3, resuldato4;

    private  Button BTN_time, go, stop, btnconfir,desbloquear,positivo,neutrar, registroTIME, salidaTIME,validarinfo,cantidadund,btnvalidar;
    private  TimePickerDialog listo;
    private int minuto, i, hora,cantidadpro,vo,volumencan,total,datoverifica;;
    ArrayList<cantidades> dato = new ArrayList<cantidades>();
    private ArrayList<String> datos2 = new ArrayList<String>();
    private ArrayList<produccion> dato3 = new ArrayList<produccion>();
    private ArrayList<cantidadfallas> dato4 = new ArrayList<cantidadfallas>();
    ActionBar actionBar;
    EditText edit,digito;
    View tiempo1,adelanto;
    AlertDialog.Builder registros,aplazarproduccion;
    Date date;
    SimpleDateFormat hourFormat;
    SimpleDateFormat dateFormat;
    private RadioGroup grupo;
    private AsyncHttpClient client;
    private AsyncHttpClient clientes;
    private AsyncHttpClient clientes2;
    private AsyncHttpClient clientes3;
    TSSManager ttsManager=null;
    public Thread hilo,eliminaOK;
    private RadioButton botonSi,botonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operador);

        client = new AsyncHttpClient();
        clientes = new AsyncHttpClient();
        clientes2 = new AsyncHttpClient();
        clientes3 = new AsyncHttpClient();


        ttsManager=new TSSManager();
        ttsManager.init(this);

        resuldato = (Spinner) findViewById(R.id.spinner1);

        totalcan = (TextView)findViewById(R.id.CANTIDADID);

        resuldato2 = (Spinner) findViewById(R.id.spinner2);

        resuldato3 = (Spinner) findViewById(R.id.spinner);

        cantidadund = (Button) findViewById(R.id.aplazar);

        desbloquear = (Button) findViewById(R.id.tiempo);

        registroTIME = (Button) findViewById(R.id.insertar);

        salidaTIME = (Button) findViewById(R.id.salida);






        llenarSpinner();
        llenarSpinner2();

        desbloquear.setEnabled(false);
        registroTIME.setEnabled(false);
        salidaTIME.setEnabled(false);
        cantidadund.setEnabled(false);

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




                        Toast.makeText(getApplicationContext(),"SESIÒN CERRADA", Toast.LENGTH_SHORT).show();
                        ttsManager.initQueue("HASTA LUEGO");
                        Intent e = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(e);


                    }
                });

                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"LA SESIÒN SE MANTENDRA ACTIVA", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void llenarSpinner() {

        String url = "http://" + cambiarIP.ip + "/validar/cantidad.php";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargarSpinner(String cargarSpinner) {
        ArrayList<cantidades> dato = new ArrayList<cantidades>();
        try {
            JSONArray objecto = new JSONArray(cargarSpinner);
            for (int i = 0; i < objecto.length(); i++) {
                cantidades a = new cantidades();
                a.setTarea(objecto.getJSONObject(i).getString("tarea"));
                dato.add(a);
            }
            ArrayAdapter<cantidades> a = new ArrayAdapter<cantidades>(this, android.R.layout.simple_dropdown_item_1line, dato);
            resuldato.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void datos() {
        String url = "http://" + cambiarIP.ip + "/validar/motivo.php";
        clientes.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    listoSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void listoSpinner(String listoSpinner) {
        ArrayList<motivoparo> dato2 = new ArrayList<motivoparo>();
        try {
            JSONArray objecto = new JSONArray(listoSpinner);
            for (int i = 0; i < objecto.length(); i++) {
                motivoparo b = new motivoparo();
                b.setParo(objecto.getJSONObject(i).getString("paro"));
                dato2.add(b);
            }
            ArrayAdapter<motivoparo> a = new ArrayAdapter<motivoparo>(this, android.R.layout.simple_dropdown_item_1line, dato2);
            resuldato2.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void llenarSpinner2() {
        String url = "http://" + cambiarIP.ip + "/validar/produccion.php";
        clientes2.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargar2Spinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargar2Spinner(String cargar2Spinner) {
        ArrayList<produccion> dato3 = new ArrayList<produccion>();
        try {
            JSONArray objecto = new JSONArray(cargar2Spinner);
            for (int i = 0; i < objecto.length(); i++) {
                produccion c = new produccion();
                c.setId(objecto.getJSONObject(i).getString("numero_id"));
                dato3.add(c);
            }
            ArrayAdapter<produccion> a = new ArrayAdapter<produccion>(this, android.R.layout.simple_dropdown_item_1line, dato3);
            resuldato3.setAdapter(a);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void llenarSpinner3() {
        String url = "http://" + cambiarIP.ip + "/validar/motivocantidad.php";
        clientes3.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargar3Spinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargar3Spinner(String cargar3Spinner) {
        ArrayList<cantidadfallas> dato4 = new ArrayList<cantidadfallas>();
        try {
            JSONArray objecto = new JSONArray(cargar3Spinner);
            for (int i = 0; i < objecto.length(); i++) {
                cantidadfallas c = new cantidadfallas();
                c.setFallas(objecto.getJSONObject(i).getString("fallas"));
                dato4.add(c);
            }
            ArrayAdapter<cantidadfallas> a = new ArrayAdapter<cantidadfallas>(this, android.R.layout.simple_dropdown_item_1line, dato4);
            resuldato4.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void operador(View v) {
        mensaje = "";

        id = (EditText) findViewById(R.id.operador);
        resultados = (ListView) findViewById(R.id.listar_operador);


        if (id.getText().toString().length() == 0) {

            id.setError("ID ES REQUERIDO !");

        }
         else {

            final String nombretarea = resuldato.getSelectedItem().toString();

            final String Nop = resuldato3.getSelectedItem().toString();


             hilo = new Thread(new Runnable() {
                @Override
                public void run() {


                        try {
                        String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/operador.php?id=" + id.getText().toString()).body();

                        JSONArray objecto = new JSONArray(response);

                        if(objecto.length()!=0){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    desbloquear.setEnabled(true);
                                    registroTIME.setEnabled(true);
                                    salidaTIME.setEnabled(true);
                                    cantidadund.setEnabled(true);
                                }
                            });

                        for (int i = 0; i < objecto.length(); i++) {
                            mensaje = "" + objecto.getJSONArray(i).getString(0);


                            datos2.add(mensaje);

                        }

                           new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadedit.php?numero="+Nop.toString()).body();

                                            try {
                                                JSONArray RESTARCANTIDAD = new JSONArray(response);
                                                String acz = HttpRequest.get("http://"+cambiarIP.ip+"/validar/validarcantidad.php?id="+nombretarea.toString()).body();

                                                JSONArray tareita = new JSONArray(acz);
                                                  datoverifica = Integer.parseInt(tareita.getString( 0 ));

                                                totalcan.setText("CANTIDAD OP : "+RESTARCANTIDAD.getString(0)+" CANTIDAD PENDIENTE : "+tareita.getString(0));

                                                if(datoverifica == 0){


                                                    new verificar().start();
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                }
                            }).start();


                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        Thread.sleep(1000);

                                        String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadedit.php?numero="+Nop.toString()).body();

                                        try {
                                            JSONArray RESTARCANTIDAD = new JSONArray(response);
                                            int datico = Integer.parseInt(RESTARCANTIDAD.getString(0));

                                            String asd = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizartarea.php?tarea="+nombretarea.toString()+"&canpen="+datico).body();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();


                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"EL CODIGO DEL USUARIO NO EXISTE", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (Exception e) {
                        // TODO: handle exception

                    }



                }


            });
            hilo.start();




            resultados.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos2));
            datos2.clear();





        }


    }
    public void MOSTRAROP(){


        final String nombretarea = resuldato.getSelectedItem().toString();

        final String Nop = resuldato3.getSelectedItem().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {

                String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadedit.php?numero="+Nop.toString()).body();

                try {
                    JSONArray RESTARCANTIDAD = new JSONArray(response);
                    String acz = HttpRequest.get("http://"+cambiarIP.ip+"/validar/validarcantidad.php?id="+nombretarea.toString()).body();

                    JSONArray tareita = new JSONArray(acz);
                    datoverifica = Integer.parseInt(tareita.getString( 0 ));

                    totalcan.setText("CANTIDAD OP : "+RESTARCANTIDAD.getString(0)+" CANTIDAD PENDIENTE : "+tareita.getString(0));

                    if(datoverifica == 0){


                        new verificar().start();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    class verificar extends Thread{
        public void run(){

            eliminaOK = new Thread(new Runnable() {
                @Override
                public void run() {



                            String cero = HttpRequest.get("http://"+cambiarIP.ip+"/validar/eliminarcanok.php").body();

                            try {
                                JSONArray nada = new JSONArray(cero);

                                System.out.println("EL TIPO DE COSITA ES "+nada.getString(0));


                                if(nada.getString(0) == null){


                                runOnUiThread( new Runnable() {
                                    @Override
                                    public void run() {
                                        ((TextView) resuldato.getSelectedView()).setTextColor(Color.BLACK);

                                    }
                                } );


                                }
                                else if(nada.getString(0) != null){

                                    runOnUiThread( new Runnable() {
                                        @Override
                                        public void run() {
                                            desbloquear.setEnabled(false);
                                            registroTIME.setEnabled(false);
                                            salidaTIME.setEnabled(false);
                                            cantidadund.setEnabled(false);
                                            ((TextView) resuldato.getSelectedView()).setTextColor(Color.RED);

                                            AlertDialog.Builder builder = new AlertDialog.Builder( OperadorActivity.this );
                                            builder.setTitle( "LA OP FINALIZO " );
                                            builder.setMessage( "DEBE SELECCIONAR OTRA OP " );
                                            builder.setPositiveButton( "ACEPTAR", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            } );
                                            builder.create().show();


                                        }
                                    } );

                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                }
            });
            eliminaOK.start();

        }
    }


    public void hora(View v) {


        llenarSpinner2();

         registros = new AlertDialog.Builder(OperadorActivity.this);
        tiempo1 = getLayoutInflater().inflate(R.layout.dialog_spinner,null);

        datos();
        id=(EditText)findViewById(R.id.operador);

        paro = (EditText)tiempo1.findViewById(R.id.paro);
        MOSTRAR = (TextView) tiempo1.findViewById(R.id.MOSTRAR);
       texto = (TextView) tiempo1.findViewById(R.id.textos);
        stop = (Button)tiempo1.findViewById(R.id.stop);
        go = (Button)tiempo1.findViewById(R.id.go);
        validarinfo = (Button)tiempo1.findViewById(R.id.validarinfo);



        motivo = (TextView) tiempo1.findViewById(R.id.MOTIVO);
        resuldato2 = (Spinner)tiempo1.findViewById(R.id.spinner2);


        //botones

        MOSTRAR.setVisibility(View.INVISIBLE);
        paro.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);
        go.setVisibility(View.INVISIBLE);
        texto.setVisibility(View.INVISIBLE);
        resuldato2.setVisibility(View.INVISIBLE);


        //TEXTVIEW Y SPINNER
        motivo.setVisibility(View.VISIBLE);
        resuldato2.setVisibility(View.VISIBLE);

        validarinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarinfo.setVisibility(View.INVISIBLE);
                resuldato2.setClickable(false);
                resuldato2.setEnabled(false);



                MOSTRAR.setVisibility(View.VISIBLE);
                paro.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                go.setVisibility(View.VISIBLE);
                texto.setVisibility(View.VISIBLE);
                resuldato2.setVisibility(View.VISIBLE);

                 String mostrardatos = resuldato2.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),"USTED SELECCIÓNO "+mostrardatos, Toast.LENGTH_SHORT).show();
            }
        });


        registros.setPositiveButton("FINALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"LOS DATOS FUERON GUARDADOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });


        registros.setNegativeButton("REGISTRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                        Toast.makeText(getApplicationContext(),"DATOS VERIFICADOS", Toast.LENGTH_SHORT).show();


            }
        });
        registros.setNeutralButton("REGISTRAR OTRO MOTIVO DE PARO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });



        stop.setEnabled(false);

        registros.setView(tiempo1);
        registros.create();
      AlertDialog alert = registros.create();
        alert.show();

        alert.setCanceledOnTouchOutside(false);

        desbloquear = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        neutrar = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
        positivo = alert.getButton(AlertDialog.BUTTON_POSITIVE);

        neutrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                positivo.setEnabled(false);
                neutrar.setEnabled(false);
                desbloquear.setEnabled(false);
                go.setEnabled(true);

                MOSTRAR.setVisibility(View.INVISIBLE);
                paro.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.INVISIBLE);
                go.setVisibility(View.INVISIBLE);
                texto.setVisibility(View.INVISIBLE);

                
                validarinfo.setVisibility(View.VISIBLE);
                motivo.setVisibility(View.VISIBLE);
                resuldato2.setClickable(true);
                resuldato2.setEnabled(true);
                resuldato2.setVisibility(View.VISIBLE);

                MOSTRAR.setText("0:0:0");
            }
        });


        desbloquear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                desbloquear.setEnabled(false);
                neutrar.setEnabled(true);
                positivo.setEnabled(true);

                final String prueba = resuldato2.getSelectedItem().toString();


                final String Nop = resuldato3.getSelectedItem().toString();

                // imprime fecha
                dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                date = new Date();

                //imprime hora
                hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

                final String horas = hourFormat.format(date);
                final String fechas = dateFormat.format(date);


                new Thread(new Runnable() {

                    @Override
                    public void run() {

                            String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/RegistrarMotivo.php?op="+Nop.toString()+"&id="+id.getText().toString()+"&paro="+paro.getText().toString()+"&motivo="+prueba.toString()+"&fecha="+fechas+"&hora="+horas.toString()).body();

                    }
                }).start();

                minuto=0;
                hora=0;

            }
        });

        positivo.setEnabled(false);
        neutrar.setEnabled(false);
        desbloquear.setEnabled(false);
    }


    public void go (View v)  {

        go.setEnabled(false);

        hilo =  new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    i=0;
                    minuto =0;
                    hora=0;

                    for ( i= 0; i <= 60; i++){
                        System.out.println(i);

                        Thread.sleep(1000);

                        if (i==60){
                            i=0;
                            minuto++;

                        }

                        if (minuto == 59){
                            minuto =0;
                            hora++;
                        }

                        if(hora==12){
                            hora=0;

                        }

                        final  String segundo = Integer.toString(i);
                        final  String minutos = Integer.toString(minuto);
                        final String horas = Integer.toString(hora);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                MOSTRAR.setText(horas+":"+minuto+":"+segundo);

                            }
                        });

                    }

                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();


                }


            }



        });

        hilo.start();

        if(hilo!=null){
            stop.setEnabled(true);
            hilo.interrupted();

        }

    }


    public void  stop (View v) {
        stop.setEnabled(false);
        go.setEnabled(false);
        desbloquear.setEnabled(true);
        if(paro!=null){

            motivo.setVisibility(View.VISIBLE);
            resuldato2.setVisibility(View.VISIBLE);
            hilo.interrupt();

        }

       String d = Integer.toString(minuto);
       paro.setText(d);

    }

    public void registrar (View v) {

        id = (EditText)findViewById(R.id.operador);

        // imprime fecha
         dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
         date = new Date();

        //imprime hora
         hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        //almacena los datos en una cadena
        final	String hora = hourFormat.format(date);

        final	String fecha = dateFormat.format(date);

        AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
        builder.setTitle("DATOS INSERTADOS");
        builder.setMessage( "PORFAVOR EMPIECE SU LABOR" );
         edit = new EditText(this);
        edit.setEnabled(false);
        edit.setText(fecha);

        final   String fechas =edit.getText().toString();
        final String Nop = resuldato3.getSelectedItem().toString();
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                     @Override
                    public void run() {

                        String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaEntrada.php?id="+id.getText().toString()+"&Finicial="+fechas+"&Hinicial="+hora.toString()+"&op="+Nop.toString()).body();

                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 registroTIME.setEnabled(false);
                             }
                         });

                         MOSTRAROP();

                    }
                }).start();
            Thread.interrupted();
            }
        });

        builder.create().show();

    }

    public void salida (View v) {

        llenarSpinner3();


        id=(EditText)findViewById(R.id.operador);
        View view = getLayoutInflater().inflate(R.layout.cantidad_produccidas,null);
        resuldato4 =(Spinner)view.findViewById(R.id.spinner2);
        fallas = (EditText)view.findViewById(R.id.fallas);
        cantidad = (EditText)view.findViewById(R.id.digicantidad);

         final String op = resuldato3.getSelectedItem().toString(); //**
         final String tarea = resuldato.getSelectedItem().toString(); //**

        // imprime fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();

        //imprime hora
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        //almacena los datos en una cadena
        final	String horafinal = hourFormat.format(date);
        final	String fechafinal = dateFormat.format(date);

        final AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);


        ArrayAdapter <cantidadfallas> a = new ArrayAdapter<cantidadfallas> (this, android.R.layout.simple_dropdown_item_1line, dato4 );
        resuldato4.setAdapter(a);

        EditText edit = new EditText(this);
        edit.setEnabled(false);
        edit.setText(fechafinal);

        EditText editt = new EditText(this);
        editt.setEnabled(false);
        editt.setText(horafinal);

        final   String fechas =edit.getText().toString();
        final   String horas =editt.getText().toString();
        builder.setPositiveButton("VERIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            final   int volumen = Integer.parseInt(cantidad.getText().toString());
            final   String falla = fallas.getText().toString();
            final String error = resuldato4.getSelectedItem().toString();

                final String Nop = resuldato3.getSelectedItem().toString();
                final String nombretarea = resuldato.getSelectedItem().toString();


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String cantiok = HttpRequest.get("http://"+cambiarIP.ip+"/validar/consulcantidad.php?numero="+id.getText().toString()).body();

                        try {

                            JSONArray objecto = new JSONArray(cantiok);


                            final int cantidadreg = Integer.parseInt(objecto.getString(0));
                            int volumen = Integer.parseInt(cantidad.getText().toString());
                            total = cantidadreg + volumen;

                            if(cantidadreg == 0){

                                String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaSalida.php?op="+op.toString()+"&id="+id.getText().toString()+"&Ffinal="+fechas+"&Hfinal="+horas+"&cantidad="+volumen+"&fallas="+falla.toString()+"&cantidaderror="+error.toString()+"&tarea="+tarea.toString()).body();

                            }
                            else if(cantidadreg > 0){

                                String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaSalida.php?op="+op.toString()+"&id="+id.getText().toString()+"&Ffinal="+fechas+"&Hfinal="+horas+"&cantidad="+total+"&fallas="+falla.toString()+"&cantidaderror="+error.toString()+"&tarea="+tarea.toString()).body();

                            }

                            MOSTRAROP();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();
                Thread.interrupted();



                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadedits.php?numero="+nombretarea.toString()).body();

                            try {
                                JSONArray RESTARCANTIDAD = new JSONArray(response);

                                volumencan = Integer.parseInt(cantidad.getText().toString());
                                cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));

                                int totalade = cantidadpro - volumencan;

                                        if(totalade >= 0){
                                        String responses = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadmodifi.php?numero="+nombretarea.toString()+"&totales="+totalade).body();

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),"DATOS VERIFICADOS", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        }else if (totalade < 0){

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                                builder.setTitle("EXEDIO LA CANTIDAD DE PRODUCCION AUTORIZADA");
                                                builder.setMessage("usted exedio la cantidad permitida por la op ");
                                                builder.setNegativeButton("VOLVER AL REGISTRO", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });


                                                builder.create().show();


                                        }
                                    });

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                    }
                }).start();
                Thread.interrupted();




            }
        });


        builder.setView(view);
        builder.create();
        AlertDialog alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);

    }
    public void aplazo(View v){

        aplazarproduccion = new AlertDialog.Builder(OperadorActivity.this);
       adelanto = getLayoutInflater().inflate(R.layout.aplazar_produccion,null);
         tex = (TextView)adelanto.findViewById(R.id.textos);
         grupo = (RadioGroup)adelanto.findViewById(R.id.GRUPORADIO);
         btnconfir = (Button)adelanto.findViewById(R.id.CONFIRMARADE);
         btnvalidar = (Button)adelanto.findViewById(R.id.VALIDARADE);
         botonSi = (RadioButton)adelanto.findViewById(R.id.SI);
        botonNo = (RadioButton)adelanto.findViewById(R.id.NO);
        digito = (EditText)adelanto.findViewById(R.id.digicantidad);

        digito.setVisibility(adelanto.INVISIBLE);
        btnconfir.setVisibility(adelanto.INVISIBLE);

        btnvalidar.setVisibility(adelanto.VISIBLE);
        grupo.setVisibility(adelanto.VISIBLE);
        botonSi.setVisibility(adelanto.VISIBLE);
        botonNo.setVisibility(adelanto.VISIBLE);




        btnvalidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(grupo.getCheckedRadioButtonId() == -1){
                    ttsManager.initQueue("NO SE PUEDE CONTINUAR DEBE SELECCIONAR UNA OPCION");
                    Toast.makeText(getApplicationContext(),"NO SE PUEDE CONTINUAR DEBE SELECCIONAR UNA OPCION", Toast.LENGTH_SHORT).show();
                }
                else if(botonSi.isChecked()){
                    Toast.makeText(getApplicationContext(),"SI DESEO ADELANTAR LO PRODUCCIDO", Toast.LENGTH_SHORT).show();

                    digito.setVisibility(adelanto.VISIBLE);
                    btnconfir.setVisibility(adelanto.VISIBLE);

                    btnvalidar.setVisibility(adelanto.INVISIBLE);
                    grupo.setVisibility(adelanto.INVISIBLE);
                    botonSi.setVisibility(adelanto.INVISIBLE);
                    botonNo.setVisibility(adelanto.INVISIBLE);
                    tex.setVisibility(adelanto.INVISIBLE);

                }
                else if(botonNo.isChecked()){

                    Toast.makeText(getApplicationContext(),"CUANTAS CANTIDADES DESEAS ADELANTAR ", Toast.LENGTH_SHORT).show();

                    digito.setVisibility(adelanto.INVISIBLE);
                }
            }
        });

        btnconfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String Nop = resuldato3.getSelectedItem().toString();
                        volumencan = Integer.parseInt(digito.getText().toString());
                       String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizarcantidad.php?numero="+Nop.toString()+"&id="+id.getText().toString()+"&canpen="+volumencan).body();

                    }
                }).start();
                Thread.interrupted();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                final String nombretarea = resuldato.getSelectedItem().toString();
                            try {
                                Thread.sleep(1000);


                                String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadedits.php?numero="+nombretarea.toString()).body();

                                try {
                                    JSONArray RESTARCANTIDAD = new JSONArray(response);

                                    volumencan = Integer.parseInt(digito.getText().toString());
                                    cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));

                                  int totalade = cantidadpro - volumencan;

                                    System.out.println("LAS CANTIDADES SON : "+totalade);

                                    if(totalade >= 0){
                                String responses = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadmodifi.php?numero="+nombretarea.toString()+"&totales="+totalade).body();

                                runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           Toast.makeText(getApplicationContext(),"SE REGISTRO EL ADELANTO PRODUCCIDO ", Toast.LENGTH_SHORT).show();
                                           MOSTRAROP();
                                       }
                                   });
                                    }
                                    else if (totalade < 0){

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(500);
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                                    builder.setTitle("EXEDIO LA CANTIDAD DE PRODUCCION AUTORIZADA");
                                                    builder.setMessage("usted exedio la cantidad permitida por la op ");
                                                    builder.setNegativeButton("VOLVER AL REGISTRO", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                        }
                                                    });


                                                    builder.create().show();
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        });

                                    }/*else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(500);

                                                ttsManager.initQueue("SE REGISTRO EL ADELANTO PRODUCIDO");
                                                Toast.makeText(getApplicationContext(),"SE REGISTRO EL ADELANTO PRODUCIDO", Toast.LENGTH_SHORT).show();

                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                    } */
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                    }
                }).start();
                Thread.interrupted();
           }
        });
        aplazarproduccion.setPositiveButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        aplazarproduccion.setView(adelanto);
        aplazarproduccion.create();
        AlertDialog alert = aplazarproduccion.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);


    }


}

